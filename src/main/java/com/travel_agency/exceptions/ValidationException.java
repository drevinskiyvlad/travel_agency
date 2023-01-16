package com.travel_agency.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
