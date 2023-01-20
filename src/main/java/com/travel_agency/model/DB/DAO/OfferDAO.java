package com.travel_agency.model.DB.DAO;

import com.travel_agency.exceptions.DAOException;

import java.util.List;

public interface OfferDAO<E, K> {
    int getNumberOfPages();
    boolean create(E entity) throws DAOException;
    E read(K key) throws DAOException;
    E read(int id) throws DAOException;
    boolean update(E entity, boolean isHot) throws DAOException;
    boolean update(E entity, int newValue) throws DAOException;
    boolean delete(K key) throws DAOException;
    List<E> readAll(int offset, int numOfRecords, boolean onlyHot) throws DAOException;
    List<String> readAllHotelTypes() throws DAOException;
    List<String> readAllOfferTypes() throws DAOException;

}
