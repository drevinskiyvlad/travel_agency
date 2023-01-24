package com.travel_agency.model.DTO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean blocked;

    public UserDTO(String email, String role, String firstName, String lastName, String phone, boolean blocked) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.blocked = blocked;
    }
}
