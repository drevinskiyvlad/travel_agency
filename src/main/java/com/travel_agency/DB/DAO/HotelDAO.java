package com.travel_agency.DB.DAO;

import com.travel_agency.models.City;
import com.travel_agency.models.Hotel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HotelDAO implements DAO<Hotel, String> {
    private final Logger logger = LogManager.getLogger();
    private final Connection con;

    public HotelDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(Hotel hotel) {

        String createCommand = checkIfDescriptionExist(hotel);

        try (PreparedStatement ps = con.prepareStatement(createCommand)){

            setVariablesToCreateStatement(hotel, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create hotel: " + e.getMessage(), e);
            return false;
        }
    }

    private String checkIfDescriptionExist(Hotel hotel) {
        if(hotel.getDescription() != null)
            return Constants.ADD_HOTEL_WITH_DESCRIPTION;

        return Constants.ADD_HOTEL;
    }

    private void setVariablesToCreateStatement(Hotel hotel, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        CityDAO cityDao = new CityDAO(con);
        City city = cityDao.read(hotel.getCity().getName());

        ps.setString(1, hotel.getName());
        ps.setString(2, hotel.getAddress());
        ps.setInt(3, city.getId());
        ps.setInt(4, readHotelType(hotel.getHotelType()));
        ps.setBoolean(5, hotel.isPartner());
        ps.setInt(6, hotel.getNumberOfAvailableRoom());

        if(hotel.getDescription() != null)
            ps.setString(7, hotel.getDescription());
    }

    @Override
    public Hotel read(String name) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_HOTEL)){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return initializeHotel(name, rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read hotel: " + e.getMessage(), e);
            return null;
        }
        return null;
    }

    private Hotel initializeHotel(String name, ResultSet rs) throws SQLException {
        CityDAO cityDao = new CityDAO(con);
        String type;

        int id = rs.getInt(Fields.HOTEL_ID);
        String address = rs.getString(Fields.HOTEL_ADDRESS);
        City city = cityDao.read(rs.getInt(Fields.CITY_ID));
        String description = rs.getString(Fields.HOTEL_DESCRIPTION);
        boolean isPartner = rs.getBoolean(Fields.HOTEL_IS_PARTNER);
        int numberOfAvailableRoom = rs.getInt(Fields.HOTEL_NUMBER_OF_AVAILABLE_ROOM);

        try {
            type = readHotelType(rs.getInt(Fields.HOTEL_TYPE));
        } catch(IllegalArgumentException e){
            e.printStackTrace();
            //todo: add logger here
            return null;
        }

        Hotel hotel = new Hotel
                (id,name,address,city,type,numberOfAvailableRoom,isPartner);

        if(description != null)
            hotel.setDescription(description);

        return hotel;
    }

    @Override
    public boolean update(Hotel hotel, String newName) {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_HOTEL_NAME);){
            ps.setString(1, newName);
            ps.setString(2, hotel.getAddress());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update hotel name: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(Hotel hotel) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_HOTEL)){
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
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_HOTEL);
             ResultSet rs = ps.executeQuery();) {
            addHotelsToList(result, rs);

        } catch (SQLException e) {
            logger.error("Unable to read all hotel: " + e.getMessage(), e);
        }
        return result;
    }

    private void addHotelsToList(List<Hotel> result, ResultSet rs) throws SQLException {
        while(rs.next()) {
            String name = rs.getString(Fields.HOTEL_NAME);
            result.add(read(name));
        }
    }

    private int readHotelType(String name) throws IllegalArgumentException{
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_HOTEL_TYPE_BY_NAME)){

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(Fields.HOTEL_TYPE_ID);
            }

        } catch (SQLException e) {
            logger.error("Unable to read hotel type: " + e.getMessage(), e);
        }
        throw new IllegalArgumentException("Unknown hotel type name");
    }

    private String readHotelType(int id) throws IllegalArgumentException{
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_HOTEL_TYPE_BY_ID)){

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getString(Fields.HOTEL_TYPE_NAME);
            }

        } catch (SQLException e) {
            logger.error("Unable to read hotel type: " + e.getMessage(), e);
        }
        throw new IllegalArgumentException("Unknown hotel type name");
    }

}
