package org.travel_agency.DAO;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.models.Hotel;
import com.travel_agency.models.Offer;
import com.travel_agency.models.TransportCompany;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
@TestInstance(Lifecycle.PER_CLASS)
class TestOfferDAO {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private Offer offer;
    private OfferDAO dao;

    @BeforeAll
    void initialize(){
        TransportCompany tc = new TransportCompany(0, "testCompany", "testAddress", 150, 200.00);
        Hotel hotel = new Hotel(0, "testHotel", "testAddress", "Inn", 150, 200.00);
        offer = new Offer(0,"123","rest", tc, hotel,0.15,false);
        con = Mockito.mock(Connection.class);
        dao = new OfferDAO(con);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }
    @Test
    void testCreate() throws SQLException {
        MockitoSetUp.CreateOffer(offer,dao,con,ps,rs);

        boolean result = dao.create(offer);

        assertTrue(result);
    }


    @Test
    void testRead() throws Exception {
        MockitoSetUp.ReadOffer(offer,true,con,ps,rs);

        Offer result = dao.read(offer.getCode());

        assertEquals(offer, result);
    }

    @Test
    void testUpdateForException() {
        //todo
    }

    @Test
    void testUpdateIsHot() throws SQLException {
        boolean isHot = true;

        MockitoSetUp.UpdateOfferIsHot(offer,isHot,con,ps);

        boolean result = dao.update(offer, isHot);
        assertTrue(result);
    }



    @Test
    void testUpdateVacancy() throws SQLException {
        int vacancy = 15;

        MockitoSetUp.UpdateOfferVacancy(offer,vacancy,con,ps);

        boolean result = dao.update(offer, vacancy);
        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoSetUp.DeleteOffer(offer,con,ps);

        boolean result = dao.delete(offer);

        assertTrue(result);
    }



    @Test
    void testReadAll() throws SQLException {
        MockitoSetUp.ReadAllOffers(offer,con,ps,rs);

        List<Offer> expected = new ArrayList<>();
        expected.add(offer);

        List<Offer> offers = dao.readAll();

        assertEquals(expected, offers);
    }
}
