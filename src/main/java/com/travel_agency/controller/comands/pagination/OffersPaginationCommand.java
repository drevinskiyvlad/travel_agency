package com.travel_agency.controller.comands.pagination;

import com.travel_agency.controller.Command;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.utils.Constants.SORTING_BY;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class OffersPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SORTING_BY sortBy = (SORTING_BY) req.getSession().getAttribute("sortBy");
        int recordsPerPage = PaginationConstants.OFFERS_RECORDS_PER_PAGE;
        int page = getPage(req);

        Connection con = DBManager.getInstance().getConnection();
        MySQLOfferDAO dao = new MySQLOfferDAO(con);
        OfferService service = new OfferService(dao);

        dao.setPage(page);
        List<OfferDTO> offers = service.getAllOffers(
                (page - 1) * recordsPerPage,
                recordsPerPage,
                false,
                sortBy);


        setAttributesToReq(req, page, dao, offers);

        DBManager.closeConnection(con);

        return PathConstants.OUR_OFFER;
    }

    private static int getPage(HttpServletRequest req) {
        int page = 1;
        if (req.getParameter("offerListPage") != null)
            page = Integer.parseInt(req.getParameter("offerListPage"));
        return page;
    }

    private static void setAttributesToReq(HttpServletRequest req, int page, MySQLOfferDAO dao, List<OfferDTO> offers) {
        req.setAttribute("offers", offers);
        req.setAttribute("numberOfPagesInOffers", dao.getNumberOfPages());
        req.setAttribute("currentPage", page);
    }
}
