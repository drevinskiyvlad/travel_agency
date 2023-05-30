package com.travel_agency.utils;

import com.travel_agency.model.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Validator() {
    }

    /**
     * Check if email match to regular expression
     */
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    /**
     * Check if phone match to regular expression
     */
    public static boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile("^\\+[(]?\\d{3}[)]?[-\\s\\.]?\\d{3}[-\\s\\.]?\\d{4,6}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * Check if password match to some rules
     */
    public static boolean validatePassword(String password) {
        return password.length() >= 4;
    }

    /**
     * Rehash password and check if this password correspond to user
     */
    public static boolean checkPasswordCorrect(String password, User user) {
        return HashPassword.validate(password, user.getPassword());
    }

    /**
     * Check if discount not less than 5% and not more than 25%
     */
    public static boolean validateDiscount(double discount) {
        return discount <= 0.25 && discount >= 0.05;
    }

    public static boolean validatePlaces(int places){
        return places > 0;
    }
}
