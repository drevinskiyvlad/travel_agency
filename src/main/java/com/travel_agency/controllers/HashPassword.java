package com.travel_agency.controllers;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HashPassword{
    private HashPassword() {
    }

    private static String parameters;
    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16,16);

    public static String hash(String password){
        String hashWithParameters = argon2.hash(2,15*1024,1, password.toCharArray());
        int indexOfSubstring = hashWithParameters.lastIndexOf("p=1$") + 4;
        parameters = hashWithParameters.substring(0,indexOfSubstring);
        return hashWithParameters.substring(indexOfSubstring);
    }

    public static boolean validate(String password, String hashedPassword){
        String fullHashedPassword = parameters + hashedPassword;
        return argon2.verify(fullHashedPassword, password.toCharArray());
    }
}