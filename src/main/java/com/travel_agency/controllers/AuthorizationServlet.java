package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.UserService;
import com.travel_agency.exceptions.ValidationException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AuthorizationServlet.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("invalid_authorization_message");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        try {
            UserDAO userDAO = new UserDAO(con);
            UserService userService = new UserService(userDAO);
            UserDTO userDTO = userService.signIn(email, password);
            req.getSession().setAttribute("user", userDTO);
        } catch (ValidationException e) {
            req.getSession().setAttribute("invalid_authorization_message", e.getMessage());
        } catch(Exception e){
            logger.error("User dont loggined: " + e.getMessage(), e);
            req.getSession().setAttribute("invalid_authorization_message", "Something went wrong, try again");
        } finally{
            manager.closeConnection(con);
        }

        resp.sendRedirect("user-cabinet.jsp");
    }
}