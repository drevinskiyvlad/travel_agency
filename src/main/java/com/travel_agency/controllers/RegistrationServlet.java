package com.travel_agency.controllers;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.DAO.services.UserService;
import com.travel_agency.exceptions.ValidationException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String redirectPage = "index.jsp";
        req.getSession().setAttribute("invalid_registration_message", null);

        try {
            UserDTO userDTO = initializeUserDTO(req);
            String password = req.getParameter("password");

            UserService userService = initUserService();
            userService.addUser(userDTO, password);

            logger.info("User {} registered successfully: ", userDTO.getEmail());
            req.getSession().setAttribute("user", userDTO);
        }catch(ValidationException e){
            req.getSession().setAttribute("invalid_registration_message", e.getMessage());
            redirectPage = "registration.jsp";
        }

        resp.sendRedirect(redirectPage);
    }

    private static UserService initUserService() {
        DBManager dbManager = DBManager.getInstance();
        UserDAO userDAO = new UserDAO(dbManager.getConnection());
        return new UserService(userDAO);
    }

    private UserDTO initializeUserDTO(HttpServletRequest req) throws ValidationException {
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        if(email.equals("") || firstName.equals("") || lastName.equals("") || phone.equals("")){
            throw new ValidationException("All fields must be filled");
        }

        return new UserDTO(email,"user",firstName,lastName,phone);
    }
}
