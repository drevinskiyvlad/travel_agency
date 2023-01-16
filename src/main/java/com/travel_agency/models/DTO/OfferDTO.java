package com.travel_agency.models.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OfferDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private String code;
    private String type;
    private String transportCompany;
    private String hotel;
    private String hotelType;
    private String city;
    private int vacancy;
    private double discount;
    private boolean isHot;
    private double price;
    private double fullPrice;

    public OfferDTO(String code, String type, String transportCompany, String hotel, String hotelType, String city, int vacancy, double discount, boolean isHot, double price) {
        this.code = code;
        this.type = type;
        this.transportCompany = transportCompany;
        this.hotel = hotel;
        this.hotelType = hotelType;
        this.city = city;
        this.vacancy = vacancy;
        this.discount = discount;
        this.isHot = isHot;
        this.price = price;
        fullPrice = (price * 100) / ((1-discount) * 100);
    }
}
