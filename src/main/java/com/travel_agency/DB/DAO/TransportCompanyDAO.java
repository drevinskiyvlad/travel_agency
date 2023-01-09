package com.travel_agency.DB.DAO;

import com.travel_agency.models.City;
import com.travel_agency.models.TransportCompany;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.sun.tools.classfile.AccessFlags.Kind.Field;

public class TransportCompanyDAO implements DAO<TransportCompany, String> {
    private final Logger logger = LogManager.getLogger();
    private final Connection con;

    public TransportCompanyDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(TransportCompany transportCompany) {

        String createCommand = checkIfDescriptionExist(transportCompany);

        try (PreparedStatement ps = con.prepareStatement(createCommand)){

            setVariablesToCreateStatement(transportCompany, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create transport company: " + e.getMessage(), e);
            return false;
        }
    }

    private String checkIfDescriptionExist(TransportCompany transportCompany) {
        if(transportCompany.getDescription() != null)
            return Constants.ADD_TRANSPORT_COMPANY_WITH_DESCRIPTION;

        return Constants.ADD_TRANSPORT_COMPANY;
    }

    private void setVariablesToCreateStatement(TransportCompany transportCompany, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        CityDAO cityDao = new CityDAO(con);
        City city = cityDao.read(transportCompany.getCity().getName());

        ps.setString(1, transportCompany.getName());
        ps.setInt(2, city.getId());
        ps.setString(3, transportCompany.getHQAddress());
        ps.setInt(4, readCompanyType(transportCompany.getCompanyType()));
        ps.setBoolean(5, transportCompany.isPartner());

        if(transportCompany.getDescription() != null)
            ps.setString(6, transportCompany.getDescription());
    }

    @Override
    public TransportCompany read(String name) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_TRANSPORT_COMPANY)){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return initializeTransportCompany(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read transport company: " + e.getMessage(), e);
            return null;
        }
        return null;
    }

    public TransportCompany read(int id) {
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_TRANSPORT_COMPANY_BY_ID)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return initializeTransportCompany(rs);
            }

        } catch (SQLException e) {
            logger.error("Unable to read transport company: " + e.getMessage(), e);
            return null;
        }
        return null;
    }

    private TransportCompany initializeTransportCompany(ResultSet rs) throws SQLException {
        CityDAO cityDao = new CityDAO(con);
        String type;

        int id = rs.getInt(Fields.TRANSPORT_COMPANY_ID);
        String name = rs.getString(Fields.TRANSPORT_COMPANY_NAME);
        City city = cityDao.read(rs.getInt(Fields.TRANSPORT_COMPANY_CITY_ID));
        String HQAddress = rs.getString(Fields.TRANSPORT_COMPANY_HQ_ADDRESS);
        String description = rs.getString(Fields.TRANSPORT_COMPANY_DESCRIPTION);
        boolean isPartner = rs.getBoolean(Fields.TRANSPORT_COMPANY_IS_PARTNER);

        try {
            type = readCompanyType(rs.getInt(Fields.TRANSPORT_COMPANY_COMPANY_TYPE));
        } catch(IllegalArgumentException e){
            logger.error("Unable to read transport company type: " + e.getMessage(), e);
            return null;
        }

        TransportCompany transportCompany = new TransportCompany
                (id, name, city, HQAddress, type, isPartner);

        if(description != null)
            transportCompany.setDescription(description);

        return transportCompany;
    }

    @Override
    public boolean update(TransportCompany transportCompany, String newName) {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_TRANSPORT_COMPANY_NAME);){
            ps.setString(1, newName);
            ps.setString(2, transportCompany.getHQAddress());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update transport company name: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(TransportCompany transportCompany) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_TRANSPORT_COMPANY)){
            ps.setString(1, transportCompany.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete transport company: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<TransportCompany> readAll() {
        List<TransportCompany> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_TRANSPORT_COMPANY);
             ResultSet rs = ps.executeQuery();) {
            addTransportCompaniesToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read all transport companies: " + e.getMessage(), e);
        }
        return result;
    }

    private void addTransportCompaniesToList
            (List<TransportCompany> result, ResultSet rs) throws SQLException {
        while(rs.next()) {
            String name = rs.getString(Fields.TRANSPORT_COMPANY_NAME);
            result.add(read(name));
        }
    }

    private int readCompanyType(String name) throws IllegalArgumentException{
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_TRANSPORT_COMPANY_TYPE_TYPE_BY_NAME)){

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(Fields.TRANSPORT_COMPANY_TYPE_ID);
            }

        } catch (SQLException e) {
            logger.error("Unable to read transport company type: " + e.getMessage(), e);
        }
        throw new IllegalArgumentException("Unknown transport company type name");
    }

    private String readCompanyType(int id) throws IllegalArgumentException{
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_TRANSPORT_COMPANY_TYPE_BY_ID)){

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getString(Fields.TRANSPORT_COMPANY_TYPE_NAME);
            }

        } catch (SQLException e) {
            logger.error("Unable to read transport company type: " + e.getMessage(), e);
        }
        throw new IllegalArgumentException("Unknown transport company type name");
    }

}
