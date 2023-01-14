package com.travel_agency.DB.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.Fields;
import com.travel_agency.models.DAO.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HotelDAO implements DAO<Hotel, String> {
    private final Logger logger = LogManager.getLogger(HotelDAO.class);
    private final Connection con;

    public HotelDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(Hotel hotel) {
        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_HOTEL)) {

            setVariablesToCreateStatement(hotel, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create hotel: " + e.getMessage(), e);
            return false;
        }
    }

    private void setVariablesToCreateStatement(Hotel hotel, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        ps.setString(1, hotel.getName());
        ps.setString(2, hotel.getAddress());
        ps.setInt(4, readHotelType(hotel.getHotelType()));
        ps.setInt(5, hotel.getVacancy());
        ps.setDouble(5, hotel.getPrice());
    }

    @Override
    public Hotel read(String name) {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_HOTEL)) {
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                return initializeHotel(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read hotel: " + e.getMessage(), e);
            return null;
        }finally {
            close(rs);
        }
        return null;
    }

    public Hotel read(int id) {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_HOTEL_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return initializeHotel(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read hotel: " + e.getMessage(), e);
            return null;
        }finally {
            close(rs);
        }
        return null;
    }

    private Hotel initializeHotel(ResultSet rs) throws SQLException {
        String type;

        int id = rs.getInt(Fields.HOTEL_ID);
        String name = rs.getString(Fields.HOTEL_NAME);
        String address = rs.getString(Fields.HOTEL_ADDRESS);
        int vacancy = rs.getInt(Fields.HOTEL_VACANCY);
        double price = rs.getDouble(Fields.HOTEL_PRICE);

        try {
            type = readHotelType(rs.getInt(Fields.HOTEL_TYPE));
        } catch (IllegalArgumentException e) {
            logger.error("Unable to read hotel type: " + e.getMessage(), e);
            return null;
        }

        return new Hotel(id, name, address, type, vacancy, price);
    }

    @Override
    public boolean update(Hotel hotel, String newName) {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_HOTEL_NAME)) {
            ps.setString(1, newName);
            ps.setString(2, hotel.getAddress());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update hotel name: " + e.getMessage(), e);
            return false;
        }
    }
    public boolean update(Hotel hotel, int newVacancy){
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_HOTEL_VACANCY)) {
            ps.setInt(1, newVacancy);
            ps.setString(2, hotel.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update hotel vacancy: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(Hotel hotel) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_HOTEL)) {
            ps.setString(1, hotel.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete hotel: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Hotel> readAll() {
        List<Hotel> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_HOTELS);
             ResultSet rs = ps.executeQuery()) {
            addHotelsToList(result, rs);

        } catch (SQLException e) {
            logger.error("Unable to read all hotel: " + e.getMessage(), e);
        }
        return result;
    }

    private void addHotelsToList(List<Hotel> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            String name = rs.getString(Fields.HOTEL_NAME);
            result.add(read(name));
        }
    }

    public int readHotelType(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_HOTEL_TYPE_BY_NAME)) {

            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.HOTEL_TYPE_ID);
            }

        } catch (SQLException e) {
            logger.error("Unable to read hotel type: " + e.getMessage(), e);
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown hotel type name");
    }

    public String readHotelType(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_HOTEL_TYPE_BY_ID)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.HOTEL_TYPE_NAME);
            }

        } catch (SQLException e) {
            logger.error("Unable to read hotel type: " + e.getMessage(), e);
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown hotel type name");
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
