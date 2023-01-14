package com.travel_agency.models.DAO;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Order {
    @Getter @Setter private int id;
    @Getter @Setter private String code;
    @Getter @Setter private User user;
    @Getter @Setter private Offer offer;
    @Getter @Setter private String orderStatus;
    @Getter @Setter private double price;

    public Order(int id, String code, User user, Offer offer, String orderStatus) {
        this.id = id;
        this.code = code;
        this.user = user;
        this.offer = offer;
        this.orderStatus = orderStatus;
        price = offer.getPrice();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", customer=" + user.getFirstName() +
                " " + user.getLastName() +
                ", offer=" + offer.getCode() +
                ", orderStatus='" + orderStatus + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(code, order.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}