package com.travel_agency.DB.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.Fields;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.Offer;
import lombok.Getter;
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

    @Getter private int numberOfPages; // for pagination

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
        ps.setString(2, offer.getCity());
        ps.setInt(3, readOfferType(offer.getOfferType()));
        ps.setInt(4, readHotelType(offer.getHotelType()));
        ps.setString(5, offer.getHotelName());
        ps.setInt(6, offer.getPlaces());
        ps.setDouble(7, offer.getDiscount());
        ps.setBoolean(8, offer.isHot());
        ps.setDouble(9, offer.getPrice());
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
        String offerType;
        String hotelType;
        int id = rs.getInt(Fields.OFFER_ID);
        String code = rs.getString(Fields.OFFER_CODE);
        String city = rs.getString(Fields.OFFER_CITY);
        String hotelName = rs.getString(Fields.HOTEL_NAME);
        int places = rs.getInt(Fields.OFFER_PLACES);
        double discount = rs.getDouble(Fields.OFFER_DISCOUNT);
        boolean isHot = rs.getBoolean(Fields.OFFER_IS_HOT);
        double price = rs.getDouble(Fields.OFFER_PRICE);

        try {
            offerType = readOfferType(rs.getInt(Fields.OFFER_TYPE));
            hotelType = readHotelType(rs.getInt(Fields.OFFER_HOTEL_TYPE));
        } catch (IllegalArgumentException e) {
            return null;
        }

        return new Offer(id,code,city,offerType,hotelType,hotelName,places,discount,isHot,price);
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

    public boolean update(Offer offer, int newValue) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(Constants.CHANGE_OFFER_PLACES)) {
            ps.setInt(1, newValue);
            ps.setString(2, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update offer places: " + e.getMessage(), e);
            throw new DAOException("Unable to update offer places: " + e.getMessage());
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
    public List<Offer> readAll(int offset, int numOfRecords) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_OFFERS)){
            ps.setInt(1,offset);
            ps.setInt(2,numOfRecords);
            rs = ps.executeQuery();
            addOffersToList(result, rs);
            rs.close();

            rs = ps.executeQuery(Constants.OFFER_GET_NUMBER_OF_RECORDS);
            if (rs.next())
                numberOfPages = (int)Math.ceil(rs.getInt(1)*1.0 / numOfRecords);

        } catch (SQLException e) {
            logger.error("Unable to read list of offers: " + e.getMessage(), e);
            throw new DAOException("Unable to read list offer: " + e.getMessage());
        }finally{
            close(rs);
        }
        return result;
    }

    public List<String> readAllHotelTypes() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_HOTEL_TYPES);
             ResultSet rs = ps.executeQuery()) {
            addHotelTypesToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read list of hotel types: " + e.getMessage(), e);
            throw new DAOException("Unable to read list hotel types: " + e.getMessage());
        }
        return result;
    }

    public List<String> readAllOfferTypes() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(Constants.FIND_ALL_OFFER_TYPES);
             ResultSet rs = ps.executeQuery()) {
            addOfferTypesToList(result, rs);
        } catch (SQLException e) {
            logger.error("Unable to read list of hotel types: " + e.getMessage(), e);
            throw new DAOException("Unable to read list hotel types: " + e.getMessage());
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

    private void addHotelTypesToList(List<String> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt(Fields.HOTEL_TYPE_ID);
            result.add(readHotelType(id));
        }
    }

    private void addOfferTypesToList(List<String> result, ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt(Fields.HOTEL_TYPE_ID);
            result.add(readOfferType(id));
        }
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
