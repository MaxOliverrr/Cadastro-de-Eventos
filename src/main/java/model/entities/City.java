package model.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Getter  @Setter
public class City {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final LocalDate TODAY = LocalDate.now();

   private String name;
    private List<Event> allEvents = new ArrayList<>();
    private List<Event> nextEvents = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public void addEvent(Event newEvent) {

        for (Event event : allEvents) {
            if (!event.equals(newEvent)) {
                this.allEvents.add(newEvent);
                System.out.println("Evento criado com sucesso!");
                return;
            }
        }

        System.out.println("Este evento já existe!");
    }

    public void showAllEvents() {
        if (!allEvents.isEmpty()) {
            System.out.println("Eventos disponiveis: ");
            allEvents.sort((e1, e2) -> -e1.getEventDate().compareTo(e2.getEventDate()));

            for (Event event : allEvents) {
                if (event.getEventDate().isEqual(TODAY)) {
                    System.out.println("" + event + " Esse evento acontece hoje!");
                }

                if (event.getEventDate().isAfter(TODAY)) {
                    System.out.println("" + event + " Esse evento acontecera " + event.getEventDate().format(FORMATTER));
                }

                if (event.getEventDate().isBefore(TODAY)) {
                    System.out.println("" + event + " Esse evento já aconteceu!");
                }
            }
        } else {
            System.out.println("Nenhum evento encontrado");
        }
    }

    public Event getEventByName(String eventName, List<Event> eventList) {
        return eventList.stream().filter(event1 -> event1.getName().equals(eventName)).collect(Collectors.toList()).get(0);
    }

    public void pastEvents() {
        if (!allEvents.isEmpty()) {
            System.out.println("Eventos que já ocorreram:");
            for (Event event : allEvents) {
                if (event.getEventDate().isBefore(TODAY)) {
                    System.out.println(event);
                    return;
                }
            }

        }

        System.out.println("Nenhum evento encontrado!");
    }

    public void nextEvents() {
        allEvents.sort(Comparator.comparing(Event::getEventDate));
        if (!allEvents.isEmpty()) {
            System.out.println("Proximos Eventos: ");
            for (Event event : allEvents) {
                if (event.getEventDate().isEqual(TODAY)) {
                    nextEvents.add(event);
                    System.out.println("" + event + " Acontece hoje!");
                }

                if (event.getEventDate().isAfter(TODAY)) {
                    nextEvents.add(event);
                    System.out.println("" + event + " Acontecera em " + event.getEventDate().format(FORMATTER));
                }
            }

        } else {
            System.out.println("Nenhum evento encontrado!");
        }
    }

    public String toString() {
        return "City{name='" + this.name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}