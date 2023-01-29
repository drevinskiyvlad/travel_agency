package com.travel_agency.model.DB.DAO;

import com.travel_agency.exceptions.DAOException;

import java.util.List;

public interface OrderDAO<E> {
    int getNumberOfPages();
    int getNumberOfUserPages();
    boolean create(E entity) throws DAOException;
    E read(String code) throws DAOException;
    boolean update(E entity, String newStatus) throws DAOException;
    boolean delete(String code) throws DAOException;
    List<E> readAll(int offset, int numOfRecords) throws DAOException;
    List<E> readAll(String email, int offset, int numOfRecords) throws DAOException;
    List<String> readAllOrderStatuses() throws DAOException;
}
