package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.OrderService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/makeOrder")
public class MakeOrderServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MakeOrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = "our-offer.jsp";

        Connection con = null;
        try {
            UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
            OfferDTO offerDTO = (OfferDTO) req.getAttribute("offerItem");

            DBManager manager = DBManager.getInstance();
            con = manager.getConnection();
            OrderDAO dao = new OrderDAO(con);
            OrderService service = new OrderService(dao);
            if (!service.makeOrder(offerDTO, userDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
            }
        } catch (Exception e) {
            logger.error("Unable to make order: " + e.getMessage(), e);
            redirectPage = "error.jsp";
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
    }
}
