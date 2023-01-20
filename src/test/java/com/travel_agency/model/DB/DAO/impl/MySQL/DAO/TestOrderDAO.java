package com.travel_agency.model.DB.DAO.impl.MySQL.DAO;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOrderDAO;
import com.travel_agency.model.entity.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class TestOrderDAO {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private MySQLOrderDAO dao;
    private Order order;

    @BeforeAll
    void initialize(){
        User user = new User(0, "test@email.com", "password", "user", "Test", "User", "1234567890",false);
        Offer offer = new Offer(0,"123","City","rest", "Inn", "Name",100,0.15,false,150);
        order = new Order(0,"123", user, offer,"registered");
        con = Mockito.mock(Connection.class);
        dao = new MySQLOrderDAO(con);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }

    @Test
    void testCreate() throws SQLException {
        MockitoDAOSetUp.CreateOrder(order, con,ps,rs);

        boolean result = dao.create(order);

        assertTrue(result);
    }

    @Test
    void testRead() throws Exception {
        MockitoDAOSetUp.ReadOrder(order,true,con,ps,rs);

        Order result = dao.read(order.getCode());

        assertEquals(order, result);
    }

    @Test
    void testUpdateStatus() throws SQLException {
        String newStatus = "canceled";

        MockitoDAOSetUp.UpdateOrderStatus(order, con,ps, rs);

        boolean result = dao.update(order, newStatus);
        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoDAOSetUp.DeleteOrder(order,con,ps);

        boolean result = dao.delete(order.getCode());

        assertTrue(result);
    }

    @Test
    void testReadAll() throws SQLException {
        MockitoDAOSetUp.ReadAllOrders(order,con,ps,rs);

        List<Order> expected = new ArrayList<>();
        expected.add(order);

        List<Order> offers = dao.readAll(0,6);

        assertEquals(expected, offers);
        assertEquals(0, dao.getNumberOfPages());
        assertEquals(0, dao.getNumberOfUserPages());
    }
    @Test
    void testReadAllOrderStatuses() throws SQLException {
        MockitoDAOSetUp.ReadAllOrderStatuses(con,ps,rs);

        List<String> expected = new ArrayList<>();
        expected.add("registered");
        expected.add("paid");
        expected.add("canceled");

        List<String> userRoles = dao.readAllOrderStatuses();

        assertEquals(expected, userRoles);
    }

}

