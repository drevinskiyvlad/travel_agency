package com.travel_agency.controllers;

import com.travel_agency.DB.DBManager;
import com.travel_agency.models.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final Logger logger = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String redirectPage = "index.jsp";

        req.getSession().setAttribute("is_register_valid", true);

        User user = initializeUser(req);

        logger.info("User {} is trying to register", user);

        DBManager dbManager = DBManager.getInstance();

        if(isUserValid(req,user) && dbManager.createUser(user)) {
            req.getSession().setAttribute("user", user);
            logger.info("User {} is registered successfully", user.getEmail());
        } else{
            redirectPage = "registration.jsp";
            req.getSession().setAttribute("is_register_valid", false);
        }

        resp.sendRedirect(redirectPage);
    }

    private User initializeUser(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");

        return new User(0,email,password,"user",firstName,lastName,phone);
    }

    private boolean isUserValid(HttpServletRequest req, User user) {
        if(!isEmailValid(user.getEmail()) || !isPhoneValid(user.getPhone())) {
            logger.info(
                    "User {} dont registered because of invalid email or number",
                    user.getEmail());
            return false;
        }
        return true;
    }

    private boolean isPhoneValid(String phone) {
        Pattern pattern = Pattern.compile("^[\\+][(]?[0-9]{3}[)]?[-\\s\\.]?\\d{3}[-\\s\\.]?[0-9]{4,6}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
