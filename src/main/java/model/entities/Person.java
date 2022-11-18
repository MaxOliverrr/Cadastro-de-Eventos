package model.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class Person {
    private final String name;
    private final String cpf;
    private final City city;
    private List<Event> events = new ArrayList<>();



    public Event getEventByName(String eventName) {
        return events.stream().filter((event) ->  event.getName().equals(eventName)).collect(Collectors.toList()).get(0);
    }

    public void registerInEvent(Event eventToRegister) {
        if (!events.isEmpty()) {

            for (Event event : events) {
                if(!event.equals(eventToRegister)){
                    events.add(eventToRegister);
                    System.out.println("Pronto!");
                    return;
                }

                System.out.println("Você já participa deste evento!");
            }

        } else {
            events.add(eventToRegister);
            System.out.println("Pronto!");
        }
    }

    public void leaveEvent(String eventName) {
        Event eventToRemove = getEventByName(eventName);
        for (Event event : events) {
            if(event != null){
                events.remove(eventToRemove);
                System.out.println("Você saiu deste evento!");
                return;
            }
        }
        System.out.println("Evento não encontrado!");
    }

    public boolean consultEvents() {
        if (!events.isEmpty()) {
            System.out.println("Você esta cadastrado nos eventos: ");
            events.forEach(System.out::println);
            return true;
        } else {
            System.out.println("Você não está participando de nenhum evento");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", city=" + city +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!Objects.equals(name, person.name)) return false;
        if (!Objects.equals(cpf, person.cpf)) return false;
        return Objects.equals(city, person.city);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}