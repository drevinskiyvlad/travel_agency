package com.travel_agency.controller.comands;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.controller.Command;
import com.travel_agency.controller.Path;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignInCommand.class);
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("invalid_authorization_message");
        String redirectPage = Path.USER_CABINET;

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(con);
            UserService userService = new UserService(userDAO);

            UserDTO userDTO = userService.signIn(email, password);
            req.getSession().setAttribute("user", userDTO);

            logger.info("User {} signed in successfully", userDTO.getEmail());
        } catch (ValidationException e) {
            req.getSession().setAttribute("invalid_authorization_message", e.getMessage());
        } catch(Exception e){
            logger.error("User dont signed in: " + e.getMessage(), e);
            redirectPage = Path.ERROR;
        } finally{
            DBManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);

        return(Path.COMMAND_REDIRECT);
    }
}
