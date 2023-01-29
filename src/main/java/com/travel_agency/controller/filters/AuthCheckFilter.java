package com.travel_agency.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class AuthCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        if (httpServletRequest.getSession(false).getAttribute("user") == null) {
            req.getRequestDispatcher("authorization.jsp").forward(req, resp);
        }
        filterChain.doFilter(req, resp);
    }
}
