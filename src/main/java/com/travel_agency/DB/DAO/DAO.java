package com.travel_agency.DB.DAO;

import java.util.List;

public interface DAO<Entity, Key> {
    boolean create(Entity entity);
    Entity read(Key key);
    boolean update(Entity entity);
    boolean delete(Entity entity);
    List<Entity> readAll();
}
