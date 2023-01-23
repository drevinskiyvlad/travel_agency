package com.travel_agency.controller.comands.our_offer;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOrderDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.model.services.OrderService;
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
        String redirectPage = PathConstants.OUR_OFFER;

        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();

            UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
            OfferDTO offerDTO = getOfferDTO(req, con);

            MySQLOrderDAO dao = new MySQLOrderDAO(con);
            OrderService service = new OrderService(dao);

            if (!service.makeOrder(offerDTO, userDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
            }

            logger.info("User {} successfully made order", userDTO.getEmail());
        } catch (Exception e) {
            logger.error("Unable to make order: " + e.getMessage(), e);
            redirectPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
        return PathConstants.COMMAND_REDIRECT;
    }

    private OfferDTO getOfferDTO(HttpServletRequest req, Connection con) {
        String code = req.getParameter("code");
        MySQLOfferDAO dao = new MySQLOfferDAO(con);
        return new OfferService(dao).getOffer(code);
    }
}
