package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
public class HotelService {
    @Getter @Setter private int id;
    @Getter @Setter private Hotel hotel;
    @Getter @Setter private String type;
    @Getter @Setter private double price;
    @Getter @Setter private boolean isActive;

    @Override
    public String toString() {
        return "HotelService{" +
                "id=" + id +
                ", hotel=" + hotel.getName() +
                ", roomType='" + type + '\'' +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelService)) return false;
        HotelService that = (HotelService) o;
        return hotel.equals(that.hotel) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotel, type);
    }
}
