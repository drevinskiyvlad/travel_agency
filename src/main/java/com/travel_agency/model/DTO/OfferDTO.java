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
        fullPrice = (price * 100) / ((1 - discount) * 100);
    }
}
