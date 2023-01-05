package com.travel_agency.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
public class Offer {
    @Getter @Setter private int id;
    @Getter @Setter private Service service;
    @Getter @Setter private LocalDateTime from;
    @Getter @Setter private LocalDateTime to;
    @Getter @Setter private String type;
    @Getter @Setter private int vacancy;
    @Getter @Setter private double discount;
    @Getter @Setter private boolean isHot;

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", service=\n" + service +
                ", from=" + from +
                ", to=" + to +
                ", offer type=" + type +
                ", vacancy=" + vacancy +
                ", discount=" + (discount * 100) +
                "%, hot=" + isHot +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return service.equals(offer.service) && from.equals(offer.from) && to.equals(offer.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, from, to);
    }
}
