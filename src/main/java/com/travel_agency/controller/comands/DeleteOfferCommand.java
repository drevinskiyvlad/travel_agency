package com.travel_agency.controller.comands;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.controller.Command;
import com.travel_agency.controller.Path;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.services.OfferService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class DeleteOfferCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteOfferCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = Path.OUR_OFFER;

        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            OfferDAO dao = new OfferDAO(con);
            OfferService service = new OfferService(dao);

            String code = req.getParameter("code");
            OfferDTO offerDTO = service.getOffer(code);

            if (!service.deleteOffer(offerDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
                redirectPage = Path.OFFER + "?code=" + code;
            }

            logger.info("Deleted offer with code: " + code);
        } catch (Exception e) {
            logger.error("Unable to delete offer: " + e.getMessage(), e);
            redirectPage = Path.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
        return Path.COMMAND_REDIRECT;
    }
}
