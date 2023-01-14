package com.travel_agency.models.DAO;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Offer {
    @Getter @Setter private int id;
    @Getter @Setter private String code;
    @Getter @Setter private String type;
    @Getter @Setter private TransportCompany transportCompany;
    @Getter @Setter private Hotel hotel;
    @Getter @Setter private int vacancy;
    @Getter @Setter private double discount;
    @Getter @Setter private boolean isHot;
    @Getter @Setter private double price;

    public Offer(int id, String code, String type,
                 TransportCompany transportCompany, Hotel hotel,
                 double discount, boolean isHot) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.transportCompany = transportCompany;
        this.hotel = hotel;
        this.discount = discount;
        this.isHot = isHot;
        vacancy = Math.min(transportCompany.getVacancy(), hotel.getVacancy());
        price = (transportCompany.getPrice() + hotel.getPrice()) * discount;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", code=" + code +
                ", offer type=" + type +
                ", transport company=" + transportCompany.getName() +
                ", hotel=" + hotel.getName() +
                ", vacancy=" + vacancy +
                ", discount=" + (int)(discount * 100) +
                "%, hot=" + isHot +
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
