package com.travel_agency.controller.comands;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.controller.Command;
import com.travel_agency.controller.Path;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.OfferService;
import com.travel_agency.models.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class MakeOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MakeOrderCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = Path.OUR_OFFER;

        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();

            UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
            OfferDTO offerDTO = getOfferDTO(req, con);

            OrderDAO dao = new OrderDAO(con);
            OrderService service = new OrderService(dao);

            if (!service.makeOrder(offerDTO, userDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
            }

            logger.info("User {} successfully made order", userDTO.getEmail());
        } catch (Exception e) {
            logger.error("Unable to make order: " + e.getMessage(), e);
            redirectPage = Path.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
        return Path.COMMAND_REDIRECT;
    }

    private OfferDTO getOfferDTO(HttpServletRequest req, Connection con) {
        String code = req.getParameter("code");
        OfferDAO dao = new OfferDAO(con);
        return new OfferService(dao).getOffer(code);
    }
}
