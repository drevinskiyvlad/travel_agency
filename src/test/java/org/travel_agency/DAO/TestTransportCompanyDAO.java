package org.travel_agency.DAO;

import com.travel_agency.DB.DAO.TransportCompanyDAO;
import com.travel_agency.models.DAO.TransportCompany;
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
class TestTransportCompanyDAO {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private TransportCompany transportCompany;
    private TransportCompanyDAO dao;

    @BeforeAll
    void initialize(){
        transportCompany = new TransportCompany(0,"testCompany","testAddress",150,200.00);
        con = Mockito.mock(Connection.class);
        dao = new TransportCompanyDAO(con);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }
    @Test
    void testCreate() throws SQLException {
        MockitoSetUp.CreateTransportCompany(transportCompany,con,ps);

        boolean result = dao.create(transportCompany);

        assertTrue(result);
    }

    @Test
    void testRead() throws Exception {
        MockitoSetUp.ReadTransportCompany(transportCompany,true,con,ps,rs);

        TransportCompany result = dao.read(transportCompany.getName());

        assertEquals(transportCompany, result);
    }

    @Test
    void testUpdateEmail() throws SQLException {
        String newName = "newName";

        MockitoSetUp.UpdateTransportCompanyName(transportCompany,newName,con,ps);

        boolean result = dao.update(transportCompany, newName);

        assertTrue(result);
    }

    @Test
    void testUpdateVacancy() throws SQLException {
        int newVacancy = 100;

        MockitoSetUp.UpdateTransportCompanyVacancy(transportCompany,newVacancy,con,ps);

        boolean result = dao.update(transportCompany, newVacancy);

        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoSetUp.DeleteTransportCompany(transportCompany,con,ps);

        boolean result = dao.delete(transportCompany);

        assertTrue(result);
    }

    @Test
    void testReadAll() throws SQLException {
        MockitoSetUp.ReadAllTransportCompany(transportCompany,con,ps,rs);

        List<TransportCompany> expected = new ArrayList<>();
        expected.add(transportCompany);

        List<TransportCompany> hotels = dao.readAll();

        assertEquals(expected, hotels);
    }

}

