package com.travel_agency.controller.commands.our_offer;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.utils.exceptions.ValidationException;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.ValidationMessageConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class UpdateOfferCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateOfferCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("error");
        String redirectPage = PathConstants.OUR_OFFER;

        String code = req.getParameter("code");
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            MySQLOfferDAO offerDAO = new MySQLOfferDAO(con);
            OfferService service = new OfferService(offerDAO);

            if(!updateOffer(req, service, code))
                logger.error("Something went wrong, return value of update offer is false");

            logger.info("Offer with code {} successfully updated", code);
        } catch (ValidationException e) {
            req.getSession().setAttribute("error", e.getMessage());
            logger.error("Invalid parameters while updating offer: " + e.getMessage());
            redirectPage = PathConstants.UPDATE_OFFER + "?code=" + code;
        } catch (Exception e) {
            logger.error("Unable to update offer:" + e.getMessage(), e);
            redirectPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
        return PathConstants.COMMAND_REDIRECT;
    }

    private boolean updateOffer(HttpServletRequest req, OfferService service,String code) throws DAOException, ValidationException {
        checkIfFieldsAreNull(req);

        OfferDTO offerDTO = initOfferDTO(req,service,code);

        return service.updateOffer(offerDTO);
    }

    private void checkIfFieldsAreNull(HttpServletRequest req) throws ValidationException {
        String hotel = req.getParameter("hotelName");
        String placesString = req.getParameter("places");
        String priceString = req.getParameter("price");
        String discountString = req.getParameter("discount");

        if(placesString.equals("") || discountString.equals("") || hotel.equals("") || priceString.equals(""))
            throw new ValidationException(ValidationMessageConstants.FILL_ALL_FIELDS);
    }

    private OfferDTO initOfferDTO(HttpServletRequest req, OfferService service, String code){
        String hotel = req.getParameter("hotelName");
        String placesString = req.getParameter("places");
        String priceString = req.getParameter("price");
        String discountString = req.getParameter("discount");

        OfferDTO initialOfferDTO = service.getOffer(code);
        String offerType = req.getParameter("offerType");
        String hotelType = req.getParameter("hotelType");
        int places = Integer.parseInt(placesString);
        double discount = Double.parseDouble(discountString)/100;
        double price = Double.parseDouble(priceString);
        String city = initialOfferDTO.getCity();
        boolean isHot = initialOfferDTO.isHot();

        return new OfferDTO(code,offerType,hotel,hotelType,city,places,discount,isHot,true,price);
    }
}
