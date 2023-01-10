package com.travel_agency.DB;

public class Fields {
    private Fields(){}

    //User db fields
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE = "user_role_id";
    public static final String USER_ROLE_ID = "id";
    public static final String USER_ROLE_NAME = "name";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PHONE = "phone";

    //hotel db fields
    public static final String HOTEL_ID = "id";
    public static final String HOTEL_NAME = "hotel_name";
    public static final String HOTEL_ADDRESS = "address";
    public static final String HOTEL_TYPE = "hotel_type_id";
    public static final String HOTEL_TYPE_ID = "id";
    public static final String HOTEL_TYPE_NAME = "name";
    public static final String HOTEL_VACANCY = "vacancy";
    public static final String HOTEL_PRICE = "price";

    //transport company db fields
    public static final String TRANSPORT_COMPANY_ID = "id";
    public static final String TRANSPORT_COMPANY_NAME = "company_name";
    public static final String TRANSPORT_COMPANY_HQ_ADDRESS = "hq_address";
    public static final String TRANSPORT_COMPANY_VACANCY = "vacancy";
    public static final String TRANSPORT_COMPANY_PRICE = "price";

    //offer db fields
    public static final String OFFER_ID = "id";
    public static final String OFFER_CODE = "code";
    public static final String OFFER_TYPE = "offer_type_id";
    public static final String OFFER_TYPE_ID = "id";
    public static final String OFFER_TYPE_NAME = "name";
    public static final String OFFER_TRANSPORT_COMPANY = "transport_company_id";
    public static final String OFFER_HOTEL = "hotel_id";
    public static final String OFFER_VACANCY = "vacancy";
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
    public static final String ORDER_STATUS_NAME = "name";
    public static final String ORDER_PRICE = "price";
}
