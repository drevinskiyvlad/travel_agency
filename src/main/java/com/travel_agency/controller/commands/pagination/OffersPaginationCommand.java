package com.travel_agency.controller.commands.pagination;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import com.travel_agency.utils.Constants.SORTING_BY;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class OffersPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //init values
        SORTING_BY sortBy = (SORTING_BY) req.getSession().getAttribute("sortBy");
        int recordsPerPage = PaginationConstants.OFFERS_RECORDS_PER_PAGE;
        int page = getPage(req);

        //init service
        OfferService service = AppContext.getInstance().getOfferService();

        service.setPage(page);
        List<OfferDTO> offers = service.getAllOffers(
                (page - 1) * recordsPerPage,
                recordsPerPage,
                false,
                sortBy);


        setAttributesToReq(req, page, service, offers);

        return PathConstants.OUR_OFFER;
    }

    private static int getPage(HttpServletRequest req) {
        int page = 1;
        if (req.getParameter("offerListPage") != null)
            page = Integer.parseInt(req.getParameter("offerListPage"));
        return page;
    }

    private static void setAttributesToReq(HttpServletRequest req, int page, OfferService service, List<OfferDTO> offers) {
        req.setAttribute("offers", offers);
        req.setAttribute("numberOfPagesInOffers", service.getNumberOfPages());
        req.setAttribute("currentPage", page);
    }
}
