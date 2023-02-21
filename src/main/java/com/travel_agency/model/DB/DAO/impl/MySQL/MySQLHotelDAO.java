package com.travel_agency.model.DB.DAO.impl.MySQL;

import com.travel_agency.model.DB.DAO.HotelDAO;
import com.travel_agency.model.DB.Fields;
import com.travel_agency.model.entity.Hotel;
import com.travel_agency.utils.Constants.MySQLDAOConstants;
import com.travel_agency.utils.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of DAO interface for MySQL
 */
public class MySQLHotelDAO implements HotelDAO<Hotel> {

    private final Connection con;

    public MySQLHotelDAO(Connection con) {
        this.con = con;
    }


    @Override
    public boolean create(Hotel hotel) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.ADD_HOTEL)) {

            setVariablesToCreateStatement(hotel, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            throw new DAOException("Unable to create hotel: " + e.getMessage());
        }
    }

    private void setVariablesToCreateStatement(Hotel hotel, PreparedStatement ps) throws SQLException {
        ps.setString(1, hotel.getName());
        ps.setInt(2, readHotelType(hotel.getType()));
        ps.setString(3, hotel.getCity());
    }

    @Override
    public Hotel read(int id) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_HOTEL)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeHotel(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Unable to read offer: " + e.getMessage());
        } finally {
            close(rs);
        }
        return null;
    }

    private Hotel initializeHotel(ResultSet rs) throws SQLException {
        String type;

        int id = rs.getInt(Fields.HOTEL_ID);
        String name = rs.getString(Fields.HOTEL_NAME);
        String city = rs.getString(Fields.HOTEL_CITY);

        try {
            type = readHotelType(rs.getInt(Fields.HOTEL_TYPE));
        } catch (IllegalArgumentException e) {
            return null;
        }

        return new Hotel(id, name, type, city);
    }

    @Override
    public boolean delete(int id) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.DELETE_HOTEL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Unable to delete hotel: " + e.getMessage());
        }
    }

    @Override
    public List<String> readAllHotelTypes() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_HOTEL_TYPES);
            ResultSet rs = ps.executeQuery()) {
            addHotelTypesToList(result, rs);
        } catch (SQLException e) {
            throw new DAOException("Unable to read list hotel types: " + e.getMessage());
        }
        return result;
    }

    private void addHotelTypesToList(List<String> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt(Fields.HOTEL_TYPE_ID);
            result.add(readHotelType(id));
        }
    }

    private int readHotelType(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.HOTEL_TYPE_ID);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown hotel type name");
        } finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown hotel type name");
    }

    private String readHotelType(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.HOTEL_TYPE_NAME);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown hotel type name");
        } finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown hotel type name");
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
