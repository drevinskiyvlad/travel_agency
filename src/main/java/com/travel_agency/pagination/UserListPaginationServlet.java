package com.travel_agency.pagination;

import com.travel_agency.DB.DAO.UserDAO;
import com.travel_agency.DB.DBManager;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.models.services.UserService;
import com.travel_agency.utils.Constants.PaginationConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/userPagination")
public class UserListPaginationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = PaginationConstants.USER_LIST_RECORDS_PER_PAGE;
        if (req.getParameter("userListPage") != null)
            page = Integer.parseInt(req.getParameter("userListPage"));
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        UserDAO dao = new UserDAO(con);
        UserService service = new UserService(dao);
        List<UserDTO> users = service.getAllUsers(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        req.setAttribute("allUsers", users);
        req.setAttribute("numberOfPagesInUserList", dao.getNumberOfPages());
        req.setAttribute("currentPage", page);
        req.getRequestDispatcher("user-cabinet.jsp").forward(req, resp);
    }
}
