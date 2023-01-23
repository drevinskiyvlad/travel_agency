package com.travel_agency.model.DB.DAO.impl.MySQL.DAO;

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
    protected static void CreateUser(User user, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.ADD_USER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER_ROLE_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setInt(3, 1);
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getLastName());
        ps.setString(6, user.getPhone());
        ps.setBoolean(7, user.isBlocked());
    }

    protected static void ReadUser(User user, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
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

    protected static void UpdateRole(User user, String newRole, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_USER_ROLE))
                .thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_USER_ROLE_BY_NAME))
                .thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setInt(1, 3);
        ps.setString(2, user.getEmail());
    }

    protected static void UpdateBlocked(User user, boolean blocked, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_USER_BLOCKED))
                .thenReturn(ps);
        ps.setBoolean(1, blocked);
        ps.setString(2, user.getEmail());
    }

    protected static void DeleteUser(User user, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.DELETE_USER)).thenReturn(ps);
        ps.setString(1, user.getEmail());
        when(ps.executeUpdate()).thenReturn(1);
    }

    protected static void ReadAllUsers(User user, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_USERS)).thenReturn(ps);
        when(ps.executeQuery(MySQLDAOConstants.USER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addUsersToList
                .thenReturn(true)//for read user
                .thenReturn(true)//for read user role
                .thenReturn(false);//for addUsersToList

        MockitoDAOSetUp.ReadUser(user, false, con, ps, rs);
    }

    protected static void ReadAllUserRoles(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
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


    //All offer setups
    protected static void CreateOffer(Offer offer, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.ADD_OFFER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER_TYPE_BY_NAME)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setString(1, offer.getCode());
        ps.setString(2, offer.getCity());
        ps.setInt(3, 1);
        ps.setInt(4, 1);
        ps.setString(5, offer.getHotelName());
        ps.setInt(6, offer.getPlaces());
        ps.setDouble(7, offer.getDiscount());
        ps.setBoolean(8, offer.isHot());
        ps.setDouble(9, offer.getPrice());
    }

    protected static void ReadOffer(Offer offer, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER_BY_ID)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_OFFER_TYPE_BY_ID)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_HOTEL_TYPE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet) when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.OFFER_ID)).thenReturn(offer.getId());
        when(rs.getString(Fields.OFFER_CODE)).thenReturn(offer.getCode());
        when(rs.getString(Fields.OFFER_CITY)).thenReturn(offer.getCity());
        when(rs.getString(Fields.HOTEL_NAME)).thenReturn(offer.getHotelName());
        when(rs.getInt(Fields.OFFER_TYPE)).thenReturn(1);
        when(rs.getInt(Fields.OFFER_HOTEL_TYPE)).thenReturn(4);
        when(rs.getInt(Fields.OFFER_PLACES)).thenReturn(offer.getPlaces());
        when(rs.getDouble(Fields.OFFER_DISCOUNT)).thenReturn(offer.getDiscount());
        when(rs.getBoolean(Fields.OFFER_IS_HOT)).thenReturn(offer.isHot());
        when(rs.getDouble(Fields.OFFER_PRICE)).thenReturn(offer.getPrice());
        when(rs.getString(Fields.OFFER_TYPE_NAME)).thenReturn(offer.getOfferType());
        when(rs.getString(Fields.HOTEL_TYPE_NAME)).thenReturn(offer.getHotelType());
    }

    protected static void UpdateOfferIsHot(Offer offer, boolean isHot, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_OFFER_IS_HOT)).thenReturn(ps);
        ps.setBoolean(1, isHot);
        ps.setString(2, offer.getCode());
    }

    protected static void UpdateOfferVacancy(Offer offer, int vacancy, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_OFFER_PLACES)).thenReturn(ps);
        ps.setInt(1, vacancy);
        ps.setString(2, offer.getCode());
    }

    protected static void DeleteOffer(Offer offer, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.DELETE_OFFER)).thenReturn(ps);
        ps.setString(1, offer.getCode());
    }

    protected static void ReadAllOffers(Offer offer, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_OFFERS)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ALL_HOT_OFFERS)).thenReturn(ps);
        when(ps.executeQuery(MySQLDAOConstants.NOT_HOT_OFFER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery(MySQLDAOConstants.HOT_OFFER_GET_NUMBER_OF_RECORDS)).thenReturn(rs);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addOffersToList
                .thenReturn(true)//for read offer
                .thenReturn(true)//for read offer type
                .thenReturn(true)//for read hotel type
                .thenReturn(false);//for addHotelsToList

        ReadOffer(offer, false, con, ps, rs);
    }

    protected static void ReadAllOfferTypes(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
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
    protected static void ReadAllHotelTypes(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
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

    //All order setups
    protected static void CreateOrder(Order order, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.ADD_ORDER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setString(1, order.getCode());
        ps.setInt(2, order.getUser().getId());
        ps.setInt(3, order.getOffer().getId());
        ps.setInt(4, 1);
    }

    protected static void ReadOrder(Order order, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet) when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.ORDER_ID)).thenReturn(order.getId());
        when(rs.getString(Fields.ORDER_CODE)).thenReturn(order.getCode());
        when(rs.getInt(Fields.ORDER_USER)).thenReturn(0);
        when(rs.getInt(Fields.ORDER_OFFER)).thenReturn(0);
        when(rs.getInt(Fields.ORDER_STATUS)).thenReturn(1);
        when(rs.getString(Fields.ORDER_STATUS_NAME)).thenReturn(order.getOrderStatus());
        ReadOffer(order.getOffer(), false, con, ps, rs);
        ReadUser(order.getUser(), false, con, ps, rs);
    }

    protected static void UpdateOrderStatus(Order order, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.CHANGE_ORDER_STATUS)).thenReturn(ps);
        when(con.prepareStatement(MySQLDAOConstants.FIND_ORDER_STATUS_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setInt(1, 3);
        ps.setString(2, order.getCode());
    }

    protected static void DeleteOrder(Order order, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(MySQLDAOConstants.DELETE_ORDER)).thenReturn(ps);
        ps.setString(1, order.getCode());
    }

    protected static void ReadAllOrders(Order order, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
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
                .thenReturn(false);//for addOrdersToList

        ReadOrder(order, false, con, ps, rs);
    }

    protected static void ReadAllOrderStatuses(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
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
