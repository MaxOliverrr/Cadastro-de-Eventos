package model.dao;

import model.entities.Event;
import model.entities.Person;

import java.util.List;

public interface PersonDao {
    void insert(Person person);
    Integer getEventId(Event event);
    void insertEvent(Event event, Person person);
    List<Integer> getAllEventsId(String cpf);
    List<Event> findAll(Person person);

}
