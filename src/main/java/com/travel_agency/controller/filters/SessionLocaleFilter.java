package com.travel_agency.controller.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class SessionLocaleFilter implements Filter {

    String baseLanguage;

    @Override
    public void init(FilterConfig filterConfig) {
        baseLanguage = filterConfig.getInitParameter("baseLanguage");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (req.getParameter("sessionLocale") != null) {
            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
        }
        if (req.getSession().getAttribute("lang") == null) {
            req.getSession().setAttribute("lang", baseLanguage);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
