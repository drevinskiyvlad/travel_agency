package com.travel_agency.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class TransportCompany {
    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private City city;
    @Getter @Setter private String HQAddress;
    @Getter @Setter private String companyType;
    @Getter @Setter private String description;
    @Getter @Setter private boolean isPartner;

    public TransportCompany(int id, String name,
                            City city, String HQAddress,
                            String companyType, boolean isPartner) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.HQAddress = HQAddress;
        this.companyType = companyType;
        this.isPartner = isPartner;
    }

    @Override
    public String toString() {
        if(description != null) {
            return "TransportCompany{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", city=" + city.getName() +
                    ", HQAddress='" + HQAddress + '\'' +
                    ", companyType='" + companyType + '\'' +
                    ", description='" + description + '\'' +
                    ", isPartner=" + isPartner +
                    '}';
        }
        return "TransportCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city.getName() +
                ", HQAddress='" + HQAddress + '\'' +
                ", companyType='" + companyType + '\'' +
                ", isPartner=" + isPartner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportCompany)) return false;
        TransportCompany that = (TransportCompany) o;
        return name.equals(that.name) && city.equals(that.city) && HQAddress.equals(that.HQAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, HQAddress);
    }
}
