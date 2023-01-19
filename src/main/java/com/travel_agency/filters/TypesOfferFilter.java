package com.travel_agency.filters;

import com.travel_agency.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.exceptions.DAOException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;

public class TypesOfferFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(TypesOfferFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {
            setOfferTypesToSession(req);
        } catch (Exception e) {
            logger.error("Unable to set offer and hotel types to session: " + e.getMessage(), e);
            req.getRequestDispatcher("error.jsp").forward(req,resp);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    private void setOfferTypesToSession(HttpServletRequest req) throws DAOException {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        MySQLOfferDAO dao = new MySQLOfferDAO(con);
        req.setAttribute("offerTypes", dao.readAllOfferTypes());
        req.setAttribute("hotelTypes", dao.readAllHotelTypes());
        DBManager.closeConnection(con);
    }
}
