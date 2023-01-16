package com.travel_agency.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidationException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(ValidationException.class);
    public ValidationException(String message) {
        super(message);
        logger.error("Validation exception: " + message);
    }
}
