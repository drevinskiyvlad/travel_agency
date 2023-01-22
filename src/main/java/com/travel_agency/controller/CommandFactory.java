package com.travel_agency.controller;

import com.travel_agency.controller.comands.authorization.LogoutCommand;
import com.travel_agency.controller.comands.authorization.SignInCommand;
import com.travel_agency.controller.comands.authorization.SignUpCommand;
import com.travel_agency.controller.comands.our_offer.*;
import com.travel_agency.controller.comands.pagination.OffersPaginationCommand;
import com.travel_agency.controller.comands.pagination.OrdersPaginationCommand;
import com.travel_agency.controller.comands.pagination.UserListPaginationCommand;
import com.travel_agency.controller.comands.pagination.UserOrdersPaginationCommand;
import com.travel_agency.controller.comands.user_cabinet.BlockUserCommand;
import com.travel_agency.controller.comands.user_cabinet.DeleteOrderCommand;
import com.travel_agency.controller.comands.user_cabinet.UpdateOrderStatusCommand;
import com.travel_agency.controller.comands.user_cabinet.UpdateUserRoleCommand;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static CommandFactory factory = new CommandFactory();
    private static final Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {}

    /**
     * Singleton.
     */
    public static CommandFactory getInstance() {
        if (factory == null) {
            factory = new CommandFactory();
        }
        return factory;
    }

    static {
        //put commands here
        commands.put("signIn", new SignInCommand());
        commands.put("signUp", new SignUpCommand());
        commands.put("logout", new LogoutCommand());

        //User cabinet command
        commands.put("blockUser", new BlockUserCommand());
        commands.put("deleteOrder", new DeleteOrderCommand());
        commands.put("updateOrderStatus", new UpdateOrderStatusCommand());
        commands.put("updateUserRole", new UpdateUserRoleCommand());


        //Our offer
        commands.put("addOffer", new AddOfferCommand());
        commands.put("updateOffer", new UpdateOfferCommand());
        commands.put("makeOfferHot", new MakeOfferHotCommand());
        commands.put("deleteOffer", new DeleteOfferCommand());
        commands.put("makeOrder", new MakeOrderCommand());

        //pagination
        commands.put("offerPagination", new OffersPaginationCommand());
        commands.put("orderPagination", new OrdersPaginationCommand());
        commands.put("userPagination", new UserListPaginationCommand());
        commands.put("userOrdersPagination", new UserOrdersPaginationCommand());

    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        if(action == null)
            return null;
        return commands.get(action);
    }
}
