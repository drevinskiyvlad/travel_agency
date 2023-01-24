package com.travel_agency.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class HashPassword {
    private HashPassword() {
    }

    private static final String PARAMETERS;
    private static final int INDEX_OF_SUBSTRING;
    private static final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16, 16);

    static {
        String hashWithParameters = argon2.hash(2, 15 * 1024, 1, "1".toCharArray());
        INDEX_OF_SUBSTRING = hashWithParameters.lastIndexOf("p=1$") + 4;
        PARAMETERS = hashWithParameters.substring(0, INDEX_OF_SUBSTRING);
    }

    public static String hash(String password) {
        String hashWithParameters = argon2.hash(2, 15 * 1024, 1, password.toCharArray());
        return hashWithParameters.substring(INDEX_OF_SUBSTRING);
    }

    public static boolean validate(String password, String hashedPassword) {
        String fullHashedPassword = PARAMETERS + hashedPassword;
        return argon2.verify(fullHashedPassword, password.toCharArray());
    }
}