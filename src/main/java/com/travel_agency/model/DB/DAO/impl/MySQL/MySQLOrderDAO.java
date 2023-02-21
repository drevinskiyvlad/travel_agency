package com.travel_agency.model.DB.DAO.impl.MySQL;

import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.model.DB.DAO.OrderDAO;
import com.travel_agency.model.DB.Fields;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.model.entity.Order;
import com.travel_agency.model.entity.User;
import com.travel_agency.utils.Constants.MySQLDAOConstants;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of DAO interface for MySQL
 */
public class MySQLOrderDAO implements OrderDAO<Order> {
    private final Connection con;
    @Getter private int numberOfPages; // for pagination
    @Getter private int numberOfUserPages; // for pagination

    /**
     * Constructor
     */
    public MySQLOrderDAO(Connection con) {
        this.con = con;
    }

    public boolean create(Order order) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.ADD_ORDER)) {

            setVariablesToCreateStatement(order, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            throw new DAOException("Unable to create order: " + e.getMessage());
        }
    }

    private void setVariablesToCreateStatement(Order order, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        ps.setString(1, order.getCode());
        ps.setInt(2, order.getUser().getId());
        ps.setInt(3, order.getOffer().getId());
        ps.setInt(4, readOrderStatus(order.getOrderStatus()));
    }

    public Order read(String code) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ORDER)) {

            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeOrder(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Unable to read order: " + e.getMessage());
        }finally {
            close(rs);
        }
        return null;
    }

    public boolean update(Order order, String newStatus) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.CHANGE_ORDER_STATUS)) {
            ps.setInt(1, readOrderStatus(newStatus));
            ps.setString(2, order.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Unable to create order: " + e.getMessage());
        }
    }

    public boolean delete(String code) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.DELETE_ORDER)) {
            ps.setString(1, code);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Unable to delete order: " + e.getMessage());
        }
    }

    public List<Order> readAll(int offset, int numOfRecords) throws DAOException {
        List<Order> result = new CopyOnWriteArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_ORDERS)){
            ps.setInt(1,offset);
            ps.setInt(2,numOfRecords);
            rs = ps.executeQuery();
            addOrdersToList(result, rs);
            rs.close();

            rs = ps.executeQuery(MySQLDAOConstants.ORDER_GET_NUMBER_OF_RECORDS);
            if (rs.next())
                numberOfPages = (int)Math.ceil(rs.getInt(1)*1.0 / numOfRecords);
        } catch (SQLException e) {
            throw new DAOException("Unable to read list of orders: " + e.getMessage());
        } finally{
            close(rs);
        }
        return result;
    }

    public List<Order> readAll(String email, int offset, int numOfRecords) throws DAOException {
        List<Order> result = new CopyOnWriteArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps2 = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_ORDERS_OF_USER)) {
            ps.setInt(1,getUserId(email));
            ps.setInt(2,offset);
            ps.setInt(3,numOfRecords);
            rs = ps.executeQuery();
            addOrdersToList(result, rs);
            rs.close();

            ps2 = con.prepareStatement(MySQLDAOConstants.USER_ORDER_GET_NUMBER_OF_RECORDS);
            ps2.setInt(1,getUserId(email));

            rs = ps2.executeQuery();
            if (rs.next())
                numberOfUserPages = (int)Math.ceil(rs.getInt(1)*1.0 / numOfRecords);
        } catch (SQLException e) {
            throw new DAOException("Unable to read list of orders: " + e.getMessage());
        } finally{
            close(rs);
            close(ps2);
        }
        return result;
    }

    public List<String> readAllOrderStatuses() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_ORDER_STATUS);
             ResultSet rs = ps.executeQuery()) {
            addOrderStatusToList(result, rs);
        } catch (SQLException e) {
            throw new DAOException("Unable to read list order status: " + e.getMessage());
        }
        return result;
    }

    private void addOrderStatusToList(List<String> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt(Fields.ORDER_STATUS_ID);
            result.add(readOrderStatus(id));
        }
    }

    private int getUserId(String userName) throws DAOException {
        MySQLUserDAO dao = new MySQLUserDAO(con);
        return dao.read(userName).getId();
    }

    private int readOrderStatus(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.ORDER_STATUS_ID);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown offer order status");
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown offer order status");
    }

    private String readOrderStatus(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.ORDER_STATUS_NAME);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown order status name");
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown order status name");
    }

    private Order initializeOrder(ResultSet rs) throws SQLException {
        MySQLUserDAO userDao = new MySQLUserDAO(con);
        MySQLOfferDAO offerDao = new MySQLOfferDAO(con);

        String type;
        int id = rs.getInt(Fields.ORDER_ID);
        String code = rs.getString(Fields.ORDER_CODE);
        User user = userDao.read(rs.getInt(Fields.ORDER_USER));
        Offer offer = offerDao.read(rs.getInt(Fields.ORDER_OFFER));

        try {
            type = readOrderStatus(rs.getInt(Fields.ORDER_STATUS));
        } catch (IllegalArgumentException e) {
            return null;
        }

        return new Order(id,code,user,offer,type);
    }

    private void addOrdersToList(List<Order> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            String code = rs.getString(Fields.ORDER_CODE);
            result.add(read(code));
        }
    }
    private void close(AutoCloseable autoCloseable){
        if(autoCloseable != null){
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
