package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class Order {
    @Getter @Setter private int id;
    @Getter @Setter private String code;
    @Getter @Setter private User customer;
    @Getter @Setter private List<Offer> offers;
    @Getter @Setter private String orderStatus;
    @Getter @Setter private double price;
    @Getter @Setter private LocalDateTime bookedTime;
    @Getter @Setter private LocalDateTime payedTime;


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
