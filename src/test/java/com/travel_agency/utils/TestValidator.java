package com.travel_agency.utils;

import com.travel_agency.model.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestValidator {

    private String validValue;
    private String invalidValue;

    @Test
    void testValidateEmail(){
        validValue = "test@examole.com";
        invalidValue = "test";

        assertTrue(Validator.validateEmail(validValue));
        assertFalse(Validator.validateEmail(invalidValue));
    }

    @Test
    void testValidatePhone(){
        validValue = "+380123456789";
        invalidValue = "123456789";

        assertTrue(Validator.validatePhone(validValue));
        assertFalse(Validator.validatePhone(invalidValue));
    }

    @Test
    void testValidatePassword(){
        validValue = RandomStringGenerator.getString(8);
        invalidValue = "123";

        assertTrue(Validator.validatePassword(validValue));
        assertFalse(Validator.validatePassword(invalidValue));
    }

    @Test
    void testPasswordCorrect(){
        User user = new User(1, "test@email.com", "password", "user", "Test", "User", "1234567890", false);

        validValue = user.getPassword();
        invalidValue = RandomStringGenerator.getString(8);

        user.setPassword(HashPassword.hash(user.getPassword()));

        assertTrue(Validator.checkPasswordCorrect(validValue, user));
        assertFalse(Validator.checkPasswordCorrect(invalidValue, user));
    }

    @Test
    void testValidateDiscount(){
        double validValue = 0.15;
        double invalidValue1 = 0.4;
        double invalidValue2 = 0.01;

        assertTrue(Validator.validateDiscount(validValue));
        assertFalse(Validator.validateDiscount(invalidValue1));
        assertFalse(Validator.validateDiscount(invalidValue2));
    }
}
