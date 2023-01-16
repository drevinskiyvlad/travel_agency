package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DBManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/deleteOrder")
public class DeleteOrderServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeleteOrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = "user-cabinet.jsp";
        Connection con = null;
        try {
            String code = req.getQueryString().split("code=")[1];
            DBManager manager = DBManager.getInstance();
            con = manager.getConnection();

            OrderDAO orderDAO = new OrderDAO(con);
            orderDAO.delete(code);

            logger.info("User deleted order with code: " + code);
        } catch (Exception e) {
            logger.error("Unable to delete order" + e.getMessage());
            redirectionPage = "error.jsp";
        } finally {
            DBManager.closeConnection(con);
        }
        resp.sendRedirect(redirectionPage);
    }
}
