package com.travel_agency.models.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
public class Hotel {
    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private String city;
    @Getter @Setter private String hotelType;
    @Getter @Setter private int vacancy;
    @Getter @Setter private double price;

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + city + '\'' +
                ", hotel type=" + hotelType +
                ", vacancy=" + vacancy +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return name.equals(hotel.name) && city.equals(hotel.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city);
    }
}
