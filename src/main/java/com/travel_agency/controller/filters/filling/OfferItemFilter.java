package com.travel_agency.controller.filters.filling;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.model.DTO.OfferDTO;
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

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static OfferDTO getOfferDTO(HttpServletRequest req) {

        Connection con = DBManager.getInstance().getConnection();
        OfferService service = getOfferService(con);

        String code = req.getParameter("code");
        OfferDTO offerDTO = service.getOffer(code);

        DBManager.closeConnection(con);
        return offerDTO;
    }

    private static OfferService getOfferService(Connection con) {
        MySQLOfferDAO dao = new MySQLOfferDAO(con);
        return new OfferService(dao);
    }
}
