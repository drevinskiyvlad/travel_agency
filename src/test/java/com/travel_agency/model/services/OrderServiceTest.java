package com.travel_agency.model.services;

import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.model.DB.DAO.OrderDAO;
import com.travel_agency.model.DTO.OrderDTO;
import com.travel_agency.model.DTO.UserDTO;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.model.entity.Order;
import com.travel_agency.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderDAO<Order> dao;
    @InjectMocks
    private OrderService service;

    private UserDTO userDTO;
    private Order order;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("user@email.com", "role", "John", "Doe", "+123456789012",false);
        order = new Order(0, "code", new User(), new Offer(), "status");
    }

    @Test
    void testGetAllOrders() throws DAOException {
        // Given
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(dao.readAll(0, 10)).thenReturn(orders);

        // When
        List<OrderDTO> result = service.getAllOrders(0, 10);

        // Then
        assertEquals(1, result.size());
        assertEquals("code", result.get(0).getCode());
        verify(dao).readAll(0, 10);
    }

    @Test
    void testGetAllOrders_DAOException() throws DAOException {
        when(dao.readAll(0, 10)).thenThrow(DAOException.class);

        List<OrderDTO> result = service.getAllOrders(0, 10);

        verify(dao).readAll(0, 10);
        assertEquals(0, result.size());
    }

    @Test
    void testGetAllOrdersFromUser() throws DAOException {
        // Given
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(dao.readAll("user@email.com", 0, 10)).thenReturn(orders);

        // When
        List<OrderDTO> result = service.getAllOrdersFromUser(userDTO, 0, 10);

        // Then
        assertEquals(1, result.size());
        assertEquals("code", result.get(0).getCode());
        verify(dao).readAll("user@email.com", 0, 10);
    }

    @Test
    void testGetAllOrdersFromUser_DAOException() throws DAOException {
        when(dao.readAll("user@email.com",0, 10)).thenThrow(DAOException.class);

        List<OrderDTO> result = service.getAllOrdersFromUser(userDTO,0, 10);

        verify(dao).readAll("user@email.com",0, 10);
        assertEquals(0, result.size());
    }

    @Test
    void testGetTotalPrice() {
        // Given
        List<OrderDTO> orders = new ArrayList<>();
        orders.add(new OrderDTO("code1", "offerCode1", "userEmail1", "status1", 100.0));
        orders.add(new OrderDTO("code2", "offerCode2", "userEmail1", "status2", 200.0));

        // When
        double result = service.getTotalPrice(orders);

        // Then
        assertEquals(300.0, result);
    }
}