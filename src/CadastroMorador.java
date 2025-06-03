package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroMorador {
    private List<Morador> moradores;
    private List<Apartamento> apartamentos;

    public CadastroMorador() {
        moradores = new ArrayList<>();
        apartamentos = new ArrayList<>();
    }

    public void cadastrarMorador() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do morador: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do morador: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o número do apartamento: ");
        String apartamento = scanner.nextLine();

        System.out.print("Digite o telefone do morador: ");
        String telefone = scanner.nextLine();

        System.out.print("O morador é proprietário ou inquilino? (P/I): ");
        String tipo = scanner.nextLine();

        Morador morador = null;
        if (tipo.equalsIgnoreCase("P")) {
            morador = new Proprietario(nome, cpf, apartamento, telefone);
        } else if (tipo.equalsIgnoreCase("I")) {
            morador = new Inquilino(nome, cpf, apartamento, telefone);
        }

        if (morador != null) {
            moradores.add(morador);
            // Associar ao apartamento
            Apartamento apt = apartamentos.stream()
                    .filter(a -> a.getNumero().equals(apartamento))
                    .findFirst()
                    .orElse(new Apartamento(apartamento));
            apt.setMorador(morador);
            if (!apartamentos.contains(apt)) {
                apartamentos.add(apt);
            }
            System.out.println("Morador cadastrado com sucesso!");
        } else {
            System.out.println("Tipo de morador inválido.");
        }
    }

    public void exibirMoradores() {
        if (moradores.isEmpty()) {
            System.out.println("Nenhum morador cadastrado.");
        } else {
            for (Morador morador : moradores) {
                morador.exibirInformacoes();
            }
        }
    }

    public void exibirApartamentos() {
        if (apartamentos.isEmpty()) {
            System.out.println("Nenhum apartamento cadastrado.");
        } else {
            for (Apartamento apartamento : apartamentos) {
                apartamento.exibirInformacoes();
            }
        }
    }

    public List<Morador> getMoradores() {
        return moradores;
    }

    public List<Apartamento> getApartamentos() {
        return apartamentos;
    }
}