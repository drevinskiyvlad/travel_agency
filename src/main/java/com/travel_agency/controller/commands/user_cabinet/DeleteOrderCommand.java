package com.travel_agency.controller.commands.user_cabinet;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.OrderDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.Constants.PathConstants;
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
        String redirectionPage = PathConstants.USER_CABINET;
        Connection con = null;
        try {
            String code = req.getParameter("code");

            con = DBManager.getInstance().getConnection();
            OrderDAOImpl orderDAO = new OrderDAOImpl();
            orderDAO.delete(code);

            logger.info("User deleted order with code: " + code);
        } catch (Exception e) {
            logger.error("Unable to delete order" + e.getMessage());
            redirectionPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectionPage);
        return PathConstants.COMMAND_REDIRECT;
    }
}
