package org.travel_agency.DAO;

import com.travel_agency.DB.DAO.HotelDAO;
import com.travel_agency.models.DAO.Hotel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class TestHotelDAO {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private Hotel hotel;
    HotelDAO dao;

    @BeforeAll
    void initialize(){
        hotel = new Hotel(0,"testHotel","testAddress","Inn",150,200.00);
        con = Mockito.mock(Connection.class);
        dao = new HotelDAO(con);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }
    @Test
    void testCreate() throws SQLException {
        MockitoDAOSetUp.CreateHotel(hotel,dao,con,ps,rs);

        boolean result = dao.create(hotel);

        assertTrue(result);
    }

    @Test
    void testRead() throws Exception {
        MockitoDAOSetUp.ReadHotel(hotel,true,con,ps,rs);

        Hotel result = dao.read(hotel.getId());
        assertEquals(hotel, result);
    }

    @Test
    void testUpdateName() throws SQLException {
        String newName = "newName";

        MockitoDAOSetUp.UpdateHotelName(hotel,newName,con,ps);

        boolean result = dao.update(hotel, newName);

        assertTrue(result);
    }

    @Test
    void testUpdateVacancy() throws SQLException {
        int newVacancy = 100;

        MockitoDAOSetUp.UpdateHotelVacancy(hotel,newVacancy,con,ps);

        boolean result = dao.update(hotel, newVacancy);

        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoDAOSetUp.DeleteHotel(hotel,con,ps);

        boolean result = dao.delete(hotel);

        assertTrue(result);
    }

    @Test
    void testReadAll() throws SQLException {
        MockitoDAOSetUp.ReadAllHotels(hotel,con,ps,rs);

        List<Hotel> expected = new ArrayList<>();
        expected.add(hotel);

        List<Hotel> hotels = dao.readAll();

        assertEquals(expected, hotels);
    }



}

