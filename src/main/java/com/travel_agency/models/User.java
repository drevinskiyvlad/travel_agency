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

    public enum USER_ROLE{
        NON_AUTHORIZED(1, "non authorized"),
        USER(2, "user"),
        MANAGER(3, "manager"),
        ADMIN(4, "admin");

        @Getter private final int id;
        @Getter private final String name;

        USER_ROLE(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static int getIdOfUserRole(String name){
        USER_ROLE[] userRoles = USER_ROLE.values();

        for (USER_ROLE role : userRoles) {
            if (name.equals(role.getName()))
                return role.getId();
        }

        throw new IllegalArgumentException("Invalid name of user role");
    }

    public static String getNameOfUserRole(int id){
        USER_ROLE[] userRoles = USER_ROLE.values();

        if(id > userRoles.length || id < 0)
            throw new IllegalArgumentException("Invalid id of user role");

        for (USER_ROLE role : userRoles) {
            if (id == role.getId())
                return role.getName();
        }

        throw new IllegalArgumentException("Invalid id of user role");
    }

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
