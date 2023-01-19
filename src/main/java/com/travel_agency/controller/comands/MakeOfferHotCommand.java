package com.travel_agency.controller.comands;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.controller.Command;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.services.OfferService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class MakeOfferHotCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MakeOfferHotCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = PathConstants.OUR_OFFER;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            OfferDAO dao = new OfferDAO(con);
            OfferService service = new OfferService(dao);

            String code = req.getParameter("code");
            OfferDTO offerDTO = service.getOffer(code);

            if (!service.updateOfferIsHot(offerDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
                logger.warn("Something went wrong, return value of update is false");
            }

            logger.info("Offer with code {} successfuly made hot", code);
        } catch (Exception e) {
            logger.error("Unable to make offer hot: " + e.getMessage(), e);
            redirectionPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectionPage);
        return PathConstants.COMMAND_REDIRECT;
    }
}
