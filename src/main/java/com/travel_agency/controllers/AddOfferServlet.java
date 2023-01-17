package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.services.OfferService;
import com.travel_agency.utils.RandomStringGenerator;
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

@WebServlet("/addOffer")
public class AddOfferServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddOfferServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("error");

        String code = RandomStringGenerator.getString(8);
        String redirectPage = "our-offer.jsp";
        Connection con = null;
        try {
            DBManager manager = DBManager.getInstance();
            con = manager.getConnection();
            OfferDAO offerDAO = new OfferDAO(con);
            OfferService service = new OfferService(offerDAO);

            if(!createOffer(req, service, code))
                logger.error("Something went wrong, return value of create offer is false");

            logger.info("Offer with code {} successfully added", code);
        } catch (ValidationException e) {
            req.getSession().setAttribute("error", e.getMessage());
            logger.error("Invalid parameters while creating offer: " + e.getMessage());
            redirectPage = "update-offer.jsp?code=" + code;
        } catch (Exception e) {
            logger.error("Unable to update offer:" + e.getMessage(), e);
            redirectPage = "error.jsp";
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
    }
    private boolean createOffer(HttpServletRequest req, OfferService service, String code) throws DAOException, ValidationException {
        checkIfFieldsAreNull(req);

        OfferDTO offerDTO = initOfferDTO(req,code);

        return service.createOffer(offerDTO);
    }

    private void checkIfFieldsAreNull(HttpServletRequest req) throws ValidationException {
        String hotel = req.getParameter("hotelName");
        String placesString = req.getParameter("places");
        String priceString = req.getParameter("price");
        String discountString = req.getParameter("discount");
        String city = req.getParameter("city");

        if(placesString.equals("") || discountString.equals("") || hotel.equals("") || priceString.equals("") || city.equals(""))
            throw new ValidationException(ValidationMessageConstants.FILL_ALL_FIELDS);
    }

    private OfferDTO initOfferDTO(HttpServletRequest req, String code){
        String placesString = req.getParameter("places");
        String priceString = req.getParameter("price");
        String discountString = req.getParameter("discount");

        String offerType = req.getParameter("offerType");
        String hotelType = req.getParameter("hotelType");
        String hotel = req.getParameter("hotelName");
        int places = Integer.parseInt(placesString);
        double discount = Double.parseDouble(discountString)/100;
        double price = Double.parseDouble(priceString);
        String city = req.getParameter("city");
        boolean isHot = false;
        return new OfferDTO(code,offerType,hotel,hotelType,city,places,discount,isHot,price);
    }
}
