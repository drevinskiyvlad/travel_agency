package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.services.OfferService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/makeOfferHot")
public class ChangeOfferHotServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ChangeOfferHotServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = "our-offer.jsp";
        DBManager manager = null;
        Connection con = null;
        try {
            OfferDTO offerDTO = getOfferDTO(req);
            manager = DBManager.getInstance();
            con = manager.getConnection();
            OfferDAO dao = new OfferDAO(con);
            OfferService service = new OfferService(dao);
            if (!service.updateOfferIsHot(offerDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
            }
        } catch (Exception e) {
            logger.error("Unable to make offer hot: " + e.getMessage(), e);
            redirectionPage = "error.jsp";
        } finally {
            if (manager != null)
                DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectionPage);
    }

    private static OfferDTO getOfferDTO(HttpServletRequest req) {

        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OfferService service = getOfferService(con);

        String code = req.getParameter("code");
        OfferDTO offerDTO = service.getOffer(code);

        DBManager.closeConnection(con);
        return offerDTO;
    }

    private static OfferService getOfferService(Connection con) {
        OfferDAO dao = new OfferDAO(con);
        return new OfferService(dao);
    }
}
