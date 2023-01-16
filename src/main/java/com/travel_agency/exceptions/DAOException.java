package com.travel_agency.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DAOException extends SQLException {
    private static final Logger logger = LogManager.getLogger(DAOException.class);
    public DAOException(String message) {
        super(message);
        logger.error("DAOException: " + message);
    }
}
