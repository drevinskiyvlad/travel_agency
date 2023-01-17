package com.travel_agency.filters;

import com.travel_agency.DB.DBManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Connection;

public class CreateOfferFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();

        DBManager.closeConnection(con);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
