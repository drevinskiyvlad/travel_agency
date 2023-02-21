package com.travel_agency.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer implements Entity{
    private int id;
    private String code;
    private Hotel hotel;
    private String offerType;
    private int places;
    private double discount;
    private boolean isHot;
    private boolean active;
    private double price;

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", hotel='" + hotel.getName() + '\'' +
                ", type='" + offerType + '\'' +
                ", places=" + places +
                ", discount=" + (int) (discount * 100) +
                "%, isHot=" + isHot +
                ", active=" + active +
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
