package com.travel_agency.models.DTO;

import com.travel_agency.models.DAO.Order;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private final List<Order> orders;

    public UserDTO(String email, String role, String firstName, String lastName, String phone) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        orders = new CopyOnWriteArrayList<>();
    }
}
