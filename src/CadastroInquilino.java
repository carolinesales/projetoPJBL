package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroInquilino {
    private List<Inquilino> inquilinos;

    public CadastroInquilino() {
        inquilinos = new ArrayList<>();
    }

    public void cadastrarInquilino() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do inquilino: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do inquilino: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o número do apartamento: ");
        String apartamento = scanner.nextLine();

        System.out.print("Digite o telefone do inquilino: ");
        String telefone = scanner.nextLine();

        Inquilino inquilino = new Inquilino(nome, cpf, apartamento, telefone);
        inquilinos.add(inquilino);

        System.out.println("Inquilino cadastrado com sucesso!");
    }

    public void exibirInquilinos() {
        if (inquilinos.isEmpty()) {
            System.out.println("Nenhum inquilino cadastrado.");
        } else {
            for (Inquilino inquilino : inquilinos) {
                inquilino.exibirInformacoes();
            }
        }
    }

    public List<Inquilino> getInquilinos() {
        return inquilinos;
    }
}
