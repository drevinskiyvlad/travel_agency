package com.travel_agency.DB.DAO;

import java.util.List;

public interface DAO<E, K> {
    boolean create(E entity);
    E read(K key);
    boolean update(E entity, K newValue);
    boolean delete(E entity);
    List<E> readAll();
}
