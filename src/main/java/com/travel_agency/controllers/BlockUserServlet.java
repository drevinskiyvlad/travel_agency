package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.services.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/blockUser")
public class BlockUserServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(BlockUserServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = "user-cabinet.jsp";

        try {
            String email = req.getQueryString().split("email=")[1];
            updateUserBlockedStatus(email);
            logger.info("User {} successfully banned",email);
        } catch (Exception e) {
            logger.error("Unable to change blocked status" + e.getMessage(),e);
            redirectionPage = "error.jsp";
        }
        resp.sendRedirect(redirectionPage);
    }

    private static void updateUserBlockedStatus(String email) throws DAOException {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        UserDAO userDAO = new UserDAO(con);
        UserService service = new UserService(userDAO);
        service.changeUserBlocked(email);
        DBManager.closeConnection(con);
    }
}
