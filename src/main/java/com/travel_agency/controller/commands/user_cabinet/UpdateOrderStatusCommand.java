package com.travel_agency.controller.commands.user_cabinet;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.OrderDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.model.entity.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class UpdateOrderStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateOrderStatusCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = PathConstants.USER_CABINET;
        Connection con = null;
        try {
            String code = req.getParameter("code");
            String status = req.getParameter("orderStatus");
            con = DBManager.getInstance().getConnection();

            updateStatus(con, code, status);

            logger.info("Manager updated order status with code: " + code + " to " + status);
        } catch (Exception e) {
            logger.error("Unable to update order" + e.getMessage());
            redirectionPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }
        resp.sendRedirect(redirectionPage);
        return PathConstants.COMMAND_REDIRECT;
    }

    private static void updateStatus(Connection con, String code, String status) throws DAOException {
        OrderDAOImpl orderDAO = new OrderDAOImpl(con);
        Order order = orderDAO.read(code);
        orderDAO.update(order, status);
    }
}
