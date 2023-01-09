package com.travel_agency.DB.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.Fields;
import com.travel_agency.models.TransportCompany;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TransportCompanyDAO implements DAO<TransportCompany, String> {
    private final Logger logger = LogManager.getLogger();
    private final Connection con;

    public TransportCompanyDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(TransportCompany transportCompany) {
        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_TRANSPORT_COMPANY)){

            setVariablesToCreateStatement(transportCompany, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create transport company: " + e.getMessage(), e);
            return false;
        }
    }

    private void setVariablesToCreateStatement(TransportCompany transportCompany, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        ps.setString(1, transportCompany.getName());
        ps.setString(2, transportCompany.getHQAddress());
        ps.setInt(3, transportCompany.getVacancy());
        ps.setDouble(3, transportCompany.getPrice());
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
        int id = rs.getInt(Fields.TRANSPORT_COMPANY_ID);
        String name = rs.getString(Fields.TRANSPORT_COMPANY_NAME);
        String HQAddress = rs.getString(Fields.TRANSPORT_COMPANY_HQ_ADDRESS);
        int vacancy = rs.getInt(Fields.TRANSPORT_COMPANY_VACANCY);
        double price = rs.getInt(Fields.TRANSPORT_COMPANY_PRICE);

        return new TransportCompany(id, name, HQAddress, vacancy, price);
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

}
