package org.travel_agency.DAO;

import com.travel_agency.DB.Constants;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.Fields;
import com.travel_agency.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UserDAOTest {

    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    private User user;
    UserDAO dao;

    @BeforeAll
    void initializeUser(){
        user = new User(1, "test@email.com","password", "user", "Test", "User", "1234567890", LocalDateTime.now());
        mockConnection = Mockito.mock(Connection.class);
        dao = new UserDAO(mockConnection);
    }

    @Test
    void testRead() throws Exception {
        //Mokito setup
        when(mockConnection.prepareStatement(Constants.FIND_USER)).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(Constants.FIND_USER_ROLE_BY_ID)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(Fields.USER_ID)).thenReturn(user.getId());
        when(mockResultSet.getString(Fields.USER_PASSWORD)).thenReturn(user.getPassword());
        when(mockResultSet.getString(Fields.USER_FIRST_NAME)).thenReturn(user.getFirstName());
        when(mockResultSet.getString(Fields.USER_LAST_NAME)).thenReturn(user.getLastName());
        when(mockResultSet.getString(Fields.USER_PHONE)).thenReturn(user.getPhone());
        when(mockResultSet.getString(Fields.USER_DETAILS)).thenReturn(user.getDetails());
        when(mockResultSet.getString(Fields.USER_ROLE_NAME)).thenReturn(user.getUserRole());
        when(mockResultSet.getTimestamp(Fields.USER_FROM)).thenReturn(Timestamp.valueOf(user.getUserFrom()));

        // Act
        User result = dao.read(user.getEmail());

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testCreate() throws SQLException {
        when(mockConnection.prepareStatement(Constants.ADD_USER)).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(Constants.FIND_USER_ROLE_BY_NAME)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockPreparedStatement.executeUpdate()).thenReturn(7);
        mockPreparedStatement.setString(1, user.getEmail());
        mockPreparedStatement.setString(2, user.getPassword());
        mockPreparedStatement.setInt(3, dao.readUserRole(user.getUserRole()));
        mockPreparedStatement.setString(4, user.getFirstName());
        mockPreparedStatement.setString(5, user.getLastName());
        mockPreparedStatement.setString(6, user.getPhone());
        mockPreparedStatement.setString(7, user.getDetails());

        boolean result = dao.create(user);

        assertTrue(result);
    }
}
