package com.travel_agency.model.DB;

/**
 * All database field names
 */
public class Fields {
    private Fields() {
    }

    //User db fields
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE = "user_role_id";
    public static final String USER_ROLE_ID = "id";
    public static final String USER_ROLE_NAME = "user_role_name";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PHONE = "phone";
    public static final String USER_BLOCKED = "blocked";

    //offer db fields
    public static final String OFFER_ID = "id";
    public static final String OFFER_CODE = "code";
    public static final String OFFER_CITY = "city";
    public static final String OFFER_TYPE = "offer_type_id";
    public static final String OFFER_TYPE_ID = "id";
    public static final String OFFER_TYPE_NAME = "offer_type_name";
    public static final String OFFER_HOTEL_TYPE = "hotel_type_id";
    public static final String HOTEL_TYPE_ID = "id";
    public static final String HOTEL_TYPE_NAME = "hotel_type_name";
    public static final String HOTEL_NAME = "hotel_name";
    public static final String OFFER_PLACES = "places";
    public static final String OFFER_DISCOUNT = "discount";
    public static final String OFFER_IS_HOT = "is_hot";
    public static final String OFFER_PRICE = "price";

    //order db fields
    public static final String ORDER_ID = "id";
    public static final String ORDER_CODE = "code";
    public static final String ORDER_USER = "user_id";
    public static final String ORDER_OFFER = "offer_id";
    public static final String ORDER_STATUS = "order_status_id";
    public static final String ORDER_STATUS_ID = "id";
    public static final String ORDER_STATUS_NAME = "order_status_name";
}
