package com.travel_agency.filters;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.OrderDTO;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.OrderService;
import com.travel_agency.models.services.UserService;
import com.travel_agency.pagination.PaginationConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class UserCabinetFillingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getSession().getAttribute("user") != null){
            UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
            processFiling(req,userDTO);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private void processFiling(HttpServletRequest req,UserDTO userDTO) {
        String role = userDTO.getRole();
        switch(role){
            case("user") -> fillUserCabinet(req,userDTO);
            case("manager") -> fillManagerCabinet(req);
            case("admin") -> fillAdminCabinet(req);
        }
    }

    private void fillUserCabinet(HttpServletRequest req,UserDTO userDTO) {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OrderDAO orderDAO = new OrderDAO(con);
        OrderService service = new OrderService(orderDAO);
        List<OrderDTO> orders = service.getAllOrdersFromUser(userDTO);
        req.setAttribute("userOrders", orders);
        req.setAttribute("totalPrice", service.getTotalPrice(orders));
        DBManager.closeConnection(con);
    }

    private void fillManagerCabinet(HttpServletRequest req) {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OrderDAO orderDAO = new OrderDAO(con);
        OrderService service = new OrderService(orderDAO);
        List<OrderDTO> orders = service.getAllOrders(0,PaginationConstants.ORDER_LIST_RECORDS_PER_PAGE);
        req.setAttribute("numberOfPagesInOrders",orderDAO.getNumberOfPages());
        req.setAttribute("currentOrderPage", 1);
        req.setAttribute("orders", orders);
        DBManager.closeConnection(con);
    }

    private void fillAdminCabinet(HttpServletRequest req) {
        fillManagerCabinet(req);
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        UserDAO userDAO = new UserDAO(con);
        UserService service = new UserService(userDAO);
        List<UserDTO> users = service.getAllUsers(0, PaginationConstants.USER_LIST_RECORDS_PER_PAGE);
        users.remove(req.getSession().getAttribute("user"));
        req.setAttribute("numberOfPagesInUserList", userDAO.getNumberOfPages());
        req.setAttribute("currentPage", 1);
        req.setAttribute("allUsers", users);
        DBManager.closeConnection(con);
    }


}
