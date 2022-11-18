package model.dao.impl;

import lombok.AllArgsConstructor;
import model.dao.EventDao;
import model.entities.City;
import model.entities.Event;
import model.entities.enums.Category;
import model.util.exception.DBExcepction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor


public class EventImpl implements EventDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private Connection conn;

    @Override
    public void insert(Event event) {
        String query = "INSERT INTO event (name, cep, category, event_date, description, city) VALUES ('%s', '%s', '%s', '%s','%s', '%s')"
                .formatted(event.getName(), event.getCep(), event.getCategory().getName(), event.getEventDate().format(FORMATTER), event.getDescription(), event.getCity().getName());
        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM event")) {
            while(rs.next()){

                boolean eventHasExist = rs
                        .getString("name").equals(event.getName()) && rs
                        .getString("event_date").equals(event.getEventDate().format(FORMATTER));
                if(eventHasExist){
                    System.out.println("Este evento já existe!");
                    return;
                }
            }
           st.executeUpdate(query);

        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM event")) {

            while(rs.next()){
                String name =  rs.getString("name");
                String cep = rs.getString("cep");
                Category category = Category.valueOf(rs.getString("category").toUpperCase());
                LocalDate eventDate = LocalDate.parse(rs.getString("event_date")) ;
                String description = rs.getString("description");
                City city = new City(rs.getString("city"));

                Event event = new Event(name, cep, category, eventDate, description, city);
                events.add(event);
            }

            return events;

        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }
}
