package com.travel_agency.utils;

import com.travel_agency.models.DAO.User;
import com.travel_agency.utils.HashPassword;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Validator() {}

    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean validatePhone(String phone){
        Pattern pattern = Pattern.compile("^\\+[(]?\\d{3}[)]?[-\\s\\.]?\\d{3}[-\\s\\.]?\\d{4,6}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean validatePassword(String password, User user){
        return HashPassword.validate(password, user.getPassword());
    }
}