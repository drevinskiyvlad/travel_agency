package com.travel_agency.DB.DAO;

import com.travel_agency.models.City;
import com.travel_agency.models.TransportCompany;
import com.travel_agency.models.TransportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TransportServiceDAO implements DAO<TransportService, Integer[]> {
    private final Logger logger = LogManager.getLogger();
    private final Connection con;

    public TransportServiceDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(TransportService transportService) {

        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_TRANSPORT_SERVICE)){

            setVariablesToCreateStatement(transportService, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create transport service: " + e.getMessage(), e);
            return false;
        }
    }

    private void setVariablesToCreateStatement(TransportService transportService, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        CityDAO cityDao = new CityDAO(con);
        TransportCompanyDAO tcDao = new TransportCompanyDAO(con);

        City fromCity = cityDao.read(transportService.getFromCity().getName());
        City toCity = cityDao.read(transportService.getToCity().getName());
        TransportCompany tc = tcDao.read(transportService.getTransportCompany().getName());

        ps.setInt(1, tc.getId());
        ps.setInt(2, fromCity.getId());
        ps.setInt(3, toCity.getId());
        ps.setDouble(4, transportService.getPrice());
        ps.setInt(5, transportService.getNumberOfFreeSeats());
        ps.setBoolean(6, transportService.isActive());
    }

    @Override
    public TransportService read(Integer[] keys) throws IllegalArgumentException {
        if(keys.length != 3){
            IllegalArgumentException e = new IllegalArgumentException("Invalid number of keys");
            logger.error("Unable to read transport service: " + e.getMessage(), e);
            throw e;
        }

        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_TRANSPORT_SERVICE)){
            ps.setInt(1, keys[0]);
            ps.setInt(2, keys[1]);
            ps.setInt(3, keys[2]);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return initializeTransportService(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read transport service: " + e.getMessage(), e);
            return null;
        }
        return null;
    }

    private TransportService initializeTransportService(ResultSet rs) throws SQLException {
        CityDAO cityDao = new CityDAO(con);
        TransportCompanyDAO tcDao = new TransportCompanyDAO(con);

        City fromCity = cityDao.read(rs.getInt(Fields.TRANSPORT_SERVICE_FROM_CITY));
        City toCity = cityDao.read(rs.getInt(Fields.TRANSPORT_SERVICE_TO_CITY));
        TransportCompany tc = tcDao.read(rs.getInt(Fields.TRANSPORT_SERVICE_COMPANY_ID));

        int id = rs.getInt(Fields.TRANSPORT_SERVICE_ID);
        double price = rs.getDouble(Fields.TRANSPORT_SERVICE_PRICE);
        int numberOfFreeSeats = rs.getInt(Fields.TRANSPORT_SERVICE_NUMBER_OF_FREE_SEATS);
        boolean isActive = rs.getBoolean(Fields.TRANSPORT_SERVICE_IS_ACTIVE);

        return new TransportService
                (id,tc,fromCity,toCity,price,numberOfFreeSeats,isActive);
    }

    @Override
    public boolean update(TransportService transportService, Integer[] keys) throws UnsupportedOperationException {
        logger.error("Update city is unsupported operation");
        return false;
    }

    @Override
    public boolean delete(TransportService transportService) {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_TRANSPORT_SERVICE)){
            ps.setInt(1, transportService.getTransportCompany().getId());
            ps.setInt(2, transportService.getFromCity().getId());
            ps.setInt(2, transportService.getToCity().getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete transport service: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<TransportService> readAll() {
        List<TransportService> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_TRANSPORT_SERVICE);
             ResultSet rs = ps.executeQuery()) {
            addTransportCompaniesToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read all transport companies: " + e.getMessage(), e);
        }
        return result;
    }

    private void addTransportCompaniesToList
            (List<TransportService> result, ResultSet rs) throws SQLException {
        while(rs.next()) {
            CityDAO cityDao = new CityDAO(con);
            TransportCompanyDAO tcDao = new TransportCompanyDAO(con);

            City fromCity = cityDao.read(rs.getInt(Fields.TRANSPORT_SERVICE_FROM_CITY));
            City toCity = cityDao.read(rs.getInt(Fields.TRANSPORT_SERVICE_TO_CITY));
            TransportCompany tc = tcDao.read(rs.getInt(Fields.TRANSPORT_SERVICE_COMPANY_ID));

            Integer[] keys = new Integer[3];
            keys[0] = tc.getId();
            keys[1] = fromCity.getId();
            keys[2] = toCity.getId();

            result.add(read(keys));
        }
    }

}
