package com.travel_agency.models.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Offer {
    private int id;
    private String code;
    private String city;
    private String offerType;
    private String hotelType;
    private String hotelName;
    private int places;
    private double discount;
    private boolean isHot;
    private double price;

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", type='" + offerType + '\'' +
                ", hotelType='" + hotelType + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", places=" + places +
                ", discount=" + (int)(discount*100) +
                "%, isHot=" + isHot +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return code.equals(offer.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
