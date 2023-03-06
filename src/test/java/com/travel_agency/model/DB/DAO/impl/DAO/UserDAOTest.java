package com.travel_agency.model.DB.DAO.impl.DAO;

import com.travel_agency.model.DB.DAO.UserDAO;
import com.travel_agency.model.DB.DAO.impl.UserDAOImpl;
import com.travel_agency.model.entity.User;
import com.travel_agency.utils.Constants.PaginationConstants;
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
class UserDAOTest {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private User user;
    private UserDAO<User> dao;

    @BeforeAll
    void initializeUser() {
        user = new User(1, "test@email.com", "password", "user", "Test", "User", "1234567890", false);
        con = Mockito.mock(Connection.class);
        dao = new UserDAOImpl();
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }

    @Test
    void testCreate() throws SQLException {
        MockitoDAOSetUp.createUser(con, ps,rs);

        boolean result = dao.create(user);

        assertTrue(result);
    }

    @Test
    void testReadByEmail() throws Exception {
        MockitoDAOSetUp.readUser(user, true, con, ps, rs);

        User result = dao.read(user.getEmail());

        assertEquals(user, result);
    }

    @Test
    void testReadById() throws Exception {
        MockitoDAOSetUp.readUser(user, true, con, ps, rs);

        User result = dao.read(user.getId());

        assertEquals(user, result);
    }


    @Test
    void testUpdateRole() throws SQLException {
        String newRole = "admin";

        MockitoDAOSetUp.updateRole(con,ps,rs);

        boolean result = dao.update(user, newRole);

        assertTrue(result);
    }

    @Test
    void testUpdateBlocked() throws SQLException {
        boolean blocked = true;

        MockitoDAOSetUp.updateBlocked(con,ps);

        boolean result = dao.update(user.getEmail(), blocked);

        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoDAOSetUp.deleteUser(con,ps);

        boolean result = dao.delete(user.getEmail());

        assertTrue(result);
    }

    @Test
    void testReadAll() throws SQLException {
        MockitoDAOSetUp.readAllUsers(user,con,ps,rs);

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);

        List<User> users = dao.readAll(0, PaginationConstants.USER_LIST_RECORDS_PER_PAGE);

        assertEquals(expectedUsers, users);
        assertEquals(0, dao.getNumberOfPages());
    }

    @Test
    void testReadAllUserRoles() throws SQLException {
        MockitoDAOSetUp.readAllUserRoles(con,ps,rs);

        List<String> expectedUsers = new ArrayList<>();
        expectedUsers.add("user");
        expectedUsers.add("manager");
        expectedUsers.add("admin");

        List<String> userRoles = dao.readAllUserRoles();

        assertEquals(expectedUsers, userRoles);
    }
}
