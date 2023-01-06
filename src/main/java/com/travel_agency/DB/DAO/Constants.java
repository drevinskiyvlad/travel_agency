package com.travel_agency.DB.DAO;

public class Constants {
    private Constants(){}

    //User request constants
    public static final String ADD_USER = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    public static final String ADD_USER_WITH_DETAILS = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone, details)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "select * from user where email LIKE ?";
    public static final String CHANGE_USER_EMAIL = "UPDATE user SET email = ? WHERE phone = ?";
    public static final String CHANGE_USER_ROLE = "UPDATE user SET user_role_id = ? WHERE email = ?";
    public static final String DELETE_USER = "DELETE FROM user WHERE email = ?";
    public static final String FIND_ALL_USERS = "SELECT * FROM user";
    public static final String FIND_USER_ROLE_BY_ID = "select name from user_role where id = ?";
    public static final String FIND_USER_ROLE_BY_NAME = "select id from user_role where name = ?";

    //Country request constants
    public static final String ADD_COUNTRY = "INSERT INTO country(name, code) VALUES(?, ?)";
    public static final String FIND_COUNTRY = "SELECT * FROM country WHERE name = ?";
    public static final String FIND_COUNTRY_BY_ID = "SELECT * FROM country WHERE id = ?";
    public static final String CHANGE_COUNTRY_NAME = "UPDATE country SET name = ? WHERE code = ?";
    public static final String DELETE_COUNTRY = "DELETE FROM country WHERE name = ?";
    public static final String FIND_ALL_COUNTRY = "SELECT * FROM country";

    //City request constants

    public static final String ADD_CITY = "INSERT INTO city(name, country_id) VALUES(?, ?);";
    public static final String FIND_CITY = "SELECT * FROM city WHERE name = ?";
    public static final String DELETE_CITY = "DELETE FROM city WHERE name = ?";
    public static final String FIND_ALL_CITY = "SELECT * FROM city";


}
