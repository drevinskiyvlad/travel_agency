package com.travel_agency.controllers;

import com.travel_agency.DB.DBManager;
import com.travel_agency.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        User user = new User(0,email,password,"user",firstName,lastName,phone,LocalDateTime.now());

        DBManager dbManager = DBManager.getInstance();

        dbManager.createUser(user);

        req.getSession().setAttribute("user", user);
        resp.sendRedirect("index.jsp");
    }
}
