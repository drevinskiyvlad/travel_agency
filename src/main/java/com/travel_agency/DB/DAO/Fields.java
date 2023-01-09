package com.travel_agency.DB.DAO;

public class Fields {
    private Fields(){}

    //User db fields
    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE = "user_role_id";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PHONE = "phone";
    public static final String USER_DETAILS = "details";
    public static final String USER_FROM = "user_from";
    public static final String USER_ROLE_ID = "id";
    public static final String USER_ROLE_NAME = "name";

    //Country db fields
    public static final String COUNTRY_ID = "id";
    public static final String COUNTRY_NAME = "name";
    public static final String COUNTRY_CODE = "code";

    //city db fields
    public static final String CITY_ID = "id";
    public static final String CITY_NAME = "name";
    public static final String CITY_COUNTRY_ID = "country_id";

    //hotel db fields
    public static final String HOTEL_ID = "id";
    public static final String HOTEL_NAME = "name";
    public static final String HOTEL_ADDRESS = "address";
    public static final String HOTEL_CITY_ID = "city_id";
    public static final String HOTEL_TYPE = "hotel_type_id";
    public static final String HOTEL_TYPE_ID = "id";
    public static final String HOTEL_TYPE_NAME = "name";
    public static final String HOTEL_DESCRIPTION = "descriptions";
    public static final String HOTEL_IS_PARTNER = "is_partner";
    public static final String HOTEL_NUMBER_OF_AVAILABLE_ROOM = "number_of_available_room";

    //transport company db fields
    public static final String TRANSPORT_COMPANY_ID = "id";
    public static final String TRANSPORT_COMPANY_NAME = "name";
    public static final String TRANSPORT_COMPANY_CITY_ID = "city_id";
    public static final String TRANSPORT_COMPANY_HQ_ADDRESS = "hq_address";
    public static final String TRANSPORT_COMPANY_COMPANY_TYPE = "company_type_id";
    public static final String TRANSPORT_COMPANY_DESCRIPTION = "description";
    public static final String TRANSPORT_COMPANY_IS_PARTNER = "is_partner";
    public static final String TRANSPORT_COMPANY_TYPE_ID = "id";
    public static final String TRANSPORT_COMPANY_TYPE_NAME = "name";

    //transport service db fields
    public static final String TRANSPORT_SERVICE_ID = "id";
    public static final String TRANSPORT_SERVICE_COMPANY_ID = "transport_company_id";
    public static final String TRANSPORT_SERVICE_FROM_CITY = "from_city_id";
    public static final String TRANSPORT_SERVICE_TO_CITY = "to_city_id";
    public static final String TRANSPORT_SERVICE_PRICE = "price";
    public static final String TRANSPORT_SERVICE_NUMBER_OF_FREE_SEATS = "number_of_free_seats";
    public static final String TRANSPORT_SERVICE_IS_ACTIVE = "is_active";
}
