package com.travel_agency.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private String code;
    private String offerCode;
    private String userEmail;
    private String orderStatus;
    private double price;
}
