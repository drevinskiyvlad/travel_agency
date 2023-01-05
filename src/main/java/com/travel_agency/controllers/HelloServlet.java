package com.travel_agency.controllers;

import com.travel_agency.DB.DBManager;
import com.travel_agency.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/test")
public class HelloServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().removeAttribute("invalid_message");

        String login = req.getParameter("email");
        String password = req.getParameter("password");
        DBManager manager = DBManager.getInstance();

        User user = manager.getUser(login);

        if(user == null){
            returnBack(resp);
        } else if(password.equals(user.getPassword())){
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("index.jsp");
        } else{
            returnBack(resp);
        }
    }

    private void returnBack(HttpServletResponse resp) throws IOException {
        getServletContext().setAttribute("invalid_message", "Email or password are incorrect");
        resp.sendRedirect("authorization.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("authorization.jsp")
                .forward(req, resp);
    }
}