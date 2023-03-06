package com.travel_agency.appContext;

import com.travel_agency.model.DB.DAO.OfferDAO;
import com.travel_agency.model.DB.DAO.OrderDAO;
import com.travel_agency.model.DB.DAO.UserDAO;
import com.travel_agency.model.DB.DAO.impl.OfferDAOImpl;
import com.travel_agency.model.DB.DAO.impl.OrderDAOImpl;
import com.travel_agency.model.DB.DAO.impl.UserDAOImpl;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.model.entity.Order;
import com.travel_agency.model.entity.User;
import com.travel_agency.model.services.OfferService;
import com.travel_agency.model.services.OrderService;
import com.travel_agency.model.services.UserService;
import lombok.Getter;

public class AppContext {

    private static final AppContext INSTANCE = new AppContext();

    //DAOs
    private final OfferDAO<Offer> offerDAO = new OfferDAOImpl();
    private final OrderDAO<Order> orderDAO = new OrderDAOImpl();
    private final UserDAO<User> userDAO = new UserDAOImpl();

    //Services
    @Getter
    private final OfferService offerService = new OfferService(offerDAO);
    @Getter
    private final OrderService orderService = new OrderService(orderDAO);
    @Getter
    private final UserService userService = new UserService(userDAO);

    private AppContext() {
    }

    public static synchronized AppContext getInstance() {
        return INSTANCE;
    }
}
