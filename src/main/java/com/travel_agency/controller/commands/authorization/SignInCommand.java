package com.travel_agency.controller.commands.authorization;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.UserService;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.utils.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignInCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("invalid_authorization_message");
        String redirectPage = PathConstants.USER_CABINET;

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            UserService userService = AppContext.getInstance().getUserService();

            UserDTO userDTO = userService.signIn(email, password);
            req.getSession().setAttribute("user", userDTO);

            logger.info("User {} signed in successfully", userDTO.getEmail());
        } catch (ValidationException e) {
            req.getSession().setAttribute("invalid_authorization_message", e.getMessage());
        } catch (Exception e) {
            logger.error("User dont signed in: " + e.getMessage(), e);
            redirectPage = PathConstants.ERROR;
        }

        resp.sendRedirect(redirectPage);

        return (PathConstants.COMMAND_REDIRECT);
    }
}
