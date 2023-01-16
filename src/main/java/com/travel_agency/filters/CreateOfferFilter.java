package com.travel_agency.filters;

import com.travel_agency.DB.DAO.HotelDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DAO.Hotel;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CreateOfferFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();

        addHotelNamesToReqest(req, con);


        DBManager.closeConnection(con);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void addHotelNamesToReqest(HttpServletRequest req, Connection con) {
        HotelDAO hotelDAO = new HotelDAO(con);
        List<Hotel> hotels = hotelDAO.readAll();
        List<String> hotelNames = new ArrayList<>();
        for(Hotel h: hotels){
            hotelNames.add(h.getName());
        }
        req.getSession().setAttribute("hotelNames", hotelNames);
    }

    private void addTransportCompanyNamesToReqest(HttpServletRequest req, Connection con) {
        HotelDAO hotelDAO = new HotelDAO(con);
        List<Hotel> hotels = hotelDAO.readAll();
        List<String> hotelNames = new ArrayList<>();
        for(Hotel h: hotels){
            hotelNames.add(h.getName());
        }
        req.getSession().setAttribute("hotelNames", hotelNames);
    }
}
