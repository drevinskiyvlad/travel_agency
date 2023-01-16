package com.travel_agency.filters;

import com.travel_agency.DB.DAO.OrderDAO;
import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.OrderDTO;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.OrderService;
import com.travel_agency.models.services.UserService;
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
        req.getSession().setAttribute("userOrders", orders);
        req.getSession().setAttribute("totalPrice", service.getTotalPrice(orders));
        DBManager.closeConnection(con);
    }

    private void fillManagerCabinet(HttpServletRequest req) {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OrderDAO orderDAO = new OrderDAO(con);
        OrderService service = new OrderService(orderDAO);
        List<OrderDTO> orders = service.getAllOrders();
        req.getSession().setAttribute("orders", orders);
        req.getSession().setAttribute("totalPrice", service.getTotalPrice(orders));
        DBManager.closeConnection(con);
    }
    private void fillAdminCabinet(HttpServletRequest req) {
        fillManagerCabinet(req);
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        UserDAO userDAO = new UserDAO(con);
        UserService service = new UserService(userDAO);
        List<UserDTO> users = service.getAllUsers();
        users.remove(req.getSession().getAttribute("user"));
        req.getSession().setAttribute("allUsers", users);
        DBManager.closeConnection(con);
    }


}
