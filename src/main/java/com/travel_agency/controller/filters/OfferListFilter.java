package com.travel_agency.controller.filters;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.PaginationConstants;
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
        MySQLOfferDAO dao = new MySQLOfferDAO(con);
        OfferService service = new OfferService(dao);

        req.setAttribute("offers", service.getAllOffers(0, PaginationConstants.OFFERS_RECORDS_PER_PAGE, false));
        req.setAttribute("numberOfPagesInOffers",dao.getNumberOfPages());
        req.setAttribute("hotOffers", service.getAllOffers(0, PaginationConstants.OFFERS_RECORDS_PER_PAGE, true));
        req.setAttribute("numberOfPagesInHotOffers",dao.getNumberOfPages());
        req.setAttribute("currentPage", 1);

        DBManager.closeConnection(con);
    }
}
