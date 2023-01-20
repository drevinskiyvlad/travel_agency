package com.travel_agency.model.DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager instance = null;
    private DataSource ds;

    private DBManager(){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/travel_agency");
        } catch (NamingException e) {
            logger.error("Unable to connect to db: " + e.getMessage(), e);
        }
    }
    public static synchronized DBManager getInstance() {
        if(instance == null){
            instance = new DBManager();
        }
        return instance;
    }


    public synchronized Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            logger.error("Unable to get connection: " + e.getMessage(), e);
        }
        return null;
    }

    public static void closeConnection(Connection con){
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Unable to close connection" + e.getMessage(),e);
            }
        }
    }
}
