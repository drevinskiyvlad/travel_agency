package com.travel_agency.controller.filters.filling;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.services.OfferService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class OfferItemFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        OfferDTO offerDTO = getOfferDTO(req);
        req.setAttribute("offerItem", offerDTO);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static OfferDTO getOfferDTO(HttpServletRequest req) {
        OfferService service = AppContext.getInstance().getOfferService();
        String code = req.getParameter("code");
        return service.getOffer(code);
    }

}
