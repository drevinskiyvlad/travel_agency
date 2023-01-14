package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.DAO.services.UserService;
import com.travel_agency.exceptions.ValidationException;
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
        req.getSession().setAttribute("invalid_authorization_message", null);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        DBManager manager = DBManager.getInstance();
        UserDAO userDAO = new UserDAO(manager.getConnection());
        UserService userService = new UserService(userDAO);
        try {
            UserDTO userDTO = userService.signIn(email, password);
            req.getSession().setAttribute("user", userDTO);
        } catch (ValidationException e) {
            logger.info("User dont loggined: " + e.getMessage());
            req.getSession().setAttribute("invalid_authorization_message", e.getMessage());
        }

        resp.sendRedirect("user-cabinet.jsp");
    }
}