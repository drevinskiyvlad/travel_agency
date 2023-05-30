package com.travel_agency.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String code;
    private String offerCode;
    private String userEmail;
    private String orderStatus;
    private double price;
}
