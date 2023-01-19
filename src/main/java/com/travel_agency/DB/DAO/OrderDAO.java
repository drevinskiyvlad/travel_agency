package com.travel_agency.DB.DAO;

import com.travel_agency.exceptions.DAOException;

import java.util.List;

public interface OrderDAO<E, K> {
    int getNumberOfPages();
    int getNumberOfUserPages();
    boolean create(E entity) throws DAOException;
    E read(K key) throws DAOException;
    boolean update(E entity, String newStatus) throws DAOException;
    boolean delete(K key) throws DAOException;
    List<E> readAll(int offset, int numOfRecords) throws DAOException;
    List<E> readAll(String email, int offset, int numOfRecords) throws DAOException;
    List<String> readAllOrderStatuses() throws DAOException;
}
