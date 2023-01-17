package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.DAO;
import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.Order;
import com.travel_agency.models.DAO.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/updateUserRole")
public class UpdateUserRoleServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateUserRoleServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String redirectionPage = "user-cabinet.jsp";
        Connection con = null;
        try {
            String email = req.getParameter("email");
            String role = req.getParameter("userRole");
            DBManager manager = DBManager.getInstance();
            con = manager.getConnection();

            updateStatus(con, email, role);

            logger.info("Admin updated user " + email + "role to " + role);
        } catch (Exception e) {
            logger.error("Unable to update order" + e.getMessage());
            redirectionPage = "error.jsp";
        } finally {
            DBManager.closeConnection(con);
        }
        resp.sendRedirect(redirectionPage);
    }

    private static void updateStatus(Connection con, String email, String role) throws DAOException {
        UserDAO userDAO = new UserDAO(con);
        User user = userDAO.read(email);
        userDAO.update(user, role);
    }
}
