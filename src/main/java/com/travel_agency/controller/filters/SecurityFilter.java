package com.travel_agency.controller.filters;

import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;


/**
 * Main security filter.
 *
 * @author Drevinskyi Vladislav
 */
public class SecurityFilter implements Filter {

    // commands access
    private static final Map<String, List<String>> accessMap = new HashMap<>();
    private List<String> commons = new ArrayList<>();
    private List<String> outOfControl = new ArrayList<>();

    @Override
    public void init(FilterConfig config) {
        // roles
        accessMap.put("admin", asList(config.getInitParameter("admin")));
        accessMap.put("manager", asList(config.getInitParameter("manager")));
        accessMap.put("user", asList(config.getInitParameter("user")));
        // commons
        commons = asList(config.getInitParameter("common"));
        // out of control
        outOfControl = asList(config.getInitParameter("out-of-control"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (accessAllowed(request)) {
            chain.doFilter(request, response);
        } else {
            String errorMessages = "You do not have permission to access the requested resource";
            request.setAttribute("errorMessage", errorMessages);

            request.getRequestDispatcher(PathConstants.ERROR).forward(request, response);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String commandName = request.getParameter("action");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        UserDTO userDTO = (UserDTO) httpRequest.getSession().getAttribute("user");
        String userRole = userDTO.getRole();

        if (userRole == null) {
            return false;
        }

        return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
    }

    private List<String> asList(String param) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(param);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
