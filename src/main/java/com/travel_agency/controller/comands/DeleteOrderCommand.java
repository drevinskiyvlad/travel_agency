package com.travel_agency.controller.comands;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.controller.Command;
import com.travel_agency.controller.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class DeleteOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteOrderCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = Path.USER_CABINET;
        Connection con = null;
        try {
            String code = req.getParameter("code");
            con = DBManager.getInstance().getConnection();

            OrderDAO orderDAO = new OrderDAO(con);
            orderDAO.delete(code);

            logger.info("User deleted order with code: " + code);
        } catch (Exception e) {
            logger.error("Unable to delete order" + e.getMessage());
            redirectionPage = Path.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectionPage);
        return Path.COMMAND_REDIRECT;
    }
}
