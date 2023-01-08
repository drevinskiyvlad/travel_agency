package com.travel_agency.DB.DAO;

import com.travel_agency.models.City;
import com.travel_agency.models.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CityDAO implements DAO<City, String>{
    private final Logger logger = LogManager.getLogger();
    private final Connection con;

    public CityDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(City city) {
        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_CITY)){

            ps.setString(1, city.getName());
            ps.setInt(2, city.getCountry().getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create city: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public City read(String name) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_CITY)){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return initializeCity(name, rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read city: " + e.getMessage(), e);
            return null;
        }
        return null;
    }

    public City read(int id) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_CITY_BY_ID)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return initializeCity(id, rs);
            }

        } catch (SQLException e) {
            logger.error("Unable to read city: " + e.getMessage(), e);
            return null;
        }
        return null;
    }

    private City initializeCity(String name, ResultSet rs) throws SQLException {
        CountryDAO countryDao = new CountryDAO(con);
        int id = rs.getInt(Fields.CITY_ID);
        Country country = countryDao.read(rs.getInt(Fields.CITY_COUNTRY_ID));
        return new City(id, name, country);
    }

    private City initializeCity(int id, ResultSet rs) throws SQLException {
        CountryDAO countryDao = new CountryDAO(con);
        String name = rs.getString(Fields.CITY_NAME);
        Country country = countryDao.read(rs.getInt(Fields.CITY_COUNTRY_ID));
        return new City(id, name, country);
    }

    @Override
    public boolean update(City city, String newValue) {
        logger.error("Update city is unsupported operation");
        return false;
    }

    @Override
    public boolean delete(City city) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_CITY)){
            ps.setString(1, city.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete city: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<City> readAll() {
        List<City> result = new CopyOnWriteArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_CITY);
             ResultSet rs = ps.executeQuery();) {

            addCityToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read all city: " + e.getMessage(), e);
        }
        return result;
    }

    private void addCityToList(List<City> result, ResultSet rs) throws SQLException {
        while(rs.next()) {
            String name = rs.getString(Fields.CITY_NAME);
            result.add(read(name));
        }
    }
}
