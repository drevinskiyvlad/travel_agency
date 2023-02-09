package com.travel_agency.controller.commands;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author Drevinskyi Vladislav
 */
public interface Command {
    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
