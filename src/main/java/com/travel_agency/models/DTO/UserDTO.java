package com.travel_agency.models.DTO;

import com.travel_agency.models.DAO.Order;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean banned;
    private final List<Order> orders;

    public UserDTO(String email, String role, String firstName, String lastName, String phone, boolean banned) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.banned = banned;
        orders = new CopyOnWriteArrayList<>();
    }
}
