package com.travel_agency.model.DB.DAO.impl.DAO;

import com.travel_agency.utils.Constants.MySQLDAOConstants;
import com.travel_agency.model.DB.Fields;
import com.travel_agency.model.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class MockitoDAOSetUp {
    private MockitoDAOSetUp() {
    }

    //All user setups
    protected static void createUser(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.ADD_USER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER_ROLE_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
    }

    protected static void readUser(User user, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER_BY_ID)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER_ROLE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet)
            when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.USER_ID)).thenReturn(user.getId());
        when(rs.getString(Fields.USER_EMAIL)).thenReturn(user.getEmail());
        when(rs.getString(Fields.USER_PASSWORD)).thenReturn(user.getPassword());
        when(rs.getString(Fields.USER_FIRST_NAME)).thenReturn(user.getFirstName());
        when(rs.getString(Fields.USER_LAST_NAME)).thenReturn(user.getLastName());
        when(rs.getString(Fields.USER_PHONE)).thenReturn(user.getPhone());
        when(rs.getBoolean(Fields.USER_BLOCKED)).thenReturn(user.isBlocked());
        when(rs.getString(Fields.USER_ROLE_NAME)).thenReturn(user.getUserRole());
        when(rs.getInt(Fields.USER_ROLE)).thenReturn(1);
    }

    protected static void updateRole(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_USER_ROLE))
                .thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER_ROLE_BY_NAME))
                .thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
    }

    protected static void updateBlocked(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_USER_BLOCKED))
                .thenReturn(ps);
    }

    protected static void deleteUser(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.DELETE_USER)).thenReturn(ps);
    }

    protected static void readAllUsers(User user, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_USERS)).thenReturn(ps);
        when(ps.executeQuery(MySQLDAOConstants.USER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addUsersToList
                .thenReturn(true)//for read user
                .thenReturn(true)//for read user role
                .thenReturn(false);//for addUsersToList

        MockitoDAOSetUp.readUser(user, false, con, ps, rs);
    }

    protected static void readAllUserRoles(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_USER_ROLES)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER_ROLE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addUsersToList
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(false);//for addUsersToList

        when(rs.getString(Fields.USER_ROLE_NAME)).thenReturn("user")
                .thenReturn("manager")
                .thenReturn("admin");
    }

    //All hotel setups

    protected static void createHotel(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.ADD_HOTEL)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
    }

    protected static void readHotel(Hotel hotel, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_HOTEL)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_BY_NAME)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet)
            when(rs.next()).thenReturn(true);

        when(rs.getInt(Fields.HOTEL_ID)).thenReturn(hotel.getId());
        when(rs.getString(Fields.HOTEL_NAME)).thenReturn(hotel.getName());
        when(rs.getString(Fields.HOTEL_CITY)).thenReturn(hotel.getCity());
        when(rs.getInt(Fields.HOTEL_TYPE)).thenReturn(1);
        when(rs.getString(Fields.HOTEL_TYPE_NAME)).thenReturn(hotel.getType());
    }

    protected static void deleteHotel(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.DELETE_HOTEL)).thenReturn(ps);
    }

    protected static void readAllHotelTypes(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_HOTEL_TYPES)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addUsersToList
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(false);//for addUsersToList

        when(rs.getString(Fields.HOTEL_TYPE_NAME)).thenReturn("Chain hotels")
                .thenReturn("Motels")
                .thenReturn("Resorts")
                .thenReturn("Inns")
                .thenReturn("All-suites")
                .thenReturn("Conference");
    }

    //All offer setups
    protected static void createOffer(Offer offer ,Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.ADD_OFFER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER_TYPE_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        createHotel(con,ps,rs);
        readHotel(offer.getHotel(), false,con,ps,rs);
    }

    protected static void readOffer(Offer offer, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER_BY_ID)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER_TYPE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet)
            when(rs.next()).thenReturn(true);

        when(rs.getInt(Fields.OFFER_ID)).thenReturn(offer.getId());
        when(rs.getString(Fields.OFFER_CODE)).thenReturn(offer.getCode());
        when(rs.getInt(Fields.OFFER_HOTEL)).thenReturn(offer.getHotel().getId());
        when(rs.getInt(Fields.OFFER_TYPE)).thenReturn(1);
        when(rs.getInt(Fields.OFFER_PLACES)).thenReturn(offer.getPlaces());
        when(rs.getDouble(Fields.OFFER_DISCOUNT)).thenReturn(offer.getDiscount());
        when(rs.getBoolean(Fields.OFFER_IS_HOT)).thenReturn(offer.isHot());
        when(rs.getBoolean(Fields.OFFER_ACTIVE)).thenReturn(offer.isActive());
        when(rs.getDouble(Fields.OFFER_PRICE)).thenReturn(offer.getPrice());
        when(rs.getString(Fields.OFFER_TYPE_NAME)).thenReturn(offer.getOfferType());
        readHotel(offer.getHotel(), false, con, ps, rs);
    }

    protected static void updateOfferIsHot(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_OFFER_IS_HOT)).thenReturn(ps);
    }

    protected static void updateOfferVacancy(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_OFFER_PLACES)).thenReturn(ps);
    }

    protected static void updateOfferActive(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_OFFER_ACTIVE)).thenReturn(ps);
    }

    protected static void deleteOffer(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.DELETE_OFFER)).thenReturn(ps);
    }

    protected static void readAllOffersSorted(Offer offer, String command, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(command)).thenReturn(ps);
        when(ps.executeQuery(MySQLDAOConstants.OFFER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addOffersToList
                .thenReturn(true)//for read offer
                .thenReturn(true)//for read offer type
                .thenReturn(true)//for read hotel
                .thenReturn(true)//for read hotel type
                .thenReturn(false);//for addHotelsToList

        readOffer(offer, false, con, ps, rs);
    }

    protected static void readAllOffers(Offer offer, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_OFFERS)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_HOT_OFFERS)).thenReturn(ps);
        when(ps.executeQuery(MySQLDAOConstants.NOT_HOT_OFFER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery(MySQLDAOConstants.HOT_OFFER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addOffersToList
                .thenReturn(true)//for read offer
                .thenReturn(true)//for read offer type
                .thenReturn(true)//for read hotel
                .thenReturn(true)//for read hotel type
                .thenReturn(false);//for addHotelsToList

        readOffer(offer, false, con, ps, rs);
    }

    protected static void readAllOfferTypes(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_OFFER_TYPES)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER_TYPE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addUsersToList
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(false);//for addUsersToList

        when(rs.getString(Fields.OFFER_TYPE_NAME)).thenReturn("rest")
                .thenReturn("excursion")
                .thenReturn("shopping");
    }

    //All order setups
    protected static void createOrder(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.ADD_ORDER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
    }

    protected static void readOrder(Order order, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet)
            when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.ORDER_ID)).thenReturn(order.getId());
        when(rs.getString(Fields.ORDER_CODE)).thenReturn(order.getCode());
        when(rs.getInt(Fields.ORDER_USER)).thenReturn(0);
        when(rs.getInt(Fields.ORDER_OFFER)).thenReturn(0);
        when(rs.getInt(Fields.ORDER_STATUS)).thenReturn(1);
        when(rs.getString(Fields.ORDER_STATUS_NAME)).thenReturn(order.getOrderStatus());
        readOffer(order.getOffer(), false, con, ps, rs);
        readUser(order.getUser(), false, con, ps, rs);
    }

    protected static void updateOrderStatus(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_ORDER_STATUS)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
    }

    protected static void deleteOrder(Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.DELETE_ORDER)).thenReturn(ps);
    }

    protected static void readAllOrders(Order order, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_ORDERS)).thenReturn(ps);
        when(ps.executeQuery(MySQLDAOConstants.ORDER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addOrdersToList
                .thenReturn(true)//for read order
                .thenReturn(true)//for read order status
                .thenReturn(true)//for read user
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read offer
                .thenReturn(true)//for read offer type
                .thenReturn(true)//for read hotel type
                .thenReturn(true)//for read hotel
                .thenReturn(false);//for addOrdersToList

        readOrder(order, false, con, ps, rs);
    }

    protected static void readAllOrderStatuses(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_ORDER_STATUS)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addUsersToList
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read user role
                .thenReturn(false);//for addUsersToList

        when(rs.getString(Fields.ORDER_STATUS_NAME)).thenReturn("registered")
                .thenReturn("paid")
                .thenReturn("canceled");
    }
}
