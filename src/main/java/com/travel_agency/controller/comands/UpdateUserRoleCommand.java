package com.travel_agency.controller.comands;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.controller.Command;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class UpdateUserRoleCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateUserRoleCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectionPage = PathConstants.USER_CABINET;
        Connection con = null;
        try {
            String email = req.getParameter("email");
            String role = req.getParameter("userRole");
            con = DBManager.getInstance().getConnection();

            updateStatus(con, email, role);

            logger.info("Admin updated user " + email + "role to " + role);
        } catch (Exception e) {
            logger.error("Unable to update order" + e.getMessage());
            redirectionPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }
        resp.sendRedirect(redirectionPage);
        return PathConstants.COMMAND_REDIRECT;
    }

    private static void updateStatus(Connection con, String email, String role) throws DAOException {
        UserDAO userDAO = new UserDAO(con);
        User user = userDAO.read(email);
        userDAO.update(user, role);
    }
}
