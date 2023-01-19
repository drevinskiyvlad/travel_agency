package org.travel_agency.DB.DAO;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.entity.Offer;
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
class TestOfferDAO {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private Offer offer;
    private MySQLOfferDAO dao;

    @BeforeAll
    void initialize(){
        offer = new Offer(0,"123","City","rest", "Inn", "Name",100,0.15,false,150);
        con = Mockito.mock(Connection.class);
        dao = new MySQLOfferDAO(con);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }
    @Test
    void testCreate() throws SQLException {
        MockitoDAOSetUp.CreateOffer(offer,dao,con,ps,rs);

        boolean result = dao.create(offer);

        assertTrue(result);
    }


    @Test
    void testRead() throws Exception {
        MockitoDAOSetUp.ReadOffer(offer,true,con,ps,rs);

        Offer result = dao.read(offer.getCode());

        assertEquals(offer, result);
    }

    @Test
    void testUpdateForException() {
        assertThrows(UnsupportedOperationException.class,
                ()->{
                    //dao.update(offer,"NewCode");
                });
    }

    @Test
    void testUpdateIsHot() throws SQLException {
        boolean isHot = true;

        MockitoDAOSetUp.UpdateOfferIsHot(offer,isHot,con,ps);

        boolean result = dao.update(offer, isHot);
        assertTrue(result);
    }



    @Test
    void testUpdateVacancy() throws SQLException {
        int vacancy = 15;

        MockitoDAOSetUp.UpdateOfferVacancy(offer,vacancy,con,ps);

        boolean result = dao.update(offer, vacancy);
        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoDAOSetUp.DeleteOffer(offer,con,ps);

        //boolean result = dao.delete(offer);

        //assertTrue(result);
    }



    @Test
    void testReadAll() throws SQLException {
        MockitoDAOSetUp.ReadAllOffers(offer,con,ps,rs);

        List<Offer> expected = new ArrayList<>();
        expected.add(offer);

        List<Offer> offers = dao.readAll(0,0,false);

        assertEquals(expected, offers);
    }
}

