package com.travel_agency.model.DB.DAO;

import com.travel_agency.exceptions.DAOException;

import java.util.List;

public interface UserDAO<E> {
    int getNumberOfPages();
    boolean create(E entity) throws DAOException;
    E read(String email) throws DAOException;
    E read(int id) throws DAOException;
    boolean update(E entity, String newRole) throws DAOException;
    boolean update(String email, boolean value) throws DAOException;
    boolean delete(String email) throws DAOException;
    List<E> readAll(int offset, int numOfRecords) throws DAOException;
    List<String> readAllUserRoles() throws DAOException;
}
