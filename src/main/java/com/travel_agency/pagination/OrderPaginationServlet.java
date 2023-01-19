package com.travel_agency.pagination;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.OrderDTO;
import com.travel_agency.models.services.OrderService;
import com.travel_agency.utils.Constants.PaginationConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/orderPagination")
public class OrderPaginationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = PaginationConstants.ORDER_LIST_RECORDS_PER_PAGE;
        if (req.getParameter("orderListPage") != null)
            page = Integer.parseInt(req.getParameter("orderListPage"));
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OrderDAO dao = new OrderDAO(con);
        OrderService service = new OrderService(dao);
        List<OrderDTO> users = service.getAllOrders(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        req.setAttribute("orders", users);
        req.setAttribute("numberOfPagesInOrders", dao.getNumberOfPages());
        req.setAttribute("currentOrderPage", page);

        req.getRequestDispatcher("user-cabinet.jsp").forward(req, resp);
    }
}
