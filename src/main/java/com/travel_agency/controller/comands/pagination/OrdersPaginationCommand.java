package com.travel_agency.controller.comands.pagination;

import com.travel_agency.controller.Command;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOrderDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.DTO.OrderDTO;
import com.travel_agency.model.services.OrderService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class OrdersPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int recordsPerPage = PaginationConstants.ORDER_LIST_RECORDS_PER_PAGE;
        int page = getPage(req);

        Connection con = DBManager.getInstance().getConnection();
        MySQLOrderDAO dao = new MySQLOrderDAO(con);
        OrderService service = new OrderService(dao);

        List<OrderDTO> users = service.getAllOrders(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        setAttributesToReq(req, page, dao, users);

        DBManager.closeConnection(con);

        return PathConstants.USER_CABINET;
    }

    private static void setAttributesToReq(HttpServletRequest req, int page, MySQLOrderDAO dao, List<OrderDTO> users) {
        req.setAttribute("orders", users);
        req.setAttribute("numberOfPagesInOrders", dao.getNumberOfPages());
        req.setAttribute("currentOrderPage", page);
    }

    private static int getPage(HttpServletRequest req) {
        int page = 1;
        if (req.getParameter("orderListPage") != null)
            page = Integer.parseInt(req.getParameter("orderListPage"));
        return page;
    }
}
