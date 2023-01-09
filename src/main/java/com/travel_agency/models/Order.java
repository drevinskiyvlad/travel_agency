package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Order {
    @Getter @Setter private int id;
    @Getter @Setter private String code;
    @Getter @Setter private User customer;
    @Getter @Setter private Offer offer;
    @Getter @Setter private String orderStatus;
    @Getter @Setter private double price;


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", customer=" + customer.getFirstName() +
                ", offer=" + offer.getCode() +
                "orderStatus='" + orderStatus + '\'' +
                ", price=" + price +
                '}';
    }
}
