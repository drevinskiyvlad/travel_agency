package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

public class Order {
    @Getter @Setter private int id;
    @Getter @Setter private String code;
    @Getter @Setter private User customer;
    @Getter @Setter private List<Offer> offers;
    @Getter @Setter private String orderStatus;
    @Getter @Setter private double price;
    @Getter @Setter private Date bookedTime;
    @Getter @Setter private Date payedTime;

    public Order(int id, String code, User customer,
                 List<Offer> offers, String orderStatus,
                 double price, Date bookedTime, Date payedTime) {
        this.id = id;
        this.code = code;
        this.customer = customer;
        this.offers = offers;
        this.orderStatus = orderStatus;
        this.price = price;
        this.bookedTime = bookedTime;
        this.payedTime = payedTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", customer=" + customer.getFirstName() +
                ", offers=\n" + offers +
                "\norderStatus='" + orderStatus + '\'' +
                ", price=" + price +
                ", bookedTime=" + bookedTime +
                ", payedTime=" + payedTime +
                '}';
    }
}
