package com.travel_agency.controller.commands.our_offer;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MakeOfferHotCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MakeOfferHotCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = PathConstants.OUR_OFFER;

        try {
            OfferService service = AppContext.getInstance().getOfferService();

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
        }

        resp.sendRedirect(redirectionPage);
        return PathConstants.COMMAND_REDIRECT;
    }
}
