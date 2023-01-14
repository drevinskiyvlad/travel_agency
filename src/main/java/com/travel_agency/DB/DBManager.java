package com.travel_agency.DB;

import com.travel_agency.DB.DAO.DAO;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private final Logger logger = LogManager.getLogger();
    private static DBManager INSTANCE = null;
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
        if(INSTANCE == null){
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }


    public User getUser(String email){
        Connection con = null;
        try{
            con = ds.getConnection();
            DAO<User, String> userDao = new UserDAO(con);
            return userDao.read(email);
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }finally{
            closeConnection(con);
        }
        return null;
    }

    public boolean createUser(User user){
        try(Connection con = ds.getConnection()) {
            DAO<User, String> userDao = new UserDAO(con);
            return userDao.create(user);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    private void closeConnection(Connection con){
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Unable to close connection" + e.getMessage(),e);
            }
        }
    }
}
