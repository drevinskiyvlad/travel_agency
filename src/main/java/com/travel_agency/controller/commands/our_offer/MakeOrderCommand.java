package com.travel_agency.controller.commands.our_offer;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.OfferDAOImpl;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.model.services.OrderService;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MakeOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MakeOrderCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = PathConstants.OUR_OFFER;

        try {

            UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
            OfferDTO offerDTO = getOfferDTO(req);

            OrderService service = AppContext.getInstance().getOrderService();

            if (!service.makeOrder(offerDTO, userDTO)) {
                req.setAttribute("error", "Something went wrong, try again");
            }

            logger.info("User {} successfully made order", userDTO.getEmail());
        } catch (Exception e) {
            logger.error("Unable to make order: " + e.getMessage(), e);
            redirectPage = PathConstants.ERROR;
        }

        resp.sendRedirect(redirectPage);
        return PathConstants.COMMAND_REDIRECT;
    }

    private OfferDTO getOfferDTO(HttpServletRequest req) {
        String code = req.getParameter("code");
        OfferDAOImpl dao = new OfferDAOImpl();
        return new OfferService(dao).getOffer(code);
    }
}
