package com.travel_agency.DB.DAO;

public class Constants {

    public static final String ADD_USER = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    public static final String ADD_USER_WITH_DETAILS = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone, details)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "select * from user where email LIKE ?";
    public static final String CHANGE_USER_EMAIL = "UPDATE user SET email = ? WHERE phone = ?";
    public static final String DELETE_USER = "DELETE FROM user WHERE email = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String GET_USER_ROLE_BY_ID = "select name from user_role where id = ?";
    public static final String GET_USER_ROLE_BY_NAME = "select id from user_role where name = 'user'";
}
