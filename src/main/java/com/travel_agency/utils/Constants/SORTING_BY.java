package com.travel_agency.utils.Constants;

import lombok.Getter;

public enum SORTING_BY {
    OFFER_TYPE("offerType", MySQLDAOConstants.OFFER_BY_TYPE),
    HOTEL_TYPE("hotelType", MySQLDAOConstants.OFFER_BY_HOTEL_TYPE),
    PRICE("price", MySQLDAOConstants.OFFER_BY_PRICE),
    PLACES("places", MySQLDAOConstants.OFFER_BY_PLACES),
    NONE("none", MySQLDAOConstants.FIND_ALL_OFFERS);



    @Getter private final String name;
    @Getter private final String command;

    SORTING_BY(String name, String command) {
        this.name = name;
        this.command = command;
    }
}
