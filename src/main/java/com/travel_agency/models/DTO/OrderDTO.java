package com.travel_agency.models.DTO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class OrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private String code;
    private OfferDTO offer;
    private String orderStatus;
    private double price;
}
