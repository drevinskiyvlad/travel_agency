package com.travel_agency.controller.commands.user_cabinet;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLUserDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.model.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class BlockUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BlockUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = PathConstants.USER_CABINET;

        try {
            String email = req.getParameter("email");
            updateUserBlockedStatus(email);
            logger.info("User {} successfully changed block status", email);
        } catch (Exception e) {
            logger.error("Unable to change blocked status" + e.getMessage(), e);
            redirectionPage = PathConstants.ERROR;
        }
        resp.sendRedirect(redirectionPage);
        return PathConstants.COMMAND_REDIRECT;
    }

    private static void updateUserBlockedStatus(String email) throws DAOException {
        Connection con = DBManager.getInstance().getConnection();
        MySQLUserDAO userDAO = new MySQLUserDAO(con);
        UserService service = new UserService(userDAO);
        service.changeUserBlocked(email);
        DBManager.closeConnection(con);
    }
}
