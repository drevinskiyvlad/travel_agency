package com.travel_agency.models.services;

import com.google.protobuf.ServiceException;
import com.travel_agency.DB.DAO.*;
import com.travel_agency.DB.DBManager;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.models.DAO.*;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.models.DTO.OrderDTO;
import com.travel_agency.models.DTO.UserDTO;
import com.travel_agency.utils.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderService {
    private final OrderDAO dao;
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public OrderService(OrderDAO dao) {
        this.dao = dao;
    }

    public boolean makeOrder(OfferDTO offerDTO, UserDTO userDTO) {
        try {
            OrderDTO orderDTO = initializeOrderDTO(offerDTO, userDTO);
            Order order = convertDTOToOrder(orderDTO, userDTO, offerDTO);
            boolean result = dao.create(order);
            if (result) {
                result = decreaseOfferVacancy(offerDTO);
                logger.info("User {} successfully made order with code: {}", userDTO.getEmail(), orderDTO.getCode());
            }

            return result;
        } catch (ServiceException | DAOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders;
        try {
            orders = dao.readAll();
        } catch (DAOException e) {
            logger.error("Unable to read orders: " + e.getMessage(), e);
            return new ArrayList<>();
        }
        return makeListOfDTOs(orders);
    }

    public List<OrderDTO> getAllOrdersFromUser(UserDTO userDTO) {
        List<Order> orders;
        try {
            orders = dao.readAll(userDTO.getEmail());
        } catch (DAOException e) {
            logger.error("Unable to read orders: " + e.getMessage(), e);
            return new ArrayList<>();
        }
        return makeListOfDTOs(orders);
    }

    public double getTotalPrice(List<OrderDTO> orders){
        double result = 0;
        for(OrderDTO o: orders){
            result += o.getPrice();
        }
        return result;
    }

    private List<OrderDTO> makeListOfDTOs(List<Order> orders) {
        List<OrderDTO> result = new CopyOnWriteArrayList<>();
        for (Order o : orders) {
            result.add(convertOrderToDTO(o));
        }
        return result;
    }

    private OrderDTO convertOrderToDTO(Order order) {
        String code = order.getCode();
        String status = order.getOrderStatus();
        String userEmail = order.getUser().getEmail();
        String offerCode = order.getOffer().getCode();
        double price = order.getOffer().getPrice();
        return new OrderDTO(code, offerCode, userEmail, status,price);
    }

    private boolean decreaseOfferVacancy(OfferDTO offerDTO) throws DAOException {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();

        OfferDAO offerDAO = new OfferDAO(con);
        Offer offer = offerDAO.read(offerDTO.getCode());

        boolean hotelResult = decreaseHotelVacancy(con, offer);
        boolean tcResult = decreaseTCVacancy(con, offer);

        boolean offerResult = offerDAO.update(offer, offer.getVacancy() - 1);
        DBManager.closeConnection(con);

        return offerResult && hotelResult && tcResult;
    }

    private static boolean decreaseTCVacancy(Connection con, Offer offer) {
        TransportCompanyDAO tcDAO = new TransportCompanyDAO(con);
        TransportCompany tc = tcDAO.read(offer.getTransportCompany().getName());
        return tcDAO.update(tc, tc.getVacancy() - 1);
    }

    private static boolean decreaseHotelVacancy(Connection con, Offer offer) {
        HotelDAO hotelDAO = new HotelDAO(con);
        Hotel hotel = hotelDAO.read(offer.getHotel().getName());
        return hotelDAO.update(hotel, hotel.getVacancy() - 1);
    }

    private Order convertDTOToOrder(OrderDTO orderDTO, UserDTO userDTO, OfferDTO offerDTO) throws ServiceException {
        try {
            String code = orderDTO.getCode();
            User user = getUser(userDTO);
            Offer offer = getOffer(offerDTO);
            String status = orderDTO.getOrderStatus();
            return new Order(0, code, user, offer, status);
        } catch (DAOException e) {
            logger.error("Unable create order: " + e.getMessage(), e);
            throw new ServiceException("Unable create order: " + e.getMessage());
        }
    }

    private Offer getOffer(OfferDTO offerDTO) throws DAOException {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        OfferDAO offerDAO = new OfferDAO(con);
        Offer offer = offerDAO.read(offerDTO.getCode());
        DBManager.closeConnection(con);
        return offer;
    }

    private User getUser(UserDTO userDTO) throws DAOException {
        DBManager manager = DBManager.getInstance();
        Connection con = manager.getConnection();
        UserDAO userDAO = new UserDAO(con);
        User user = userDAO.read(userDTO.getEmail());
        DBManager.closeConnection(con);
        return user;
    }

    private OrderDTO initializeOrderDTO(OfferDTO offerDTO, UserDTO userDTO) {
        String code = RandomStringGenerator.getString(8);
        String status = "registered";
        String userEmail = userDTO.getEmail();
        String offerCode = offerDTO.getCode();
        double price = offerDTO.getPrice();
        return new OrderDTO(code, offerCode, userEmail, status,price);
    }
}
