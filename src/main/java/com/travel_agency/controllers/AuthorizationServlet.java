package com.travel_agency.controllers;

import com.travel_agency.DB.DBManager;
import com.travel_agency.models.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("is_login_valid", true);

        String login = req.getParameter("email");
        String password = req.getParameter("password");

        DBManager manager = DBManager.getInstance();

        User user = manager.getUser(login);
        redirect(req, resp, login, password, user);
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, String login, String password, User user) throws IOException {
        if (user != null && HashPassword.validate(password, user.getPassword())) {
            logUserIn(req, resp, user);
        }else {
            returnBack(req, resp, login);
        }
    }

    private void logUserIn(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        req.getSession().setAttribute("user", user);
        logger.info("User {} are logged in", user);
        resp.sendRedirect("user-cabinet.jsp");
    }

    private void returnBack(HttpServletRequest req, HttpServletResponse resp, String login) throws IOException {
        req.getSession().setAttribute("is_login_valid", false);
        logger.info("User {} is null or entered incorrect password", login);
        resp.sendRedirect("authorization.jsp");
    }
}