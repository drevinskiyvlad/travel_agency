package com.travel_agency.DB.DAO;

import com.travel_agency.models.City;
import com.travel_agency.models.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CityDAO implements DAO<City, String>{
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
            e.printStackTrace();
            //todo: place here logger
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
            e.printStackTrace();
            System.out.println("CityDAO#read");
            return null;
            //todo: place here logger
        }
        return null;
    }

    private City initializeCity(String name, ResultSet rs) throws SQLException {
        CountryDAO countryDao = new CountryDAO(con);
        int id = rs.getInt(Fields.CITY_ID);
        Country country = countryDao.read(rs.getInt(Fields.CITY_COUNTRY_ID));
        return new City(id, name, country);
    }

    @Override
    public boolean update(City city, String newValue) {
        return false;
    }

    @Override
    public boolean delete(City city) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_CITY)){
            ps.setString(1, city.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CityDAO#delete");
            //todo:add logger here
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
            e.printStackTrace();
            System.out.println("CountryDAO#readAll");
            //todo: add logger here
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
