package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CadastroMorador cadastro = new CadastroMorador();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar Morador");
            System.out.println("2. Exibir Moradores");
            System.out.println("3. Exibir Apartamentos");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastro.cadastrarMorador();
                    break;
                case 2:
                    cadastro.exibirMoradores();
                    break;
                case 3:
                    cadastro.exibirApartamentos();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}