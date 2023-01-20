package com.travel_agency.controller.pagination;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.PaginationConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/offerPagination")
public class OffersPaginationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = PaginationConstants.OFFERS_RECORDS_PER_PAGE;
        if (req.getParameter("offerListPage") != null)
            page = Integer.parseInt(req.getParameter("offerListPage"));
        Connection con = DBManager.getInstance().getConnection();
        MySQLOfferDAO dao = new MySQLOfferDAO(con);
        OfferService service = new OfferService(dao);
        List<OfferDTO> offers = service.getAllOffers(
                (page - 1) * recordsPerPage,
                recordsPerPage,
                false);

        req.setAttribute("offers", offers);
        req.setAttribute("numberOfPagesInOffers", dao.getNumberOfPages());
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("our-offer.jsp").forward(req, resp);

        DBManager.closeConnection(con);
    }
}
