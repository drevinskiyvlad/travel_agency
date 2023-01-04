package com.travel_agency.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Hotel {
    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private String address;
    @Getter @Setter private City city;
    @Getter @Setter private String hotelType;
    @Getter @Setter private int numberOfAvailableRoom;
    @Getter @Setter private String description;
    @Getter @Setter private boolean isPartner;


    public Hotel(int id, String name,
                 String address, City city,
                 String hotelType, int numberOfAvailableRoom, boolean isPartner)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.hotelType = hotelType;
        this.numberOfAvailableRoom = numberOfAvailableRoom;
        this.isPartner = isPartner;
    }

    @Override
    public String toString() {
        if(description != null) {
            return "Hotel{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", city=" + city.getName() +
                    ", hotel type=" + hotelType +
                    ", number of available rooms=" + numberOfAvailableRoom +
                    ", description='" + description + '\'' +
                    ", isPartner=" + isPartner +
                    '}';
        }
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city=" + city.getName() +
                ", hotel type=" + hotelType +
                ", number of available rooms=" + numberOfAvailableRoom +
                ", isPartner=" + isPartner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return name.equals(hotel.name) && address.equals(hotel.address) && Objects.equals(city, hotel.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, city);
    }
}
