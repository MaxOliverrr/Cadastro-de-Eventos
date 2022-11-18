package model.dao;

import java.util.List;
import model.entities.Event;

public interface EventDao {
    void insert(Event event);

    List<Event> findAll();
}
