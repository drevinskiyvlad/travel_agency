package com.travel_agency.controller.commands.our_offer;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DeleteOfferCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteOfferCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = PathConstants.OUR_OFFER;

        try {
            String code = req.getParameter("code");

            OfferService service = AppContext.getInstance().getOfferService();

            if (!service.deleteOffer(code)) {
                req.setAttribute("error", "Something went wrong, try again");
                redirectPage = PathConstants.OFFER + "?code=" + code;
            }

            logger.info("Deleted offer with code: " + code);
        } catch (Exception e) {
            logger.error("Unable to delete offer: " + e.getMessage(), e);
            redirectPage = PathConstants.ERROR;
        }

        resp.sendRedirect(redirectPage);
        return PathConstants.COMMAND_REDIRECT;
    }
}
