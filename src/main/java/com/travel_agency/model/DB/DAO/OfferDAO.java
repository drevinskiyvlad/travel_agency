package com.travel_agency.model.DB.DAO;

import com.travel_agency.model.entity.Entity;
import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.utils.Constants.SORTING_BY;

import java.util.List;

/**
 * DAO interface for CRUD operations for offer
 * @param <E> entity
 */
public interface OfferDAO<E extends Entity> {
    /**
     * @return number of pages for pagination
     */
    int getNumberOfPages();

    /**
     * set page for pagination
     * @param page page of pagination
     */
    void setPage(int page);

    /**
     * Added offer to database
     * @param entity that will be added to database
     * @return result of creating
     */
    boolean create(E entity) throws DAOException;

    /**
     * Get offer from database
     * @param code key of offer
     */
    E read(String code) throws DAOException;

    /**
     * Get offer from database
     * @param id key of offer
     * @return offer
     */
    E read(int id) throws DAOException;

    /**
     * Change offer is hot status
     * @return result of updating
     */
    boolean update(E entity, boolean isHot) throws DAOException;

    /**
     * Change offer is hot status
     * @return result of updating
     */
    boolean updateActive(E entity, boolean active) throws DAOException;

    /**
     * Change offer number of places
     * @return result of updating
     */
    boolean update(E entity, int newValue) throws DAOException;

    /**
     * Delete offer from database
     * @param code primary key of offer
     * @return result of deleting
     */
    boolean delete(String code) throws DAOException;

    /**
     * Get all offer from database with defined parameters
     * @param offset index of first offer from database
     * @param numOfRecords number of records that given from database
     * @param onlyHot if true, then return only hot offers, if false, then all offers
     * @return List of offers in queue, first all hot then all not hot, if parameter onlyHot is false
     */
    List<E> readAll(int offset, int numOfRecords, boolean onlyHot) throws DAOException;

    /**
     * Get all offer from database with defined parameters and in sorted queue
     * @param offset index of first offer from database
     * @param numOfRecords number of records that given from database
     * @param sortBy parameter of sorting
     * @return List of offers in sorted queue
     */
    List<Offer> readAllSorted(int offset, int numOfRecords, SORTING_BY sortBy) throws DAOException;


    /**
     * @return List of all offer types
     */
    List<String> readAllOfferTypes() throws DAOException;

}
