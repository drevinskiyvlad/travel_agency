package com.travel_agency.controller.comands.pagination;

import com.travel_agency.controller.Command;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOrderDAO;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLUserDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.DTO.OrderDTO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.OrderService;
import com.travel_agency.model.services.UserService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class UserOrdersPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page = 1;
        int recordsPerPage = PaginationConstants.ORDER_LIST_RECORDS_PER_PAGE;
        if (req.getParameter("orderListPage") != null)
            page = Integer.parseInt(req.getParameter("orderListPage"));

        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        Connection con = DBManager.getInstance().getConnection();
        MySQLOrderDAO dao = new MySQLOrderDAO(con);
        OrderService service = new OrderService(dao);

        List<OrderDTO> users = service.getAllOrdersFromUser(
                user,
                (page - 1) * recordsPerPage,
                recordsPerPage);

        req.setAttribute("userOrders", users);
        req.setAttribute("numberOfPagesInUserOrders", dao.getNumberOfPages());
        req.setAttribute("currentUserOrderPage", page);

        DBManager.closeConnection(con);

        return PathConstants.USER_CABINET;
    }
}