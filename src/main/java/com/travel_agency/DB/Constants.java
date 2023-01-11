package com.travel_agency.DB;

public class Constants {
    private Constants(){}

    //User request constants
    public static final String ADD_USER = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "select * from user where email LIKE ?";
    public static final String FIND_USER_BY_ID = "select * from user where id LIKE ?";
    public static final String CHANGE_USER_EMAIL = "UPDATE user SET email = ? WHERE phone = ?";
    public static final String CHANGE_USER_ROLE = "UPDATE user SET user_role_id = ? WHERE email = ?";
    public static final String DELETE_USER = "DELETE FROM user WHERE email = ?";
    public static final String FIND_ALL_USERS = "SELECT * FROM user";
    public static final String FIND_USER_ROLE_BY_ID = "select user_role_name from user_role where id = ?";
    public static final String FIND_USER_ROLE_BY_NAME = "select id from user_role where user_role_name = ?";

    //Hotel request constants
    public static final String ADD_HOTEL = "INSERT INTO hotel(hotel_name, address, hotel_type_id, vacancy, price)" +
            "VALUES(?, ?, ?, ?, ?)";
    public static final String FIND_HOTEL = "SELECT * FROM hotel WHERE hotel_name LIKE ?";
    public static final String FIND_HOTEL_BY_ID = "SELECT * FROM hotel WHERE id LIKE ?";
    public static final String CHANGE_HOTEL_NAME = "UPDATE hotel SET hotel_name = ? WHERE address = ?";
    public static final String CHANGE_HOTEL_VACANCY = "UPDATE hotel SET vacancy = ? WHERE hotel_name = ?";
    public static final String DELETE_HOTEL = "DELETE FROM hotel WHERE hotel_name = ?";
    public static final String FIND_ALL_HOTELS = "SELECT * FROM hotel";
    public static final String FIND_HOTEL_TYPE_BY_ID = "SELECT hotel_type_name FROM hotel_type WHERE id = ?";
    public static final String FIND_HOTEL_TYPE_BY_NAME = "SELECT id FROM hotel_type WHERE hotel_type_name = ?";

    //Transport company request constants
    public static final String ADD_TRANSPORT_COMPANY = "INSERT INTO transport_company(company_name,hq_address,vacancy,price)" +
            "VALUES(?,?,?,?)";
    public static final String FIND_TRANSPORT_COMPANY = "SELECT * FROM transport_company WHERE company_name LIKE ?";
    public static final String FIND_TRANSPORT_COMPANY_BY_ID = "SELECT * FROM transport_company WHERE id LIKE ?";
    public static final String CHANGE_TRANSPORT_COMPANY_NAME = "UPDATE transport_company SET company_name = ? WHERE hq_address = ?";
    public static final String CHANGE_TRANSPORT_COMPANY_VACANCY = "UPDATE transport_company SET vacancy = ? WHERE company_name = ?";
    public static final String DELETE_TRANSPORT_COMPANY = "DELETE FROM transport_company WHERE company_name = ?";
    public static final String FIND_ALL_TRANSPORT_COMPANY = "SELECT * FROM transport_company";

    //Offer request constants
    public static final String ADD_OFFER = "INSERT INTO offer(code,offer_type_id,transport_company_id,hotel_id,vacancy,discount,is_hot,price)" +
            "VALUES(?,?,?,?,?,?,?,?)";
    public static final String FIND_OFFER = "SELECT * FROM offer WHERE code LIKE ?";
    public static final String FIND_OFFER_BY_ID = "SELECT * FROM offer WHERE id LIKE ?";
    public static final String CHANGE_OFFER_IS_HOT = "UPDATE offer SET is_hot = ? WHERE code = ?";
    public static final String CHANGE_OFFER_VACANCY = "UPDATE offer SET vacancy = ? WHERE code = ?";
    public static final String DELETE_OFFER = "DELETE FROM offer WHERE code = ?";
    public static final String FIND_ALL_OFFERS = "SELECT * FROM offer";
    public static final String FIND_OFFER_TYPE_BY_ID = "select offer_type_name from offer_type where id = ?";
    public static final String FIND_OFFER_TYPE_BY_NAME = "select id from offer_type where offer_type_name = ?";

    //Order request constants
    public static final String ADD_ORDER = "INSERT INTO order(code,user_id,offer_id,order_status_id,price)" +
            "VALUES(?,?,?,?,?)";
    public static final String FIND_ORDER = "SELECT * FROM order WHERE code LIKE ?";
    public static final String CHANGE_ORDER_STATUS = "UPDATE order SET order_status_id = ? WHERE code = ?";
    public static final String DELETE_ORDER = "DELETE FROM order WHERE code = ?";
    public static final String FIND_ALL_ORDERS = "SELECT * FROM order";
    public static final String FIND_ORDER_STATUS_BY_ID = "select order_status_name from order_status where id = ?";
    public static final String FIND_ORDER_STATUS_BY_NAME = "select id from order_status where order_status_name = ?";
}
