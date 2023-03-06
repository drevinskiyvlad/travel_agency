package com.travel_agency.controller.commands.our_offer;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.OfferDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.Constants.PathConstants;
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
        String redirectPage = PathConstants.OUR_OFFER;

        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            OfferDAOImpl dao = new OfferDAOImpl(con);

            String code = req.getParameter("code");

            if (!dao.delete(code)) {
                req.setAttribute("error", "Something went wrong, try again");
                redirectPage = PathConstants.OFFER + "?code=" + code;
            }

            logger.info("Deleted offer with code: " + code);
        } catch (Exception e) {
            logger.error("Unable to delete offer: " + e.getMessage(), e);
            redirectPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
        return PathConstants.COMMAND_REDIRECT;
    }
}
