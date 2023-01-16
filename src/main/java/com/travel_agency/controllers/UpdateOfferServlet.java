package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.models.services.OfferService;
import com.travel_agency.utils.ValidationMessageConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/updateOffer")
public class UpdateOfferServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateOfferServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().removeAttribute("error");

        String code = req.getParameter("code");
        String redirectPage = "user-cabinet.jsp";
        Connection con = null;
        try {
            DBManager manager = DBManager.getInstance();
            con = manager.getConnection();
            OfferDAO offerDAO = new OfferDAO(con);
            OfferService service = new OfferService(offerDAO);

            if(!updateOffer(req, service))
                logger.error("Something went wrong, return value of update offer is false");

        } catch (ValidationException e) {
            req.getSession().setAttribute("error", e.getMessage());
            logger.error("Invalid parameters while updating offer: " + e.getMessage());
            redirectPage = "update-offer.jsp?code=" + code;
        } catch (Exception e) {
            logger.error("Unable to update offer:" + e.getMessage(), e);
            redirectPage = "error.jsp";
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
    }

    private static boolean updateOffer(HttpServletRequest req, OfferService service) throws DAOException, ValidationException {
        String type = req.getParameter("offerType");
        String vacancyString = req.getParameter("vacancy");
        String discountString = req.getParameter("discount");
        String code = req.getParameter("code");

        if(vacancyString.equals("") || discountString.equals(""))
            throw new ValidationException(ValidationMessageConstants.FILL_ALL_FIELDS);

        int vacancy = Integer.parseInt(vacancyString);
        double discount = Double.parseDouble(discountString)/100;
        return service.updateOffer(code, type, vacancy, discount);
    }
}
