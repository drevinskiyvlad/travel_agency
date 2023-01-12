package com.travel_agency.controllers;

import com.travel_agency.DB.DBManager;
import com.travel_agency.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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

        if(isUserValid(user) && dbManager.createUser(user)) {
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

        String hashedPassword = hashPassword(password);

        return new User(0,email,hashedPassword,"user",firstName,lastName,phone);
    }

    private static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 8,16);
        return argon2.hash(2,15*1024,1, password.toCharArray());
    }

    private boolean isUserValid(User user) {
        if(!isEmailValid(user.getEmail())) {
            logger.info("User {} dont registered because of invalid email",
                    user.getEmail());
            return false;
        }else if(!isPhoneValid(user.getPhone())){
            logger.info("User {} dont registered because of invalid phone number",
                    user.getEmail());
            return false;
        }
        return true;
    }

    private boolean isPhoneValid(String phone) {
        Pattern pattern = Pattern.compile("^\\+[(]?\\d{3}[)]?[-\\s\\.]?\\d{3}[-\\s\\.]?\\d{4,6}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
