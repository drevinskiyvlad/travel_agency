package com.travel_agency.utils.Constants;

/**
 * All MySQL commands for DAO
 */
public class MySQLDAOConstants {
    private MySQLDAOConstants() {
    }

    //User request constants
    public static final String ADD_USER = "INSERT INTO user(email, password, user_role_id, first_name, last_name, phone, blocked)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "select * from user where email LIKE ?";
    public static final String FIND_USER_BY_ID = "select * from user where id LIKE ?";
    public static final String CHANGE_USER_EMAIL = "UPDATE user SET email = ? WHERE phone = ?";
    public static final String CHANGE_USER_BLOCKED = "UPDATE user SET blocked = ? WHERE email = ?";
    public static final String CHANGE_USER_ROLE = "UPDATE user SET user_role_id = ? WHERE email = ?";
    public static final String DELETE_USER = "DELETE FROM user WHERE email = ?";
    public static final String FIND_ALL_USERS = "select * from user limit ?, ?";
    public static final String FIND_ALL_USER_ROLES = "select * from user_role";
    public static final String FIND_USER_ROLE_BY_ID = "select user_role_name from user_role where id = ?";
    public static final String FIND_USER_ROLE_BY_NAME = "select id from user_role where user_role_name = ?";


    //Hotel request constant
    public static final String ADD_HOTEL = "INSERT INTO hotel(name,type_id,city) " +
            "VALUES (?, ?, ?)";
    public static final String FIND_HOTEL = "SELECT * FROM hotel WHERE id = ?";
    public static final String FIND_HOTEL_BY_NAME = "SELECT * FROM hotel WHERE name = ?";
    public static final String DELETE_HOTEL = "DELETE FROM hotel WHERE id = ?";
    public static final String FIND_ALL_HOTEL_TYPES = "SELECT * FROM hotel_type";

    //Offer request constants
    public static final String ADD_OFFER = "INSERT INTO offer(code,hotel_id,offer_type_id,places,discount,is_hot,active,price)" +
            "VALUES(?,?,?,?,?,?,?,?);";
    public static final String FIND_OFFER = "SELECT * FROM offer WHERE code LIKE ?";
    public static final String FIND_OFFER_BY_ID = "SELECT * FROM offer WHERE id LIKE ?";
    public static final String CHANGE_OFFER_IS_HOT = "UPDATE offer SET is_hot = ? WHERE code = ?";
    public static final String CHANGE_OFFER_ACTIVE = "UPDATE offer SET active = ? WHERE code = ?";
    public static final String CHANGE_OFFER_PLACES = "UPDATE offer SET places = ? WHERE code = ?";
    public static final String DELETE_OFFER = "DELETE FROM offer WHERE code = ?";
    public static final String FIND_ALL_OFFERS = "SELECT * FROM offer WHERE is_hot = false LIMIT ?, ?";
    public static final String FIND_ALL_HOT_OFFERS = "SELECT * FROM offer WHERE is_hot = true LIMIT ?,?";

    public static final String FIND_ALL_OFFER_TYPES = "SELECT * FROM offer_type";
    public static final String FIND_OFFER_TYPE_BY_ID = "select offer_type_name from offer_type where id = ?";
    public static final String FIND_OFFER_TYPE_BY_NAME = "select id from offer_type where offer_type_name = ?";
    public static final String FIND_HOTEL_TYPE_BY_ID = "select hotel_type_name from hotel_type where id = ?";
    public static final String FIND_HOTEL_TYPE_BY_NAME = "select id from hotel_type where hotel_type_name = ?";


    //Order request constants
    public static final String ADD_ORDER = "INSERT INTO user_order(code,user_id,offer_id,order_status_id)" +
            "VALUES(?,?,?,?)";
    public static final String FIND_ORDER = "SELECT * FROM user_order WHERE code LIKE ?";
    public static final String CHANGE_ORDER_STATUS = "UPDATE user_order SET order_status_id = ? WHERE code = ?";
    public static final String DELETE_ORDER = "DELETE FROM user_order WHERE code = ?";
    public static final String FIND_ALL_ORDERS = "SELECT * FROM user_order LIMIT ?, ?";
    public static final String FIND_ALL_ORDER_STATUS = "SELECT * FROM order_status";
    public static final String FIND_ALL_ORDERS_OF_USER = "SELECT * FROM user_order WHERE user_id = ? LIMIT ?,?";
    public static final String FIND_ORDER_STATUS_BY_ID = "select order_status_name from order_status where id = ?";
    public static final String FIND_ORDER_STATUS_BY_NAME = "select id from order_status where order_status_name = ?";


    //All for pagination
    public static final String USER_GET_NUMBER_OF_RECORDS = "SELECT COUNT(1) from user";
    public static final String NOT_HOT_OFFER_GET_NUMBER_OF_RECORDS = "SELECT COUNT(1) from offer WHERE is_hot = false";
    public static final String HOT_OFFER_GET_NUMBER_OF_RECORDS = "SELECT COUNT(1) from offer WHERE is_hot = true";
    public static final String OFFER_GET_NUMBER_OF_RECORDS = "SELECT COUNT(1) from offer";
    public static final String ORDER_GET_NUMBER_OF_RECORDS = "SELECT COUNT(1) from user_order";
    public static final String USER_ORDER_GET_NUMBER_OF_RECORDS = "SELECT COUNT(1) from user_order where user_id = ?";

    //All offer sorting
    public static final String OFFER_BY_TYPE = "SELECT * FROM offer ORDER BY offer_type_id LIMIT ?,?";
    public static final String OFFER_BY_HOTEL_TYPE = "select offer.*, hotel.type_id " +
                    "from offer " +
                    "INNER JOIN hotel ON offer.hotel_id=hotel.id " +
                    "order by hotel.type_id " +
                    "limit ?,?";
    public static final String OFFER_BY_PRICE = "SELECT * FROM offer ORDER BY price LIMIT ?,?";
    public static final String OFFER_BY_PLACES = "SELECT * FROM offer ORDER BY places LIMIT ?,?";

}
