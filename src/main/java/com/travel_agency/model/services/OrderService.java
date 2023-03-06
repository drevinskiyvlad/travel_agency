package com.travel_agency.model.services;

import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.model.DB.DAO.OrderDAO;
import com.travel_agency.model.DB.DAO.impl.OfferDAOImpl;
import com.travel_agency.model.DB.DAO.impl.UserDAOImpl;
import com.travel_agency.model.DB.DBManager;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.DTO.OrderDTO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.model.entity.Order;
import com.travel_agency.model.entity.User;
import com.travel_agency.utils.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OrderService {
    private final OrderDAO<Order> dao;
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    /**
     * Constructor
     */
    public OrderService(OrderDAO<Order> dao) {
        this.dao = dao;
    }

    /**
     * Creates order
     * @return result of creating
     */
    public boolean makeOrder(OfferDTO offerDTO, UserDTO userDTO) {
        try {
            OrderDTO orderDTO = initializeOrderDTO(offerDTO, userDTO);
            Order order = convertDTOToOrder(orderDTO, userDTO, offerDTO);
            boolean result = dao.create(order);
            if (result) {
                result = decreaseOfferPlaces(offerDTO);
                logger.info("User {} successfully made order with code: {}", userDTO.getEmail(), orderDTO.getCode());
            }

            return result;
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
    /**
     * Get all orders from database with defined parameters
     * @param offset index of first order from database
     * @param numOfRecords number of records that given from database
     * @return List of order
     */
    public List<OrderDTO> getAllOrders(int offset, int numOfRecords) {
        List<Order> orders;
        try {
            orders = dao.readAll(offset,numOfRecords);
        } catch (DAOException e) {
            logger.error("Unable to read orders: " + e.getMessage(), e);
            return new ArrayList<>();
        }
        return makeListOfDTOs(orders);
    }
    /**
     * Get all orders of user from database with defined parameters
     * @param offset index of first offer from database
     * @param numOfRecords number of records that given from database
     * @return List of order from user
     */
    public List<OrderDTO> getAllOrdersFromUser(UserDTO userDTO, int offset, int numOfRecords) {
        List<Order> orders;
        try {
            orders = dao.readAll(userDTO.getEmail(), offset, numOfRecords);
        } catch (DAOException e) {
            logger.error("Unable to read orders: " + e.getMessage(), e);
            return new ArrayList<>();
        }
        return makeListOfDTOs(orders);
    }

    /**
     * @return total price of all orders
     */
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

    private boolean decreaseOfferPlaces(OfferDTO offerDTO) throws DAOException {
        Connection con = DBManager.getInstance().getConnection();

        com.travel_agency.model.DB.DAO.OfferDAO<Offer> offerDAO = new OfferDAOImpl(con);
        Offer offer = offerDAO.read(offerDTO.getCode());

        if(offer.getPlaces() <= 1)
            offerDAO.updateActive(offer, false);

        boolean offerResult = offerDAO.update(offer, offer.getPlaces() - 1);
        DBManager.closeConnection(con);

        return offerResult;
    }

    private Order convertDTOToOrder(OrderDTO orderDTO, UserDTO userDTO, OfferDTO offerDTO) {
        try {
            String code = orderDTO.getCode();
            User user = getUser(userDTO);
            Offer offer = getOffer(offerDTO);
            String status = orderDTO.getOrderStatus();
            return new Order(0, code, user, offer, status);
        } catch (DAOException e) {
            logger.error("Unable to convert orderDTO to order", e);
        }
        return null;
    }

    private Offer getOffer(OfferDTO offerDTO) throws DAOException {
        Connection con = DBManager.getInstance().getConnection();
        OfferDAOImpl offerDAO = new OfferDAOImpl(con);
        Offer offer = offerDAO.read(offerDTO.getCode());
        DBManager.closeConnection(con);
        return offer;
    }

    private User getUser(UserDTO userDTO) throws DAOException {
        Connection con = DBManager.getInstance().getConnection();
        UserDAOImpl userDAO = new UserDAOImpl(con);
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
