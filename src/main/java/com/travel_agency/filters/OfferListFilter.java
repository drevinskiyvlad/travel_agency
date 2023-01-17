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
        addOffersToRequest(req);
        chain.doFilter(servletRequest,servletResponse);
    }

    private static void addOffersToRequest(HttpServletRequest req) {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OfferDAO dao = new OfferDAO(con);
        OfferService service = new OfferService(dao);
        req.setAttribute("offers", service.getAllOffers());
        req.setAttribute("hot_offers", service.getAllHotOffers());
        DBManager.closeConnection(con);
    }
}
