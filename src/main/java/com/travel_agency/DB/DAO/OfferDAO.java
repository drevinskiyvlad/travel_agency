package com.travel_agency.DB.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.Fields;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.Hotel;
import com.travel_agency.models.DAO.Offer;
import com.travel_agency.models.DAO.TransportCompany;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OfferDAO implements DAO<Offer, String>{
    private static final Logger logger = LogManager.getLogger(OfferDAO.class);
    private final Connection con;

    public OfferDAO(Connection con) {
        this.con = con;
    }

    @Override
    public boolean create(Offer offer) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.ADD_OFFER)) {

            setVariablesToCreateStatement(offer, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
            logger.error("Unable to create offer: " + e.getMessage(), e);
            throw new DAOException("Unable to create offer: " + e.getMessage());
        }
    }
    private void setVariablesToCreateStatement(Offer offer, PreparedStatement ps) throws SQLException, IllegalArgumentException {
        ps.setString(1, offer.getCode());
        ps.setInt(2, readOfferType(offer.getType()));
        ps.setInt(3, offer.getTransportCompany().getId());
        ps.setInt(4, offer.getHotel().getId());
        ps.setInt(5, offer.getVacancy());
        ps.setDouble(6, offer.getDiscount());
        ps.setBoolean(7, offer.isHot());
        ps.setDouble(8, offer.getPrice());

    }


    @Override
    public Offer read(String code) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_OFFER)) {

            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeOffer(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read offer: " + e.getMessage(), e);
            throw new DAOException("Unable to read offer: " + e.getMessage());
        }finally {
            close(rs);
        }
        return null;
    }

    public Offer read(int id) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_OFFER_BY_ID)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeOffer(rs);
            }
        } catch (SQLException e) {
            logger.error("Unable to read offer: " + e.getMessage(), e);
            throw new DAOException("Unable to read offer: " + e.getMessage());
        }finally {
            close(rs);
        }
        return null;
    }

    private Offer initializeOffer(ResultSet rs) throws SQLException {
        HotelDAO hotelDao = new HotelDAO(con);
        TransportCompanyDAO tcDao = new TransportCompanyDAO(con);

        String type;
        int id = rs.getInt(Fields.OFFER_ID);
        String code = rs.getString(Fields.OFFER_CODE);
        TransportCompany tc = tcDao.read(rs.getInt(Fields.OFFER_TRANSPORT_COMPANY));
        Hotel hotel = hotelDao.read(rs.getInt(Fields.OFFER_HOTEL));
        double discount = rs.getDouble(Fields.OFFER_DISCOUNT);
        boolean isHot = rs.getBoolean(Fields.OFFER_IS_HOT);

        try {
            type = readOfferType(rs.getInt(Fields.OFFER_TYPE));
        } catch (IllegalArgumentException e) {
            return null;
        }

        return new Offer(id,code,type,tc,hotel,discount,isHot);
    }
    @Override
    public boolean update(Offer offer, String newCode) throws UnsupportedOperationException {
        String message = "Update in offer is unsupported";
        logger.error(message);
        throw new UnsupportedOperationException(message);
    }

    public boolean update(Offer offer, boolean isHot) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_OFFER_IS_HOT)) {
            ps.setBoolean(1, isHot);
            ps.setString(2, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update offer is hot: " + e.getMessage(), e);
            throw new DAOException("Unable to update offer is hot: " + e.getMessage());
        }
    }

    public boolean update(Offer offer, int newVacancy) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_OFFER_VACANCY)) {
            ps.setInt(1, newVacancy);
            ps.setString(2, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update offer vacancy: " + e.getMessage(), e);
            throw new DAOException("Unable to update offer vacancy: " + e.getMessage());
        }
    }


    @Override
    public boolean delete(Offer offer) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.DELETE_OFFER)) {
            ps.setString(1, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete offer: " + e.getMessage(), e);
            throw new DAOException("Unable to delete offer: " + e.getMessage());
        }
    }

    @Override
    public List<Offer> readAll() throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_OFFERS);
             ResultSet rs = ps.executeQuery()) {
            addOffersToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read list of offers: " + e.getMessage(), e);
            throw new DAOException("Unable to read list offer: " + e.getMessage());
        }
        return result;
    }

    public int readOfferType(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_OFFER_TYPE_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.OFFER_TYPE_ID);
            }

        } catch (SQLException e) {
            logger.error("Unable to read offer type: " + e.getMessage(), e);
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown offer type name");
    }

    public String readOfferType(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_OFFER_TYPE_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.OFFER_TYPE_NAME);
            }

        } catch (SQLException e) {
            logger.error("Unable to read offer type: " + e.getMessage(), e);
        }finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown offer type name");
    }



    private void addOffersToList(List<Offer> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            String code = rs.getString(Fields.OFFER_CODE);
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
