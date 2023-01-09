package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
public class TransportCompany {
    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private String HQAddress;
    @Getter @Setter private int vacancy;
    @Getter @Setter private double price;



    @Override
    public String toString() {
        return "TransportCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", HQAddress='" + HQAddress + '\'' +
                ", vacancy='" + vacancy + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportCompany)) return false;
        TransportCompany that = (TransportCompany) o;
        return name.equals(that.name) && HQAddress.equals(that.HQAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, HQAddress);
    }
}
