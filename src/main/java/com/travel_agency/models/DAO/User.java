package com.travel_agency.models.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
public class User {
    private int id;
    private String email;
    private String password;
    private String userRole;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean banned;

    public User(int id, String email, String password, String userRole, String firstName, String lastName, String phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User(" + userRole +
                "){" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", banned='" + banned + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }


}
