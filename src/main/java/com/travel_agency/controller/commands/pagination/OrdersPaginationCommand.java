package com.travel_agency.controller.commands.pagination;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DTO.OrderDTO;
import com.travel_agency.model.services.OrderService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class OrdersPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int recordsPerPage = PaginationConstants.ORDER_LIST_RECORDS_PER_PAGE;
        int page = getPage(req);

        OrderService service = AppContext.getInstance().getOrderService();

        List<OrderDTO> users = service.getAllOrders(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        setAttributesToReq(req, page, service, users);

        return PathConstants.USER_CABINET;
    }

    private static void setAttributesToReq(HttpServletRequest req, int page, OrderService service, List<OrderDTO> users) {
        req.setAttribute("orders", users);
        req.setAttribute("numberOfPagesInOrders", service.getNumberOfPages());
        req.setAttribute("currentOrderPage", page);
    }

    private static int getPage(HttpServletRequest req) {
        int page = 1;
        if (req.getParameter("orderListPage") != null)
            page = Integer.parseInt(req.getParameter("orderListPage"));
        return page;
    }
}
