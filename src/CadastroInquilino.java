package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// classe responsável pelo cadastro de inquilinos
public class CadastroInquilino {
    private final List<Inquilino> inquilinos;

    // construtor que inicializa a lista de inquilinos
    public CadastroInquilino() {
        inquilinos = new ArrayList<>();
    }

    // método para cadastrar um novo inquilino
    public void cadastrarInquilino() {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Digite o nome do inquilino: "); // nome do inquilino
            String nome = scanner.nextLine();

            System.out.print("Digite o CPF do inquilino: "); // CPF do inquilino
            String cpf = scanner.nextLine();

            System.out.print("Digite o número do apartamento: "); // número do apartamento
            String apartamento = scanner.nextLine();

            System.out.print("Digite o telefone do inquilino: "); // telefone do inquilino
            String telefone = scanner.nextLine();

            Inquilino inquilino = new Inquilino(nome, cpf, apartamento, telefone); // cria um novo inquilino com os dados fornecidos
            inquilinos.add(inquilino);

            System.out.println("Inquilino cadastrado com sucesso!"); // exibe mensagem de sucesso
        }
    }

    // método para exibir todos os inquilinos cadastrados
    public void exibirInquilinos() { 
        if (inquilinos.isEmpty()) { // verifica se a lista de inquilinos está vazia
            System.out.println("Nenhum inquilino cadastrado."); // exibe mensagem se não houver inquilinos
        } else {
            for (Inquilino inquilino : inquilinos) {  // itera sobre cada inquilino na lista  
                inquilino.exibirInformacoes(); // chama o método para exibir as informações do inquilino
            }
        }
    }

    public List<Inquilino> getInquilinos() { // método para obter a lista de inquilinos
        return inquilinos; // retorna a lista de inquilinos cadastrados
    }
}
