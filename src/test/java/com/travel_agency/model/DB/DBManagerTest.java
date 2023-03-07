package com.travel_agency.model.DB;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DBManagerTest {

    @Mock
    private DataSource mockDataSource;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        DBManager.getInstance().setDs(mockDataSource);
    }

    @Test
    void testGetConnection() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);

        Connection connection = DBManager.getInstance().getConnection();

        assertEquals(mockConnection, connection);
        verify(mockDataSource).getConnection();
    }

    @Test
    void testCloseConnection() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        DBManager.closeConnection(mockConnection);

        verify(mockConnection).close();
    }

}
