package org.travel_agency.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.DAO.HotelDAO;
import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.Fields;
import com.travel_agency.models.DAO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class MockitoSetUp {
    private MockitoSetUp() {
    }

    //All user setups
    protected static void ReadUser(User user, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_USER)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_USER_BY_ID)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_USER_ROLE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet)
            when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.USER_ID)).thenReturn(user.getId());
        when(rs.getString(Fields.USER_EMAIL)).thenReturn(user.getEmail());
        when(rs.getString(Fields.USER_PASSWORD)).thenReturn(user.getPassword());
        when(rs.getString(Fields.USER_FIRST_NAME)).thenReturn(user.getFirstName());
        when(rs.getString(Fields.USER_LAST_NAME)).thenReturn(user.getLastName());
        when(rs.getString(Fields.USER_PHONE)).thenReturn(user.getPhone());
        when(rs.getString(Fields.USER_ROLE_NAME)).thenReturn(user.getUserRole());
        when(rs.getInt(Fields.USER_ROLE)).thenReturn(1);
    }

    protected static void CreateUser(User user, UserDAO dao, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.ADD_USER)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_USER_ROLE_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setInt(3, dao.readUserRole(user.getUserRole()));
        ps.setString(4, user.getFirstName());
        ps.setString(5, user.getLastName());
        ps.setString(6, user.getPhone());
    }

    protected static void UpdateUserEmail(User user, String newEmail, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_USER_EMAIL)).thenReturn(ps);
        ps.setString(1, newEmail);
        ps.setString(2, user.getPhone());
    }

    protected static void UpdateRole(User user, String newRole, UserDAO dao, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_USER_ROLE))
                .thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_USER_ROLE_BY_NAME))
                .thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setString(1, newRole);
        int roleId = dao.readUserRole(newRole);
        ps.setInt(1, roleId);
        ps.setString(2, user.getEmail());
    }

    protected static void DeleteUser(User user, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.DELETE_USER)).thenReturn(ps);
        ps.setString(1, user.getEmail());
        when(ps.executeUpdate()).thenReturn(1);
    }

    protected static void ReadAllUsers(User user, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_USERS)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addUsersToList
                .thenReturn(true)//for read user
                .thenReturn(true)//for read user role
                .thenReturn(false);//for addUsersToList

        MockitoSetUp.ReadUser(user, false, con, ps, rs);
    }


    //All hotel setups
    protected static void CreateHotel(Hotel hotel, HotelDAO dao, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.ADD_HOTEL)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_HOTEL_TYPE_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setString(1, hotel.getName());
        ps.setString(2, hotel.getAddress());
        ps.setInt(4, dao.readHotelType(hotel.getHotelType()));
        ps.setInt(5, hotel.getVacancy());
        ps.setDouble(5, hotel.getPrice());
    }

    protected static void ReadHotel(Hotel hotel, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_HOTEL_BY_ID)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_HOTEL)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_HOTEL_TYPE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet)
            when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.HOTEL_ID)).thenReturn(hotel.getId());
        when(rs.getString(Fields.HOTEL_NAME)).thenReturn(hotel.getName());
        when(rs.getString(Fields.HOTEL_ADDRESS)).thenReturn(hotel.getAddress());
        when(rs.getInt(Fields.HOTEL_VACANCY)).thenReturn(hotel.getVacancy());
        when(rs.getDouble(Fields.HOTEL_PRICE)).thenReturn(hotel.getPrice());
        when(rs.getString(Fields.HOTEL_TYPE_NAME)).thenReturn(hotel.getHotelType());
        when(rs.getInt(Fields.HOTEL_TYPE)).thenReturn(2);
    }

    protected static void UpdateHotelName(Hotel hotel, String newName, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_HOTEL_NAME)).thenReturn(ps);
        ps.setString(1, newName);
        ps.setString(2, hotel.getAddress());
    }

    protected static void UpdateHotelVacancy(Hotel hotel, int newVacancy, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_HOTEL_VACANCY)).thenReturn(ps);
        ps.setInt(1, newVacancy);
        ps.setString(2, hotel.getName());
    }

    protected static void DeleteHotel(Hotel hotel, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.DELETE_HOTEL)).thenReturn(ps);
        ps.setString(1, hotel.getName());
    }

    protected static void ReadAllHotels(Hotel hotel, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_HOTELS)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addHotelsToList
                .thenReturn(true)//for read hotel
                .thenReturn(true)//for read hotel type
                .thenReturn(false);//for addHotelsToList

        ReadHotel(hotel, false, con, ps, rs);
    }


    //All transport company setups
    protected static void CreateTransportCompany(TransportCompany tc, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.ADD_TRANSPORT_COMPANY)).thenReturn(ps);
        ps.setString(1, tc.getName());
        ps.setString(2, tc.getHqAddress());
        ps.setInt(3, tc.getVacancy());
        ps.setDouble(3, tc.getPrice());
    }

    protected static void ReadTransportCompany(TransportCompany tc, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_TRANSPORT_COMPANY)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_TRANSPORT_COMPANY_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet)
            when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.TRANSPORT_COMPANY_ID)).thenReturn(tc.getId());
        when(rs.getString(Fields.TRANSPORT_COMPANY_NAME)).thenReturn(tc.getName());
        when(rs.getString(Fields.TRANSPORT_COMPANY_HQ_ADDRESS)).thenReturn(tc.getHqAddress());
        when(rs.getInt(Fields.TRANSPORT_COMPANY_VACANCY)).thenReturn(tc.getVacancy());
    }

    protected static void UpdateTransportCompanyName(TransportCompany tc, String newName, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_TRANSPORT_COMPANY_NAME)).thenReturn(ps);
        ps.setString(1, newName);
        ps.setString(2, tc.getHqAddress());
    }

    protected static void UpdateTransportCompanyVacancy(TransportCompany tc, int newVacancy, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_TRANSPORT_COMPANY_VACANCY)).thenReturn(ps);
        ps.setInt(1, newVacancy);
        ps.setString(2, tc.getName());
    }

    protected static void DeleteTransportCompany(TransportCompany tc, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.DELETE_TRANSPORT_COMPANY)).thenReturn(ps);
        ps.setString(1, tc.getName());
    }

    protected static void ReadAllTransportCompany(TransportCompany tc, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_TRANSPORT_COMPANY)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addHotelsToList
                .thenReturn(true)//for read hotel
                .thenReturn(false);//for addHotelsToList

        ReadTransportCompany(tc, false, con, ps, rs);
    }


    //All offer setups
    protected static void CreateOffer(Offer offer, OfferDAO dao, Connection con, PreparedStatement os, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.ADD_OFFER)).thenReturn(os);
        when(con.prepareStatement(Constants.FIND_OFFER_TYPE_BY_NAME)).thenReturn(os);
        when(os.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        os.setString(1, offer.getCode());
        os.setInt(2, dao.readOfferType(offer.getType()));
        os.setInt(3, offer.getTransportCompany().getId());
        os.setInt(4, offer.getHotel().getId());
        os.setInt(5, offer.getVacancy());
        os.setDouble(6, offer.getDiscount());
        os.setBoolean(7, offer.isHot());
        os.setDouble(8, offer.getPrice());
    }

    protected static void ReadOffer(Offer offer, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_OFFER)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_OFFER_BY_ID)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_OFFER_TYPE_BY_ID)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        if (withResultSet) when(rs.next()).thenReturn(true);
        when(rs.getInt(Fields.OFFER_ID)).thenReturn(offer.getId());
        when(rs.getString(Fields.OFFER_CODE)).thenReturn(offer.getCode());
        when(rs.getInt(Fields.OFFER_TRANSPORT_COMPANY)).thenReturn(0);
        when(rs.getInt(Fields.OFFER_HOTEL)).thenReturn(0);
        when(rs.getInt(Fields.OFFER_TYPE)).thenReturn(2);
        when(rs.getDouble(Fields.OFFER_DISCOUNT)).thenReturn(offer.getDiscount());
        when(rs.getBoolean(Fields.OFFER_IS_HOT)).thenReturn(offer.isHot());
        when(rs.getString(Fields.OFFER_TYPE_NAME)).thenReturn(offer.getType());
        ReadTransportCompany(offer.getTransportCompany(), false, con, ps, rs);
        ReadHotel(offer.getHotel(), false, con, ps, rs);
    }

    protected static void UpdateOfferIsHot(Offer offer, boolean isHot, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_OFFER_IS_HOT)).thenReturn(ps);
        ps.setBoolean(1, isHot);
        ps.setString(2, offer.getCode());
    }

    protected static void UpdateOfferVacancy(Offer offer, int vacancy, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.CHANGE_OFFER_VACANCY)).thenReturn(ps);
        ps.setInt(1, vacancy);
        ps.setString(2, offer.getCode());
    }

    protected static void DeleteOffer(Offer offer, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.DELETE_OFFER)).thenReturn(ps);
        ps.setString(1, offer.getCode());
    }

    protected static void ReadAllOffers(Offer offer, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_OFFERS)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addOffersToList
                .thenReturn(true)//for read offer
                .thenReturn(true)//for read offer type
                .thenReturn(true)//for read hotel
                .thenReturn(true)//for read hotel type
                .thenReturn(true)//for read transport company
                .thenReturn(false);//for addHotelsToList

        ReadOffer(offer, false, con, ps, rs);
    }

    //All order setups
    protected static void CreateOrder(Order order, OrderDAO dao, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.ADD_ORDER)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_ORDER_STATUS_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setString(1, order.getCode());
        ps.setInt(2, order.getUser().getId());
        ps.setInt(3, order.getOffer().getId());
        ps.setInt(4, dao.readOrderStatus(order.getOrderStatus()));
        ps.setDouble(5, order.getPrice());
    }

    protected static void ReadOrder(Order order, boolean withResultSet, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_ORDER)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_ORDER_STATUS_BY_ID)).thenReturn(ps);
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
        when(con.prepareStatement(Constants.CHANGE_ORDER_STATUS)).thenReturn(ps);
        when(con.prepareStatement(Constants.FIND_ORDER_STATUS_BY_NAME)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        ps.setInt(1, 3);
        ps.setString(2, order.getCode());
    }

    protected static void DeleteOrder(Order order, Connection con, PreparedStatement ps) throws SQLException {
        when(con.prepareStatement(Constants.DELETE_ORDER)).thenReturn(ps);
        ps.setString(1, order.getCode());
    }

    protected static void ReadAllOrders(Order order, Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        when(con.prepareStatement(Constants.FIND_ALL_ORDERS)).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true)//for addOrdersToList
                .thenReturn(true)//for read order
                .thenReturn(true)//for read order status
                .thenReturn(true)//for read user
                .thenReturn(true)//for read user role
                .thenReturn(true)//for read offer
                .thenReturn(true)//for read offer type
                .thenReturn(true)//for read hotel
                .thenReturn(true)//for read hotel type
                .thenReturn(true)//for read transport company
                .thenReturn(false);//for addOrdersToList

        ReadOrder(order, false, con, ps, rs);
    }
}
