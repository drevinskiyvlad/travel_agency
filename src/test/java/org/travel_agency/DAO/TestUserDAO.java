package org.travel_agency.DAO;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.models.User;
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
class TestUserDAO {

    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;
    private User user;
    private UserDAO dao;

    @BeforeAll
    void initializeUser() {
        user = new User(1, "test@email.com", "password", "user", "Test", "User", "1234567890");
        con = Mockito.mock(Connection.class);
        dao = new UserDAO(con);
    }

    @AfterAll
    void closeConnection() throws SQLException {
        con.close();
    }

    @Test
    void testCreate() throws SQLException {
        MockitoSetUp.CreateUser(user, dao, con, ps, rs);

        boolean result = dao.create(user);

        assertTrue(result);
    }

    @Test
    void testRead() throws Exception {
        MockitoSetUp.ReadUser(user, true, con, ps, rs);

        User result = dao.read(user.getEmail());

        assertEquals(user, result);
    }

    @Test
    void testUpdateEmail() throws SQLException {
        String newEmail = "doe@example.com";

        MockitoSetUp.UpdateUserEmail(user, newEmail, con, ps);

        boolean result = dao.update(user, newEmail);

        assertTrue(result);
    }


    @Test
    void testUpdateUserRole() throws SQLException {
        String newRole = "admin";

        MockitoSetUp.UpdateRole(user,newRole,dao,con,ps,rs);

        boolean result = dao.updateUserRole(user, newRole);

        assertTrue(result);
    }

    @Test
    void testDelete() throws SQLException {
        MockitoSetUp.DeleteUser(user,con,ps);

        boolean result = dao.delete(user);

        assertTrue(result);
    }

    @Test
    void testReadAll() throws SQLException {
        MockitoSetUp.ReadAllUsers(user,con,ps,rs);

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);

        List<User> users = dao.readAll();

        assertEquals(expectedUsers, users);
    }
}