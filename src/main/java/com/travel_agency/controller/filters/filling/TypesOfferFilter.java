package com.travel_agency.controller.filters.filling;

import com.travel_agency.model.DB.DAO.impl.HotelDAOImpl;
import com.travel_agency.model.DB.DAO.impl.OfferDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.entity.Hotel;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.utils.exceptions.DAOException;
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
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setOfferTypesToSession(HttpServletRequest req) throws DAOException {
        Connection con = DBManager.getInstance().getConnection();
        com.travel_agency.model.DB.DAO.OfferDAO<Offer> offerDAO = new OfferDAOImpl(con);
        com.travel_agency.model.DB.DAO.HotelDAO<Hotel> hotelDAO = new HotelDAOImpl(con);

        req.setAttribute("offerTypes", offerDAO.readAllOfferTypes());
        req.setAttribute("hotelTypes", hotelDAO.readAllHotelTypes());

        DBManager.closeConnection(con);
    }
}
