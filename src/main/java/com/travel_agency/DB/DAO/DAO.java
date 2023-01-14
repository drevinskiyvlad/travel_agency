package com.travel_agency.DB.DAO;

import com.travel_agency.exceptions.DAOException;

import java.util.List;

public interface DAO<E, K> {
    boolean create(E entity) throws DAOException;
    E read(K key) throws DAOException;
    boolean update(E entity, K newValue) throws DAOException;
    boolean delete(E entity) throws DAOException;
    List<E> readAll() throws DAOException;
}
