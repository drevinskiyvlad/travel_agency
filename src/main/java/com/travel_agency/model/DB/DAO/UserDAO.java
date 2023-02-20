package com.travel_agency.model.DB.DAO;

import com.travel_agency.utils.exceptions.DAOException;

import java.util.List;

/**
 * DAO interface for CRUD operations for user
 * @param <E> entity
 */
public interface UserDAO<E> {
    /**
     * @return number of pages for pagination
     */
    int getNumberOfPages();
    /**
     * Added user to database
     * @param entity that will be added to database
     * @return result of creating
     */
    boolean create(E entity) throws DAOException;
    /**
     * Get user by email from database
     * @param email key of user
     */
    E read(String email) throws DAOException;
    /**
     * Get user by id from database
     * @param id key of user
     */
    E read(int id) throws DAOException;
    /**
     * Change user role
     * @return result of updating
     */
    boolean update(E entity, String newRole) throws DAOException;
    /**
     * Change user blocked status
     * @return result of updating
     */
    boolean update(String email, boolean value) throws DAOException;
    /**
     * Delete user from database
     * @param email key of user
     * @return result of deleting
     */
    boolean delete(String email) throws DAOException;
    /**
     * Get all users from database with defined parameters
     * @param offset index of first user from database
     * @param numOfRecords number of records that given from database
     * @return List of user
     */
    List<E> readAll(int offset, int numOfRecords) throws DAOException;
    /**
     * @return List of all user roles
     */
    List<String> readAllUserRoles() throws DAOException;
}
