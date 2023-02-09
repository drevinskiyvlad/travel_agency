package com.travel_agency.controller;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.controller.commands.CommandFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Main Controller Servlet.
 *
 * @author Drevinskyi Vladislav
 */
@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(req);

        String page = getPage(req, resp, command);

        RequestDispatcher dispatcher = req.getRequestDispatcher(page);

        if (!page.equals("redirect"))
            dispatcher.forward(req, resp);
    }

    private static String getPage(HttpServletRequest req, HttpServletResponse resp, Command command) throws IOException {
        String page = command.execute(req, resp);

        if (page == null)
            page = req.getContextPath();
        return page;
    }
}
