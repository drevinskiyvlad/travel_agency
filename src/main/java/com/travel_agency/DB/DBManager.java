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
import java.util.List;

public class DBManager {
    private final Logger logger = LogManager.getLogger();
    private static DBManager INSTANCE = null;
    private Connection con;

    private DBManager(){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/travel_agency");
            con = ds.getConnection();
        } catch (SQLException | NamingException e) {
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
        DAO<User, String> userDao = new UserDAO(con);
        return userDao.read(email);
    }

    public boolean createUser(User user){
        DAO<User, String> userDao = new UserDAO(con);
        return userDao.create(user);
    }

    public List<User> getAllUsers(){
        DAO<User, String> userDao = new UserDAO(con);
        return userDao.readAll();
    }

    public boolean changeUserRole(User user, String newRole){
        UserDAO userDao = new UserDAO(con);
        return userDao.updateUserRole(user, newRole);
    }
}
