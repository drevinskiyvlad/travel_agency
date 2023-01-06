package com.travel_agency.DB.DAO;

import com.travel_agency.models.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CountryDAO implements DAO<Country, String>{
    private final Connection con;

    public CountryDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(Country country) {

        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_COUNTRY)){

            ps.setString(1, country.getName());
            ps.setString(2, country.getCode());
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("CountryDAO#create");
            //todo: place here logger
            return false;
        }
    }

    @Override
    public Country read(String name) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_COUNTRY)){

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return initializeCountry(name, rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CountryDAO#read");
            return null;
            //todo: place here logger
        }
        return null;
    }

    public Country read(int id) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_COUNTRY_BY_ID)){

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return initializeCountry(id, rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CountryDAO#read");
            return null;
            //todo: place here logger
        }
        return null;
    }

    private Country initializeCountry(String name, ResultSet rs) throws SQLException {
        int id = rs.getInt(Fields.COUNTRY_ID);
        String code = rs.getString(Fields.COUNTRY_CODE);
        return new Country(id, name, code);
    }

    private Country initializeCountry(int id, ResultSet rs) throws SQLException {
        String name = rs.getString(Fields.COUNTRY_NAME);
        String code = rs.getString(Fields.COUNTRY_CODE);
        return new Country(id, name, code);
    }

    @Override
    public boolean update(Country country, String newName) {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_COUNTRY_NAME);){
            ps.setString(1, newName);
            ps.setString(2, country.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CountryDAO#update");
            //todo:add logger here
            return false;
        }
    }

    @Override
    public boolean delete(Country country) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_COUNTRY)){
            ps.setString(1, country.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CountryDAO#delete");
            //todo:add logger here
            return false;
        }
    }

    @Override
    public List<Country> readAll() {
        List<Country> result = new CopyOnWriteArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_COUNTRY);
             ResultSet rs = ps.executeQuery();) {

            addCountryToList(result, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CountryDAO#readAll");
            //todo: add logger here
        }
        return result;
    }

    private void addCountryToList(List<Country> result, ResultSet rs) throws SQLException {
        while(rs.next()) {
            String name = rs.getString(Fields.COUNTRY_NAME);
            result.add(read(name));
        }
    }
}
