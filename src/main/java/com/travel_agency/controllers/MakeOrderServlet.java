package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.OrderService;
import jakarta.servlet.ServletException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirectPage = "our-offer.jsp";
        OfferDTO offerDTO = null;
        DBManager manager = null;
        Connection con = null;
        try {
            UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
            offerDTO = (OfferDTO) req.getAttribute("offerItem");

            manager = DBManager.getInstance();
            con = manager.getConnection();
            OrderDAO dao = new OrderDAO(con);
            OrderService service = new OrderService(dao);
            if (!service.makeOrder(offerDTO, userDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
                redirectPage = "offer.jsp?code=" + offerDTO.getCode();
            }
        } catch(Exception e){
            logger.error("Unable to make order: " + e.getMessage(), e);
            assert offerDTO != null;
            redirectPage = "offer.jsp?code=" + offerDTO.getCode();
            req.setAttribute("error", "Something went wrong, try again");
        } finally{
            if(manager != null)
                manager.closeConnection(con);
        }

        req.getRequestDispatcher(redirectPage).forward(req,resp);
    }
}
