package com.travel_agency.utils;

import com.travel_agency.utils.HashPassword;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestHashPassword {

    private String password;
    String hashedPassword;

    @BeforeAll
    void initialize(){
        password = "Hello world!";
        hashedPassword = HashPassword.hash(password);
    }

    @Test
    void testHash(){
        assertNotEquals(hashedPassword, password);
    }

    @Test
    void testValidate(){
        assertTrue(HashPassword.validate(password, hashedPassword));
    }
}
