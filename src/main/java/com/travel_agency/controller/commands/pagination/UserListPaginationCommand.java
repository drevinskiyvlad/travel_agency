package com.travel_agency.controller.commands.pagination;

import com.travel_agency.appContext.AppContext;
import com.travel_agency.controller.commands.Command;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.services.UserService;
import com.travel_agency.utils.Constants.PaginationConstants;
import com.travel_agency.utils.Constants.PathConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserListPaginationCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //init variables
        int recordsPerPage = PaginationConstants.USER_LIST_RECORDS_PER_PAGE;
        int page = getPage(req);

        UserService service = AppContext.getInstance().getUserService();

        List<UserDTO> users = service.getAllUsers(
                (page - 1) * recordsPerPage,
                recordsPerPage);

        setAttributesToReq(req, page, service, users);


        return PathConstants.USER_CABINET;
    }

    private static void setAttributesToReq(HttpServletRequest req, int page, UserService service, List<UserDTO> users) {
        req.setAttribute("allUsers", users);
        req.setAttribute("numberOfPagesInUserList", service.getNumberOfPages());
        req.setAttribute("currentPage", page);
    }

    private static int getPage(HttpServletRequest req) {
        int page = 1;
        if (req.getParameter("userListPage") != null)
            page = Integer.parseInt(req.getParameter("userListPage"));
        return page;
    }
}
