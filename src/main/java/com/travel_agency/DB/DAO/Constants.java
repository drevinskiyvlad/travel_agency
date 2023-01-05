package com.travel_agency.DB.DAO;

public class Constants {

    public static final String ADD_USER = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    public static final String ADD_USER_WITH_DETAILS = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone, details)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "select * from user where email LIKE ?";
}
