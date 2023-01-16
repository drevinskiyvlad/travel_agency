package com.travel_agency.models.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean banned;

    public UserDTO(String email, String role, String firstName, String lastName, String phone, boolean banned) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.banned = banned;
    }
}
