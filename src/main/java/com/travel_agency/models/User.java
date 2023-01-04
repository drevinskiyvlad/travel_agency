package com.travel_agency.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    @Getter @Setter private int id;
    @Getter @Setter private String email;
    @Getter @Setter private String password;
    @Getter @Setter private String userRole;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String phone;
    @Getter @Setter private String details;
    @Getter @Setter private LocalDateTime userFrom;
    @Getter @Setter private List<Order> orders;

    public User(int id, String email, String password,
                String userRole, String firstName, String lastName,
                String phone, LocalDateTime userFrom) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.userFrom = userFrom;
        orders = new CopyOnWriteArrayList<>();//arraylist for multithreading
    }

    @Override
    public String toString() {
        if(details != null) {
            return "User(" + userRole +
                    "){" +
                    "id=" + id +
                    ", email='" + email + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", details='" + details + '\'' +
                    ", userFrom=" + userFrom +
                    '}';
        }
        return "User(" + userRole +
                "){" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", userFrom=" + userFrom +
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
