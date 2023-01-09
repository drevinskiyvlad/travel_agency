package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Service {
    @Getter @Setter private int id;
    @Getter @Setter private TransportService transportService;
    @Getter @Setter private HotelService hotelService;
    @Getter @Setter private double price;
    @Getter @Setter private boolean isActive;

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", transportService=\n" + transportService +
                ", hotelService=\n" + hotelService +
                ", price=" + price +
                ", is active=" + isActive +
                "}";
    }
}
