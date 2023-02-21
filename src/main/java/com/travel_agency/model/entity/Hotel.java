package com.travel_agency.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel implements Entity {
    private int id;
    private String name;
    private String type;
    private String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        Hotel hotel = (Hotel) o;
        return name.equals(hotel.name) && type.equals(hotel.type) && city.equals(hotel.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, city);
    }
}
