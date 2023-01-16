package com.travel_agency.DB.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.Fields;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.Offer;
import com.travel_agency.models.DAO.Order;
import com.travel_agency.models.DAO.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderDAO implements DAO<Order, String>{
    private static final Logger logger = LogManager.getLogger(OrderDAO.class);
    private final Connection con;

    public OrderDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(Order order) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_ORDER)) {

            setVariablesToCreateStatement(order, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create order: " + e.getMessage(), e);
            throw new DAOException("Unable to create order: " + e.getMessage());
        }
    }

    private void setVariablesToCreateStatement(Order order, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        ps.setString(1, order.getCode());
        ps.setInt(2, order.getUser().getId());
        ps.setInt(3, order.getOffer().getId());
        ps.setInt(4, readOrderStatus(order.getOrderStatus()));
        ps.setDouble(5, order.getPrice());
    }

    @Override
    public Order read(String code) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ORDER)) {

            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeOrder(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read offer: " + e.getMessage(), e);
            throw new DAOException("Unable to read order: " + e.getMessage());
        }finally {
            close(rs);
        }
        return null;
    }

    @Override
    public boolean update(Order order, String newStatus) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_ORDER_STATUS)) {
            ps.setInt(1, readOrderStatus(newStatus));
            ps.setString(2, order.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update order status: " + e.getMessage(), e);
            throw new DAOException("Unable to create order: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Order order) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_ORDER)) {
            ps.setString(1, order.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete order: " + e.getMessage(), e);
            throw new DAOException("Unable to delete order: " + e.getMessage());
        }
    }

    public boolean delete(String code) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_ORDER)) {
            ps.setString(1, code);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete order: " + e.getMessage(), e);
            throw new DAOException("Unable to delete order: " + e.getMessage());
        }
    }

    @Override
    public List<Order> readAll() throws DAOException {
        List<Order> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_ORDERS);
             ResultSet rs = ps.executeQuery()) {
            addOrdersToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read list of offers: " + e.getMessage(), e);
            throw new DAOException("Unable to read list of orders: " + e.getMessage());
        }
        return result;
    }

    public List<Order> readAll(String email) throws DAOException {
        List<Order> result = new CopyOnWriteArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_ORDERS_OF_USER)) {
            ps.setInt(1,getUserId(email));
            rs = ps.executeQuery();
            addOrdersToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read list of offers: " + e.getMessage(), e);
            throw new DAOException("Unable to read list of orders: " + e.getMessage());
        } finally{
            close(rs);
        }
        return result;
    }

    private int getUserId(String userName) throws DAOException {
        UserDAO dao = new UserDAO(con);
        return dao.read(userName).getId();
    }

    public int readOrderStatus(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ORDER_STATUS_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.ORDER_STATUS_ID);
            }

        } catch (SQLException e) {
            logger.error("Unable to read order status: " + e.getMessage(), e);
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown offer order status");
    }

    public String readOrderStatus(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ORDER_STATUS_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.ORDER_STATUS_NAME);
            }

        } catch (SQLException e) {
            logger.error("Unable to read order status: " + e.getMessage(), e);
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown order status name");
    }



    private Order initializeOrder(ResultSet rs) throws SQLException {
        UserDAO userDao = new UserDAO(con);
        OfferDAO offerDao = new OfferDAO(con);

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
                logger.error("Error while close: " + e.getMessage(), e);
            }
        }
    }
}
