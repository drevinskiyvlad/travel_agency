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
    public static final String FIND_CITY_BY_ID = "SELECT * FROM city WHERE id = ?";
    public static final String DELETE_CITY = "DELETE FROM city WHERE name = ?";
    public static final String FIND_ALL_CITY = "SELECT * FROM city";

    //Hotel request constants
    public static final String ADD_HOTEL = "INSERT INTO hotel(name, address, city_id, hotel_type_id,is_partner,number_of_available_room)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    public static final String ADD_HOTEL_WITH_DESCRIPTION = "INSERT INTO hotel(name, address, city_id, hotel_type_id,is_partner,number_of_available_room,descriptions)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?);";
    public static final String FIND_HOTEL = "SELECT * FROM hotel WHERE name LIKE ?";
    public static final String CHANGE_HOTEL_NAME = "UPDATE hotel SET name = ? WHERE address = ?";
    public static final String CHANGE_HOTEL_TYPE = "UPDATE hotel SET hotel_type_id = ? WHERE name = ?";
    public static final String DELETE_HOTEL = "DELETE FROM hotel WHERE name = ?";
    public static final String FIND_ALL_HOTEL = "SELECT * FROM hotel";
    public static final String FIND_HOTEL_TYPE_BY_ID = "SELECT name FROM hotel_type WHERE id = ?";
    public static final String FIND_HOTEL_TYPE_BY_NAME = "SELECT id FROM hotel_type WHERE name = ?";

    //Transport company request constants
    public static final String ADD_TRANSPORT_COMPANY = "INSERT INTO transport_company(name,city_id,hq_address,company_type_id,is_partner)" +
            "VALUES(?, ?, ?, ?, ?)";
    public static final String ADD_TRANSPORT_COMPANY_WITH_DESCRIPTION = "INSERT INTO transport_company(name,city_id,hq_address,company_type_id,is_partner,description)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    public static final String FIND_TRANSPORT_COMPANY = "SELECT * FROM transport_company WHERE name LIKE ?";
    public static final String CHANGE_TRANSPORT_COMPANY_NAME = "UPDATE transport_company SET name = ? WHERE hq_address = ?";
    public static final String DELETE_TRANSPORT_COMPANY = "DELETE FROM transport_company WHERE name = ?";
    public static final String FIND_ALL_TRANSPORT_COMPANY = "SELECT * FROM transport_company";
    public static final String FIND_TRANSPORT_COMPANY_TYPE_BY_ID = "SELECT name FROM transport_company_type WHERE id = ?";
    public static final String FIND_TRANSPORT_COMPANY_TYPE_TYPE_BY_NAME = "SELECT id FROM transport_company_type WHERE name = ?";

}
