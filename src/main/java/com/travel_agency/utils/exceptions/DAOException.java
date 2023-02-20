package com.travel_agency.utils.exceptions;

import java.sql.SQLException;

public class DAOException extends SQLException {
    public DAOException(String message) {
        super(message);
    }
}
