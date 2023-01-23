package com.travel_agency.controller.comands.pagination;

import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLUserDAO;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.DTO.UserDTO;
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
        //init variables
        int recordsPerPage = PaginationConstants.USER_LIST_RECORDS_PER_PAGE;
        int page = getPage(req);

        //init service
        Connection con = DBManager.getInstance().getConnection();
        MySQLUserDAO dao = new MySQLUserDAO(con);
        UserService service = new UserService(dao);

        List<UserDTO> users = service.getAllUsers(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        setAttributesToReq(req, page, dao, users);

        DBManager.closeConnection(con);

        return PathConstants.USER_CABINET;
    }

    private static void setAttributesToReq(HttpServletRequest req, int page, MySQLUserDAO dao, List<UserDTO> users) {
        req.setAttribute("allUsers", users);
        req.setAttribute("numberOfPagesInUserList", dao.getNumberOfPages());
        req.setAttribute("currentPage", page);
    }

    private static int getPage(HttpServletRequest req) {
        int page = 1;
        if (req.getParameter("userListPage") != null)
            page = Integer.parseInt(req.getParameter("userListPage"));
        return page;
    }
}
