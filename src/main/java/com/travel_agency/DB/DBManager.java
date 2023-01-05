package com.travel_agency.DB;

import com.travel_agency.DB.DAO.DAO;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.models.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBManager {
    private static DBManager INSTANCE = null;
    private Connection con;

    private DBManager(){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/travel_agency");
            con = ds.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            //todo: add logger here
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
}
