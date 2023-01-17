package com.travel_agency.filters;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.services.OfferService;
import com.travel_agency.models.DTO.OfferDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Connection;

public class OfferItemFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        OfferDTO offerDTO = getOfferDTO(req);
        req.setAttribute("offerItem", offerDTO);

        filterChain.doFilter(servletRequest,servletResponse);
    }

    private static OfferDTO getOfferDTO(HttpServletRequest req) {

        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OfferService service = getOfferService(con);

        String code = req.getParameter("code");
        OfferDTO offerDTO = service.getOffer(code);

        DBManager.closeConnection(con);
        return offerDTO;
    }

    private static OfferService getOfferService(Connection con) {
        OfferDAO dao = new OfferDAO(con);
        return new OfferService(dao);
    }
}
