package com.travel_agency.pagination;

import com.travel_agency.DB.DAO.DAO;
import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.OfferService;
import com.travel_agency.models.services.UserService;
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
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OfferDAO dao = new OfferDAO(con);
        OfferService service = new OfferService(dao);
        List<OfferDTO> offers = service.getAllOffers(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        req.setAttribute("offers", offers);
        req.setAttribute("numberOfPagesInOffers", dao.getNumberOfPages());
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("our-offer.jsp").forward(req, resp);
    }
}
