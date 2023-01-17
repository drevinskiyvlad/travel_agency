package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.Order;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateOrderStatusServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String redirectionPage = "user-cabinet.jsp";
        Connection con = null;
        try {
            String code = req.getParameter("code");
            String status = req.getParameter("orderStatus");
            DBManager manager = DBManager.getInstance();
            con = manager.getConnection();

            updateStatus(con, code, status);

            logger.info("Manager updated order status with code: " + code + " to " + status);
        } catch (Exception e) {
            logger.error("Unable to update order" + e.getMessage());
            redirectionPage = "error.jsp";
        } finally {
            DBManager.closeConnection(con);
        }
        resp.sendRedirect(redirectionPage);
    }

    private static void updateStatus(Connection con, String code, String status) throws DAOException {
        OrderDAO orderDAO = new OrderDAO(con);
        Order order = orderDAO.read(code);
        orderDAO.update(order, status);
    }
}
