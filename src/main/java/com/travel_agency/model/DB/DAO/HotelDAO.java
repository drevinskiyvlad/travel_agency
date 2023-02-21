package com.travel_agency.model.DB.DAO;

import com.travel_agency.model.entity.Entity;
import com.travel_agency.utils.exceptions.DAOException;

import java.util.List;

public interface HotelDAO<E extends Entity> {

    /**
     * Added hotel to database
     * @param entity that will be added to database
     * @return result of creating
     */
    boolean create(E entity) throws DAOException;

    /**
     * Get hotel from database
     * @param id key of hotel
     * @return hotel
     */
    E read(int id) throws DAOException;

    /**
     * Delete hotel from database
     * @param id primary key of hotel
     * @return result of deleting
     */
    boolean delete(int id) throws DAOException;

    /**
     * @return List of all hotel types
     */
    List<String> readAllHotelTypes() throws DAOException;
}
