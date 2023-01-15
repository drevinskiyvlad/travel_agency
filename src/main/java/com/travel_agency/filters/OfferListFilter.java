package com.travel_agency.filters;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.services.OfferService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Connection;

public class OfferListFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getAttribute("offers") == null) {
            addOffersToSession(req);
        }
        chain.doFilter(servletRequest,servletResponse);
    }

    private static void addOffersToSession(HttpServletRequest req) {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OfferDAO dao = new OfferDAO(con);
        OfferService service = new OfferService(dao);
        req.getSession().setAttribute("offers", service.getAllOffers());
        req.getSession().setAttribute("hot_offers", service.getAllHotOffers());
        manager.closeConnection(con);
    }
}
