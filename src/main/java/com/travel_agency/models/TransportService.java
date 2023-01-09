package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
public class TransportService {
    @Getter @Setter private int id;
    @Getter @Setter private TransportCompany transportCompany;
    @Getter @Setter private City fromCity;
    @Getter @Setter private City toCity;
    @Getter @Setter private double price;
    @Getter @Setter private int numberOfFreeSeats;
    @Getter @Setter private boolean isActive;

    @Override
    public String toString() {
        return "TransportService{" +
                "id=" + id +
                ", transport company=" + transportCompany.getName() +
                ", from city=" + fromCity.getName() +
                ", to city=" + toCity.getName() +
                ", number of free seats=" + numberOfFreeSeats +
                ", price=" + price +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportService)) return false;
        TransportService that = (TransportService) o;
        return Double.compare(that.price, price) == 0 && isActive == that.isActive && transportCompany.equals(that.transportCompany) && fromCity.equals(that.fromCity) && toCity.equals(that.toCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transportCompany, fromCity, toCity, price, isActive);
    }
}
