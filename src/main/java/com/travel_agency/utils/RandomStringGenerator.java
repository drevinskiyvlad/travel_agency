package com.travel_agency.utils;

import java.util.Random;

/**
 * Util to generate random string for codes
 */
public class RandomStringGenerator {
    private RandomStringGenerator() {
    }

    private static final Random random = new Random();

    /**
     * @param length Length of returned string
     * @return Random string
     */
    public static String getString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
