package com.travel_agency.model.DB.DAO.impl.MySQL.DAO;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.entity.Hotel;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.utils.Constants.SORTING_BY;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class OfferDAOTest {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private Offer offer;
    private MySQLOfferDAO dao;

    @BeforeAll
    void initialize(){
        Hotel hotel = new Hotel(0, "name", "type", "city");
        offer = new Offer(0,"code", hotel,"type", 100,0.15,false,true,150);
        con = Mockito.mock(Connection.class);
        dao = new MySQLOfferDAO(con);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }
    @Test
    void testCreate() throws SQLException {
        MockitoDAOSetUp.CreateOffer(con,ps,rs);

        boolean result = dao.create(offer);

        assertTrue(result);
    }


    @Test
    void testReadByCode() throws Exception {
        MockitoDAOSetUp.ReadOffer(offer,true,con,ps,rs);

        Offer expected = dao.read(offer.getCode());

        assertEquals(offer, expected);
    }

    @Test
    void testReadById() throws Exception {
        MockitoDAOSetUp.ReadOffer(offer,true,con,ps,rs);

        Offer expected = dao.read(offer.getId());

        assertEquals(offer, expected);
    }

    @Test
    void testUpdateIsHot() throws SQLException {
        boolean isHot = true;

        MockitoDAOSetUp.UpdateOfferIsHot(con,ps);

        boolean result = dao.update(offer, isHot);
        assertTrue(result);
    }

    @Test
    void testUpdateVacancy() throws SQLException {
        int vacancy = 15;

        MockitoDAOSetUp.UpdateOfferVacancy(con,ps);

        boolean result = dao.update(offer, vacancy);
        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoDAOSetUp.DeleteOffer(con,ps);

        boolean result = dao.delete(offer.getCode());

        assertTrue(result);
    }

    @Test
    void testReadAll() throws SQLException {
        MockitoDAOSetUp.ReadAllOffers(offer,con,ps,rs);

        List<Offer> expected = new ArrayList<>();
        expected.add(offer);

        List<Offer> offers = dao.readAll(0,0,false);

        assertEquals(expected, offers);
        assertEquals(0, dao.getNumberOfPages());
    }

    @Test
    void testReadAllSorted() throws SQLException {
        SORTING_BY sortingBy = SORTING_BY.OFFER_TYPE;

        MockitoDAOSetUp.ReadAllOffersSorted(offer,sortingBy.getCommand(),con,ps,rs);

        List<Offer> expected = new ArrayList<>();
        expected.add(offer);

        List<Offer> offers = dao.readAllSorted(0,0,sortingBy);

        assertEquals(expected, offers);
        assertEquals(0, dao.getNumberOfPages());
    }

    @Test
    void testReadAllOfferTypes() throws SQLException {
        MockitoDAOSetUp.ReadAllOfferTypes(con,ps,rs);

        List<String> expectedTypes = new ArrayList<>();
        expectedTypes.add("rest");
        expectedTypes.add("excursion");
        expectedTypes.add("shopping");

        List<String> offerTypes = dao.readAllOfferTypes();

        assertEquals(expectedTypes, offerTypes);
    }
}

