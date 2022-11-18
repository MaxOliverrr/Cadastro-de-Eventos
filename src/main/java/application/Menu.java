package application;

import lombok.NoArgsConstructor;

@NoArgsConstructor

public class Menu {

    public static void exibirMenu() {
        System.out.println("");
        System.out.println("1 - Criar evento");
        System.out.println("2 - Listar eventos existentes");
        System.out.println("3 - Listar eventos que eu estou participando");
        System.out.println("4 - Cadastrar em um evento");
        System.out.println("5 - Sair de em um evento");
        System.out.println("6 - Proximos eventos");
        System.out.println("7 - Eventos que já ocorreram");
        System.out.println("8 - Sair");
    }
}