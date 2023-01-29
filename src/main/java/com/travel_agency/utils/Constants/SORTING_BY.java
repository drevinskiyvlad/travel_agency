package com.travel_agency.utils.Constants;

import lombok.Getter;

/**
 * Constants for sorting types
 */
public enum SORTING_BY {
    HOTEL_TYPE("hotelType", MySQLDAOConstants.OFFER_BY_HOTEL_TYPE),
    OFFER_TYPE("offerType", MySQLDAOConstants.OFFER_BY_TYPE),
    PLACES("places", MySQLDAOConstants.OFFER_BY_PLACES),
    PRICE("price", MySQLDAOConstants.OFFER_BY_PRICE),
    NONE("none", MySQLDAOConstants.FIND_ALL_OFFERS);

    @Getter
    private final String name;
    @Getter
    private final String command;

    SORTING_BY(String name, String command) {
        this.name = name;
        this.command = command;
    }
}
