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

public class UserListPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page = 1;
        int recordsPerPage = PaginationConstants.USER_LIST_RECORDS_PER_PAGE;
        if (req.getParameter("userListPage") != null)
            page = Integer.parseInt(req.getParameter("userListPage"));
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        MySQLUserDAO dao = new MySQLUserDAO(con);
        UserService service = new UserService(dao);
        List<UserDTO> users = service.getAllUsers(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        req.setAttribute("allUsers", users);
        req.setAttribute("numberOfPagesInUserList", dao.getNumberOfPages());
        req.setAttribute("currentPage", page);

        DBManager.closeConnection(con);

        return PathConstants.USER_CABINET;
    }
}
