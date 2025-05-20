package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroInquilino {
    private List<Inquilino> inquilino;

    public CadastroInquilino() {
        moradores = new ArrayList<>();
    }

    public void CadastroInquilino() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do inquilino: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o número do apartamento: ");
        String apartamento = scanner.nextLine();

        System.out.print("Digite o telefone do inquilino: ");
        String telefone = scanner.nextLine();

        System.out.print("O morador é proprietário ou inquilino? (P/I): ");
        String tipo = scanner.nextLine();

        Inquilino inquilino = null;
        if (tipo.equalsIgnoreCase("P")) {
            inquilino = new Proprietario(nome, apartamento, telefone);
        } else if (tipo.equalsIgnoreCase("I")) {
            inquilino = new Inquilino(nome, apartamento, telefone);
        }

        if (inquilino != null) {
            inquilino.add(inquilino);
            System.out.println("Inquilino cadastrado com sucesso!");
        } else {
            System.out.println("Tipo de inquilino inválido.");
        }
    }

    public void exibirMoradores() {
        if (moradores.isEmpty()) {
            System.out.println("Nenhum inquilino cadastrado.");
        } else {
            for (Morador inquilino : moradores) {
                inquilino.exibirInformacoes();
            }
        }
    }
}
