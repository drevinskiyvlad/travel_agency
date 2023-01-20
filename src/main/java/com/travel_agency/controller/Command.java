package com.travel_agency.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
