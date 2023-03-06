package com.travel_agency.controller.filters.filling;

import com.travel_agency.model.DB.DAO.impl.OrderDAOImpl;
import com.travel_agency.model.DB.DAO.impl.UserDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.model.DTO.OrderDTO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.OrderService;
import com.travel_agency.model.services.UserService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class UserCabinetFillingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(UserCabinetFillingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getSession().getAttribute("user") != null) {
            try {
                UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
                processFiling(req, userDTO);
            } catch (Exception e) {
                logger.error("Error while set parameters to user cabinet: " + e.getMessage(), e);
                req.getRequestDispatcher(PathConstants.ERROR);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void processFiling(HttpServletRequest req, UserDTO userDTO) throws DAOException {
        String role = userDTO.getRole();
        switch (role) {
            case ("user") -> fillUserCabinet(req, userDTO);
            case ("manager") -> fillManagerCabinet(req);
            case ("admin") -> fillAdminCabinet(req);
            default -> throw new IllegalArgumentException("Unknown user role");
        }
    }

    private void fillUserCabinet(HttpServletRequest req, UserDTO userDTO) {
        Connection con = DBManager.getInstance().getConnection();
        OrderDAOImpl orderDAO = new OrderDAOImpl(con);
        OrderService service = new OrderService(orderDAO);

        List<OrderDTO> orders = service.getAllOrdersFromUser(userDTO, 0,
                PaginationConstants.USER_LIST_RECORDS_PER_PAGE);

        setAttrToUserCabinet(req, orderDAO, service, orders);

        DBManager.closeConnection(con);
    }

    private static void setAttrToUserCabinet(HttpServletRequest req, OrderDAOImpl orderDAO,
                                             OrderService service, List<OrderDTO> orders) {
        req.setAttribute("numberOfPagesInUserOrders", orderDAO.getNumberOfUserPages());
        req.setAttribute("currentUserOrderPage", 1);
        req.setAttribute("userOrders", orders);
        req.setAttribute("totalPrice", service.getTotalPrice(orders));
    }

    private void fillManagerCabinet(HttpServletRequest req) throws DAOException {
        Connection con = DBManager.getInstance().getConnection();
        OrderDAOImpl orderDAO = new OrderDAOImpl(con);
        OrderService service = new OrderService(orderDAO);

        List<OrderDTO> orders = service.getAllOrders(0,
                PaginationConstants.ORDER_LIST_RECORDS_PER_PAGE);

        setAttrToManagerCabinet(req, orderDAO, orders);

        DBManager.closeConnection(con);
    }

    private static void setAttrToManagerCabinet(HttpServletRequest req, OrderDAOImpl orderDAO,
                                                List<OrderDTO> orders) throws DAOException {
        req.setAttribute("orderStatuses", orderDAO.readAllOrderStatuses());
        req.setAttribute("numberOfPagesInOrders", orderDAO.getNumberOfPages());
        req.setAttribute("currentOrderPage", 1);
        req.setAttribute("orders", orders);
    }

    private void fillAdminCabinet(HttpServletRequest req) throws DAOException {
        fillManagerCabinet(req);

        Connection con = DBManager.getInstance().getConnection();
        UserDAOImpl userDAO = new UserDAOImpl(con);
        UserService service = new UserService(userDAO);

        List<UserDTO> users = service.getAllUsers(0,
                PaginationConstants.USER_LIST_RECORDS_PER_PAGE);
        //noinspection SuspiciousMethodCalls
        users.remove(req.getSession().getAttribute("user"));

        setAttrToAdminCabinet(req, userDAO, users);

        DBManager.closeConnection(con);
    }

    private static void setAttrToAdminCabinet(HttpServletRequest req, UserDAOImpl userDAO,
                                              List<UserDTO> users) throws DAOException {

        req.setAttribute("userRoles", userDAO.readAllUserRoles());
        req.setAttribute("numberOfPagesInUserList", userDAO.getNumberOfPages());
        req.setAttribute("currentPage", 1);
        req.setAttribute("allUsers", users);

    }
}
