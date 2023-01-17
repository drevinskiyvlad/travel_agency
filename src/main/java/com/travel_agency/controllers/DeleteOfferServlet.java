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

@WebServlet("/deleteOffer")
public class DeleteOfferServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeleteOfferServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = "our-offer.jsp";

        Connection con = null;
        try {
            OfferDTO offerDTO = getOfferDTO(req);

            DBManager manager = DBManager.getInstance();
            con = manager.getConnection();
            OfferDAO dao = new OfferDAO(con);
            OfferService service = new OfferService(dao);

            if (!service.deleteOffer(offerDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
                redirectPage = "offer.jsp?code=" + offerDTO.getCode();
            }

            logger.info("Deleted offer with code: " + offerDTO.getCode());
        } catch (Exception e) {
            logger.error("Unable to delete offer: " + e.getMessage(), e);
            redirectPage = "error.jsp";
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
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
