package com.travel_agency.controller.commands.pagination;

import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DB.DAO.impl.OrderDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.DTO.OrderDTO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.OrderService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class UserOrdersPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //init variables
        int recordsPerPage = PaginationConstants.ORDER_LIST_RECORDS_PER_PAGE;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        int page = getPage(req);

        //init service
        Connection con = DBManager.getInstance().getConnection();
        OrderDAOImpl dao = new OrderDAOImpl(con);
        OrderService service = new OrderService(dao);

        List<OrderDTO> users = service.getAllOrdersFromUser(user,
                (page - 1) * recordsPerPage,
                recordsPerPage);

        setAttributesToReq(req, page, dao, users);

        DBManager.closeConnection(con);

        return PathConstants.USER_CABINET;
    }

    private static void setAttributesToReq(HttpServletRequest req, int page, OrderDAOImpl dao, List<OrderDTO> users) {
        req.setAttribute("userOrders", users);
        req.setAttribute("numberOfPagesInUserOrders", dao.getNumberOfPages());
        req.setAttribute("currentUserOrderPage", page);
    }

    private static int getPage(HttpServletRequest req) {
        int page = 1;
        if (req.getParameter("orderListPage") != null)
            page = Integer.parseInt(req.getParameter("orderListPage"));
        return page;
    }
}
