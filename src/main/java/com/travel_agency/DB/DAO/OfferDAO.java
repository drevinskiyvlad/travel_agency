package com.travel_agency.DB.DAO;

import com.travel_agency.utils.Constants.DAOConstants;
import com.travel_agency.DB.Fields;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.Offer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OfferDAO{
    private static final Logger logger = LogManager.getLogger(OfferDAO.class);
    private final Connection con;

    private double numberOfNotHotPages; // for pagination
    private double numberOfHotPages; // for pagination
    private int numberOfHotRecords; // for pagination

    public OfferDAO(Connection con) {
        this.con = con;
    }


    public boolean create(Offer offer) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.ADD_OFFER)) {

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


    public Offer read(String code) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_OFFER)) {

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
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_OFFER_BY_ID)) {

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

    public boolean update(Offer offer, boolean isHot) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.CHANGE_OFFER_IS_HOT)) {
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
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.CHANGE_OFFER_PLACES)) {
            ps.setInt(1, newValue);
            ps.setString(2, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to update offer places: " + e.getMessage(), e);
            throw new DAOException("Unable to update offer places: " + e.getMessage());
        }
    }



    public boolean delete(Offer offer) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.DELETE_OFFER)) {
            ps.setString(1, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Unable to delete offer: " + e.getMessage(), e);
            throw new DAOException("Unable to delete offer: " + e.getMessage());
        }
    }


    public List<Offer> readAll(int offset, int numOfRecords, boolean onlyHot) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>(readAllHot(offset, numOfRecords));
        if(!onlyHot)
            result.addAll(readAllNotHot(offset,numOfRecords));
        return result;
    }

    private List<Offer> readAllNotHot(int offset, int numOfRecords) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>();

        int numOfNotHotRecords = numOfRecords - numberOfHotRecords;
        int notHotOffset = offset - numberOfHotRecords;
        if(notHotOffset<0) notHotOffset = 0;

        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_ALL_OFFERS)){
            ps.setInt(1,notHotOffset);
            ps.setInt(2,numOfNotHotRecords);
            rs = ps.executeQuery();
            addOffersToList(result, rs);
            rs.close();

            rs = ps.executeQuery(DAOConstants.OFFER_GET_NUMBER_OF_RECORDS);
            if (rs.next())
                numberOfNotHotPages = rs.getInt(1)*1.0 / numOfRecords;

        } catch (SQLException e) {
            logger.error("Unable to read list of not hot offers: " + e.getMessage(), e);
            throw new DAOException("Unable to read list of not hot offers: " + e.getMessage());
        }finally{
            close(rs);
        }
        return result;
    }

    private List<Offer> readAllHot(int offset, int numOfRecords) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_ALL_HOT_OFFERS)){
            ps.setInt(1,offset);
            ps.setInt(2,numOfRecords);
            rs = ps.executeQuery();
            addOffersToList(result, rs);
            rs.close();

            rs = ps.executeQuery(DAOConstants.HOT_OFFER_GET_NUMBER_OF_RECORDS);
            if (rs.next()) {
                numberOfHotRecords = rs.getInt(1);
                numberOfHotPages = numberOfHotRecords * 1.0 / numOfRecords;
            }

        } catch (SQLException e) {
            logger.error("Unable to read list of hot offers: " + e.getMessage(), e);
            throw new DAOException("Unable to read list of hot offer: " + e.getMessage());
        }finally{
            close(rs);
        }
        return result;
    }

    public List<String> readAllHotelTypes() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_ALL_HOTEL_TYPES);
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
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_ALL_OFFER_TYPES);
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
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_OFFER_TYPE_BY_NAME)) {
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
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_OFFER_TYPE_BY_ID)) {
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
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_HOTEL_TYPE_BY_NAME)) {
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
        try (PreparedStatement ps = con.prepareStatement(DAOConstants.FIND_HOTEL_TYPE_BY_ID)) {
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


    public int getNumberOfPages(){
        return (int)Math.ceil(numberOfHotPages + numberOfNotHotPages);
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
