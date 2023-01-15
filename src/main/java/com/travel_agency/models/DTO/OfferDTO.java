package com.travel_agency.models.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class OfferDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private String code;
    private String type;
    private String transportCompany;
    private String hotel;
    private String city;
    private int vacancy;
    private double discount;
    private boolean isHot;
    private double price;
}
