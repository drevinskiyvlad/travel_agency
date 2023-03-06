package com.travel_agency.controller.filters.filling;

import com.travel_agency.model.DB.DAO.impl.OfferDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.SORTING_BY;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Connection;

public class OfferListFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        addOffersToRequest(req);

        chain.doFilter(servletRequest, servletResponse);
    }

    private static void addOffersToRequest(HttpServletRequest req) {
        Connection con = DBManager.getInstance().getConnection();
        OfferDAOImpl dao = new OfferDAOImpl(con);
        OfferService service = new OfferService(dao);

        SORTING_BY sortBy = getSortBy(req);

        setAttributesToRequest(req, dao, service, sortBy);

        DBManager.closeConnection(con);
    }

    private static void setAttributesToRequest(HttpServletRequest req, OfferDAOImpl dao, OfferService service, SORTING_BY sortBy) {
        req.getSession().setAttribute("sortBy", sortBy);

        req.setAttribute("offers",
                service.getAllOffers(0,
                        PaginationConstants.OFFERS_RECORDS_PER_PAGE,
                        false,
                        sortBy));

        req.setAttribute("numberOfPagesInOffers", dao.getNumberOfPages());

        req.setAttribute("hotOffers",
                service.getAllOffers(0,
                        PaginationConstants.OFFERS_RECORDS_PER_PAGE,
                        true,
                        sortBy));

        req.setAttribute("numberOfPagesInHotOffers", dao.getNumberOfPages());
        req.setAttribute("currentPage", 1);
    }

    private static SORTING_BY getSortBy(HttpServletRequest req) {
        String sortingName = req.getParameter("sortBy");
        for (SORTING_BY s : SORTING_BY.values()) {
            if (s.getName().equals(sortingName))
                return s;
        }
        return SORTING_BY.NONE;
    }
}
