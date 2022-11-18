package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import model.dao.CityDaoFactory;
import model.dao.EventDaoFactory;
import model.dao.PersonDaoFactory;
import model.entities.City;
import model.entities.Event;
import model.entities.Person;
import model.entities.enums.Category;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        int menu = 0;
        System.out.println("-------------------------");
        System.out.println("CADASTRO DE EVENTOS");
        System.out.println("-------------------------");
        System.out.println();
        System.out.print("Digite o nome da sua cidade: ");
        City cidadeDoUsuario = new City(sc.nextLine().trim());
        CityDaoFactory.createCityDao().insert(cidadeDoUsuario);
        System.out.println("");
        System.out.println("-------------------------");
        System.out.println("Cadastre o seu usuario");
        System.out.println("-------------------------");
        System.out.println();
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("Cpf: ");
        String cpf = sc.nextLine();
        Person pessoa = new Person(nome, cpf, cidadeDoUsuario);
        PersonDaoFactory.createPersonDao().insert(pessoa);
        cidadeDoUsuario.setAllEvents(EventDaoFactory.createEventDao().findAll());
        pessoa.setEvents(PersonDaoFactory.createPersonDao().findAll(pessoa));

        while(menu != 8) {
            if (menu == 1) {
                sc.nextLine();
                System.out.print("Nome do evento: ");
                String name = sc.nextLine().trim();
                System.out.print("Endereço: ");
                String cep = sc.nextLine().trim();
                System.out.print("Categoria (Cultural, Esportivo, Religioso, Outro): ");
                Category category = Category.valueOf(sc.next().toUpperCase().trim());
                System.out.print("Data (dd/mm/yyyy) : ");
                LocalDate date = LocalDate.parse(sc.next().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                System.out.print("Descrição: ");
                sc.nextLine();
                String description = sc.nextLine().trim();
                Event event = new Event(name, cep, category, date, description, cidadeDoUsuario);
                EventDaoFactory.createEventDao().insert(event);
                cidadeDoUsuario.addEvent(event);
            }

            if (menu == 2) {
                cidadeDoUsuario.showAllEvents();
            }

            if (menu == 3) {
                pessoa.consultEvents();
            }

            if (menu == 4) {
                cidadeDoUsuario.nextEvents();
                System.out.println("");
                System.out.print("Digite o nome do evento para participar: ");
                sc.nextLine();
                System.out.println("");
                Event eventToRegister = cidadeDoUsuario.getEventByName(sc.nextLine().trim(), cidadeDoUsuario.getNextEvents());
                PersonDaoFactory.createPersonDao().insertEvent(eventToRegister, pessoa);
                pessoa.setEvents(PersonDaoFactory.createPersonDao().findAll(pessoa));
                pessoa.consultEvents();
            }

            if (menu == 5) {
                if(pessoa.consultEvents()){
                    System.out.println("");
                    System.out.print("Digite o nome do evento para sair: ");
                    sc.nextLine();
                    pessoa.leaveEvent(sc.nextLine().trim());
                }
            }

            if (menu == 6) {
                cidadeDoUsuario.nextEvents();
            }

            if (menu == 7) {
                cidadeDoUsuario.pastEvents();
            }

            Menu.exibirMenu();
            menu = sc.nextInt();
        }

    }
}