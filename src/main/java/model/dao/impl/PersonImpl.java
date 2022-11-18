package model.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import model.dao.PersonDao;
import model.entities.City;
import model.entities.Event;
import model.entities.Person;
import model.entities.enums.Category;
import model.util.exception.DBExcepction;

@AllArgsConstructor

public class PersonImpl implements PersonDao {
    private Connection conn;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public void insert(Person person) {
        String query = "INSERT INTO person (name, cpf, city) VALUES ('%s', '%s', '%s')".formatted(person.getName(), person.getCpf(), person.getCity().getName());

        try (Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM person")) {
            boolean cpfHasExist = false;
            while (rs.next()) {
                if (person.getCpf().equals(rs.getString("cpf"))) {
                    cpfHasExist = true;
                }
            } if (cpfHasExist) {
                System.out.println("Usuario selecionado");
                return;
            }
            System.out.println("Pessoa cadastrada com sucesso!");
            st.executeUpdate(query);

        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }

    @Override
    public Integer getEventId(Event event) {

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM event")) {
            Integer eventId = null;

            while (rs.next()) {
                boolean eventHasExist = rs.getString("name").equals(event.getName()) && rs
                        .getString("event_date").equals(String.format("%s", event.getEventDate()));

                if (eventHasExist) {
                    eventId = rs.getInt("id");
                }
            }
            return eventId;

        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }

        @Override
    public void insertEvent(Event event, Person person) {

        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT person_event.event_id FROM person_event")){
            boolean idAlreadyExists = false;
            Integer eventId = getEventId(event);
            String query = "INSERT INTO person_event (person_cpf, event_id) VALUES ('%s', '%d')"
                    .formatted(person.getCpf(), eventId);
            while (rs.next()) {
                    if(eventId.equals(rs.getInt("event_id"))){
                        idAlreadyExists = true;
                    }
                }

            if(!idAlreadyExists) {
                System.out.println("Evento cadastrado no banco de dados!");
                st.executeUpdate(query);
                return;
            }
                System.out.println("Este evento já está cadastrado!");

        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
    }

    @Override
    public List<Integer> getAllEventsId(String cpf) {
        List<Integer> idList = new ArrayList<>();
        String query = "SELECT event_id FROM person_event WHERE person_cpf = '%s'".formatted(cpf);
        try(Statement st = conn.createStatement();
            ResultSet rsWithEventsId = st.executeQuery(query)){
            while(rsWithEventsId.next()){
                idList.add(rsWithEventsId.getInt("event_id"));
            }
        } catch (SQLException e) {
            throw new DBExcepction(e.getMessage());
        }
        return idList;
    }

    @Override
    public List<Event> findAll(Person person) {
        List<Event> events = new ArrayList<>();
        for (Integer id : getAllEventsId(person.getCpf())) {
            String query = "SELECT * FROM event WHERE event.id = '%d'".formatted(id);
            try (Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)){
                while(rs.next()){
                    String name = rs.getString("name");
                    String cep = rs.getString("cep");
                    Category category = Category.valueOf(rs.getString("category").toUpperCase());
                    LocalDate eventDate = LocalDate.parse(rs.getString("event_date"));
                    String description = rs.getString("description");
                    City city = new City(rs.getString("city"));

                    Event event = new Event(name, cep, category, eventDate, description, city);
                    events.add(event);
                }

            } catch (SQLException e) {
                throw new DBExcepction(e.getMessage());
            }
        }
        return events;
    }

}