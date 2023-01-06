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



}
