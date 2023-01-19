package com.travel_agency.model.DB.DAO;

import com.travel_agency.exceptions.DAOException;

import java.util.List;

public interface UserDAO<E, K> {
    int getNumberOfPages();
    boolean create(E entity) throws DAOException;
    E read(K key) throws DAOException;
    E read(int id) throws DAOException;
    boolean update(E entity, String newRole) throws DAOException;
    boolean update(K key, boolean value) throws DAOException;
    boolean delete(K key) throws DAOException;
    List<E> readAll(int offset, int numOfRecords) throws DAOException;
    List<String> readAllUserRoles() throws DAOException;
}
