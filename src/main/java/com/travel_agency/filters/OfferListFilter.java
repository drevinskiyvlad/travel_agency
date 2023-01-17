package com.travel_agency.filters;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.services.OfferService;
import com.travel_agency.pagination.PaginationConstants;
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

        req.setAttribute("offers", service.getAllOffers(0, PaginationConstants.OFFERS_RECORDS_PER_PAGE));
        req.setAttribute("numberOfPagesInOffers",dao.getNumberOfPages());
        req.setAttribute("currentPage", 1);

        DBManager.closeConnection(con);
    }
}
