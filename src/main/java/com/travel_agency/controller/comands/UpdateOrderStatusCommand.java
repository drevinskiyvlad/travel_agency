package com.travel_agency.controller.comands;

import com.travel_agency.DB.DAO.impl.MySQL.MySQLOrderDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.controller.Command;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.Order;
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
        MySQLOrderDAO orderDAO = new MySQLOrderDAO(con);
        Order order = orderDAO.read(code);
        orderDAO.update(order, status);
    }
}
