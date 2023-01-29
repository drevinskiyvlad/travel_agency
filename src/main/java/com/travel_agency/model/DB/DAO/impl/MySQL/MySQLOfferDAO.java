package com.travel_agency.model.DB.DAO.impl.MySQL;

import com.travel_agency.exceptions.DAOException;
import com.travel_agency.model.DB.DAO.OfferDAO;
import com.travel_agency.model.DB.Fields;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.utils.Constants.MySQLDAOConstants;
import com.travel_agency.utils.Constants.SORTING_BY;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of DAO interface for MySQL
 */
public class MySQLOfferDAO implements OfferDAO<Offer> {
    private final Connection con;

    private double numberOfNotHotPages; // for pagination
    private double numberOfHotPages; // for pagination
    private int numberOfHotRecords; // for pagination
    @Setter private int page; // for pagination

    /**
     * Constructor
     */
    public MySQLOfferDAO(Connection con) {
        this.con = con;
    }


    public boolean create(Offer offer) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.ADD_OFFER)) {

            setVariablesToCreateStatement(offer, ps);
            ps.executeUpdate();

            return true;
        } catch (SQLException | IllegalArgumentException e) {
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
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_OFFER)) {

            ps.setString(1, code);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeOffer(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Unable to read offer: " + e.getMessage());
        } finally {
            close(rs);
        }
        return null;
    }

    public Offer read(int id) throws DAOException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_OFFER_BY_ID)) {

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return initializeOffer(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Unable to read offer: " + e.getMessage());
        } finally {
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

        return new Offer(id, code, city, offerType, hotelType, hotelName, places, discount, isHot, price);
    }

    public boolean update(Offer offer, boolean isHot) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.CHANGE_OFFER_IS_HOT)) {
            ps.setBoolean(1, isHot);
            ps.setString(2, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Unable to update offer is hot: " + e.getMessage());
        }
    }

    public boolean update(Offer offer, int newValue) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.CHANGE_OFFER_PLACES)) {
            ps.setInt(1, newValue);
            ps.setString(2, offer.getCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Unable to update offer places: " + e.getMessage());
        }
    }

    public boolean delete(String code) throws DAOException {
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.DELETE_OFFER)) {
            ps.setString(1, code);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Unable to delete offer: " + e.getMessage());
        }
    }

    public List<Offer> readAll(int offset, int numOfRecords, boolean onlyHot) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>(readAllHot(offset, numOfRecords));
        if (!onlyHot)
            result.addAll(readAllNotHot(offset, numOfRecords));
        return result;
    }

    private List<Offer> readAllNotHot(int offset, int numOfRecords) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>();
        int numOfNotHotRecords = numOfRecords;
        if(page <= (int)Math.ceil(numberOfHotPages)) {
            numOfNotHotRecords = numOfRecords - numberOfHotRecords;
        }
        int notHotOffset = offset - numberOfHotRecords;
        if (notHotOffset < 0) notHotOffset = 0;

        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_OFFERS)) {
            ps.setInt(1, notHotOffset);
            ps.setInt(2, numOfNotHotRecords);
            rs = ps.executeQuery();
            addOffersToList(result, rs);
            rs.close();

            rs = ps.executeQuery(MySQLDAOConstants.NOT_HOT_OFFER_GET_NUMBER_OF_RECORDS);
            if (rs.next())
                numberOfNotHotPages = rs.getInt(1) * 1.0 / numOfRecords;
        } catch (SQLException e) {
            throw new DAOException("Unable to read list of not hot offers: " + e.getMessage());
        } finally {
            close(rs);
        }
        return result;
    }

    private List<Offer> readAllHot(int offset, int numOfRecords) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_HOT_OFFERS)) {
            ps.setInt(1, offset);
            ps.setInt(2, numOfRecords);
            rs = ps.executeQuery();
            addOffersToList(result, rs);
            rs.close();

            rs = ps.executeQuery(MySQLDAOConstants.HOT_OFFER_GET_NUMBER_OF_RECORDS);
            if (rs.next()) {
                numberOfHotRecords = rs.getInt(1);
                numberOfHotPages = numberOfHotRecords * 1.0 / numOfRecords;
            }

        } catch (SQLException e) {
            throw new DAOException("Unable to read list of hot offer: " + e.getMessage());
        } finally {
            close(rs);
        }
        return result;
    }

    public List<Offer> readAllSorted(int offset, int numOfRecords, SORTING_BY sortBy) throws DAOException {
        List<Offer> result = new CopyOnWriteArrayList<>();

        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(sortBy.getCommand())) {
            ps.setInt(1, offset);
            ps.setInt(2, numOfRecords);
            rs = ps.executeQuery();
            addOffersToList(result, rs);
            rs.close();

            rs = ps.executeQuery(MySQLDAOConstants.OFFER_GET_NUMBER_OF_RECORDS);
            if (rs.next()) {
                numberOfHotPages = 0;
                numberOfNotHotPages = rs.getInt(1) * 1.0 / numOfRecords;
            }
        } catch (SQLException e) {
            throw new DAOException("Unable to read list of not hot offers: " + e.getMessage());
        } finally {
            close(rs);
        }
        return result;
    }

    public List<String> readAllHotelTypes() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_HOTEL_TYPES);
             ResultSet rs = ps.executeQuery()) {
            addHotelTypesToList(result, rs);
        } catch (SQLException e) {
            throw new DAOException("Unable to read list hotel types: " + e.getMessage());
        }
        return result;
    }

    public List<String> readAllOfferTypes() throws DAOException {
        List<String> result = new CopyOnWriteArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_ALL_OFFER_TYPES);
             ResultSet rs = ps.executeQuery()) {
            addOfferTypesToList(result, rs);
        } catch (SQLException e) {
            throw new DAOException("Unable to read list hotel types: " + e.getMessage());
        }
        return result;
    }

    private int readOfferType(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_OFFER_TYPE_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.OFFER_TYPE_ID);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown offer type name");
        } finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown offer type name");
    }

    private String readOfferType(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_OFFER_TYPE_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.OFFER_TYPE_NAME);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown offer type name");
        } finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown offer type name");
    }

    private int readHotelType(String name) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_NAME)) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(Fields.HOTEL_TYPE_ID);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown hotel type name");
        } finally {
            close(rs);
        }
        throw new IllegalArgumentException("Unknown hotel type name");
    }

    private String readHotelType(int id) throws IllegalArgumentException {
        ResultSet rs = null;
        try (PreparedStatement ps = con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(Fields.HOTEL_TYPE_NAME);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("Unknown hotel type name");
        } finally {
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


    public int getNumberOfPages() {
        return (int) Math.ceil(numberOfHotPages + numberOfNotHotPages);
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
