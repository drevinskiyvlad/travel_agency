package com.travel_agency.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class OfferDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String code;
    private String offerType;
    private String hotel;
    private String hotelType;
    private String city;
    private int places;
    private double discount;
    private boolean isHot;
    private double price;
    private double fullPrice;

    /**
     * @param code Primary key
     * @param offerType Type of offer
     * @param hotel Hotel name
     * @param hotelType Hotel type
     * @param city City
     * @param places Number of places
     * @param discount Discount(from 0.05 to 0.25)
     * @param isHot Is hot status
     * @param price Price of offer
     */
    public OfferDTO(String code, String offerType, String hotel, String hotelType, String city, int places, double discount, boolean isHot, double price) {
        this.code = code;
        this.offerType = offerType;
        this.hotel = hotel;
        this.hotelType = hotelType;
        this.city = city;
        this.places = places;
        this.discount = discount;
        this.isHot = isHot;
        this.price = price;
        fullPrice = price / (1 - discount);
    }
}
