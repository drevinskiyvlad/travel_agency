package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.UserService;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.utils.ValidationMessageConstants;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = "index.jsp";
        req.getSession().removeAttribute("invalid_registration_message");

        DBManager dbManager = null;
        Connection con = null;

        try {
            dbManager = DBManager.getInstance();
            con = dbManager.getConnection();
            UserDTO userDTO = initializeUserDTO(req);
            String password = req.getParameter("password");

            UserDAO userDAO = new UserDAO(con);
            UserService userService = new UserService(userDAO);
            userService.addUser(userDTO, password);

            logger.info("User {} registered successfully: ", userDTO.getEmail());
            req.getSession().setAttribute("user", userDTO);
        }catch(ValidationException e){
            req.getSession().setAttribute("invalid_registration_message", e.getMessage());
            redirectPage = "registration.jsp";
        }catch(Exception e){
            logger.error("Unable to register user: " + e.getMessage(), e);
            req.getSession().setAttribute("invalid_registration_message", "Something went wrong, try again");
            redirectPage = "registration.jsp";
        }finally{
            if(dbManager != null)
                dbManager.closeConnection(con);
        }

        resp.sendRedirect(redirectPage);
    }

    private UserDTO initializeUserDTO(HttpServletRequest req) throws ValidationException {
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        if(email.equals("") || firstName.equals("") || lastName.equals("") || phone.equals("")){
            throw new ValidationException(ValidationMessageConstants.FILL_ALL_FIELDS);
        }

        return new UserDTO(email,"user",firstName,lastName,phone,false);
    }
}
