package com.travel_agency.model.DB.DAO.impl.DAO;

import com.travel_agency.model.DB.DAO.impl.HotelDAOImpl;
import com.travel_agency.model.entity.Hotel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelDAOTest {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    private Hotel hotel;
    private HotelDAOImpl dao;

    @BeforeAll
    void setUp() {
        hotel = new Hotel(0, "name", "type", "city");
        con = Mockito.mock(Connection.class);
        dao = new HotelDAOImpl();
    }


    @Test
    void testCreate() throws SQLException {
        MockitoDAOSetUp.createHotel(con,ps,rs);
        boolean result = dao.create(hotel);
        assertTrue(result);
    }

    @Test
    void testRead() throws SQLException {
        MockitoDAOSetUp.readHotel(hotel,true,con,ps,rs);
        int id = hotel.getId();
        Hotel result = dao.read(id);
        assertEquals(hotel, result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoDAOSetUp.deleteHotel(con,ps);
        boolean result = dao.delete(hotel.getId());
        assertTrue(result);
    }

    @Test
    void testReadAllHotelTypes() throws SQLException {
        MockitoDAOSetUp.readAllHotelTypes(con,ps,rs);

        List<String> expectedTypes = new ArrayList<>();
        expectedTypes.add("Chain hotels");
        expectedTypes.add("Motels");
        expectedTypes.add("Resorts");
        expectedTypes.add("Inns");
        expectedTypes.add("All-suites");
        expectedTypes.add("Conference");

        List<String> hotelTypes = dao.readAllHotelTypes();

        assertEquals(expectedTypes, hotelTypes);
    }
}