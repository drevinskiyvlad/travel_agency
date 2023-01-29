package com.travel_agency.model.DB.DAO;

import com.travel_agency.exceptions.DAOException;

import java.util.List;

/**
 * DAO interface for CRUD operations for order
 * @param <E> entity
 */
public interface OrderDAO<E> {
    /**
     * @return number of pages for pagination
     */
    int getNumberOfPages();
    /**
     * @return number of pages of user orders for pagination
     */
    int getNumberOfUserPages();
    /**
     * Added order to database
     * @param entity that will be added to database
     * @return result of creating
     */
    boolean create(E entity) throws DAOException;
    /**
     * Get order from database
     * @param code key of order
     */
    E read(String code) throws DAOException;
    /**
     * Change order status
     */
    boolean update(E entity, String newStatus) throws DAOException;
    /**
     * Delete offer from database
     * @param code primary key of offer
     * @return result of deleting
     */
    boolean delete(String code) throws DAOException;
    /**
     * Get all orders from database with defined parameters
     * @param offset index of first order from database
     * @param numOfRecords number of records that given from database
     * @return List of order
     */
    List<E> readAll(int offset, int numOfRecords) throws DAOException;
    /**
     * Get all orders of user from database with defined parameters
     * @param offset index of first offer from database
     * @param numOfRecords number of records that given from database
     * @return List of order from user
     */
    List<E> readAll(String email, int offset, int numOfRecords) throws DAOException;

    /**
     * @return List of all order statuses
     */
    List<String> readAllOrderStatuses() throws DAOException;
}
