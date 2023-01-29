package com.travel_agency.controller.commands.authorization;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLUserDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.UserService;
import com.travel_agency.utils.Constants.ValidationMessageConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class SignUpCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignUpCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("invalid_registration_message");
        String redirectPage = PathConstants.USER_CABINET;

        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            UserDTO userDTO = initializeUserDTO(req);
            String password = req.getParameter("password");

            MySQLUserDAO userDAO = new MySQLUserDAO(con);
            UserService userService = new UserService(userDAO);

            userService.signUp(userDTO, password);

            req.getSession().setAttribute("user", userDTO);
            logger.info("User {} registered successfully: ", userDTO.getEmail());

        } catch (ValidationException e) {
            req.getSession().setAttribute("invalid_registration_message", e.getMessage());
            redirectPage = PathConstants.REGISTRATION;
        } catch (Exception e) {
            logger.error("Unable to register user: " + e.getMessage(), e);
            redirectPage = PathConstants.ERROR;
        } finally {
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
        return PathConstants.COMMAND_REDIRECT;
    }

    private UserDTO initializeUserDTO(HttpServletRequest req) throws ValidationException {
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        checkIfFieldsNotEmpty(email, firstName, lastName, phone);

        return new UserDTO(email, "user", firstName, lastName, phone, false);
    }

    private static void checkIfFieldsNotEmpty(String email, String firstName, String lastName, String phone) {
        if (email.equals("") || firstName.equals("") || lastName.equals("") || phone.equals("")) {
            throw new ValidationException(ValidationMessageConstants.FILL_ALL_FIELDS);
        }
    }
}
