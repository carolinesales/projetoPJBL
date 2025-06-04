package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CadastroMorador cadastroMorador = new CadastroMorador();
        CadastroInquilino cadastroInquilino = new CadastroInquilino();
        Condominio condominio = new Condominio();
        List<Funcionario> funcionarios = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar Morador");
            System.out.println("2. Cadastrar Inquilino");
            System.out.println("3. Cadastrar Funcionário");
            System.out.println("4. Exibir Moradores");
            System.out.println("5. Exibir Inquilinos");
            System.out.println("6. Exibir Funcionários");
            System.out.println("7. Exibir Apartamentos");
            System.out.println("8. Cadastrar Despesa");
            System.out.println("9. Exibir Despesas");
            System.out.println("10. Total de Despesas no Mês");
            System.out.println("11. Abrir Interface Gráfica");
            System.out.println("12. Sair");
            System.out.println("13. Carregar moradores de arquivo CSV"); // ✅ nova opção
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    cadastroMorador.cadastrarMorador();
                    condominio.adicionarApartamento(
                            cadastroMorador.getApartamentos().get(cadastroMorador.getApartamentos().size() - 1));
                    break;

                case 2:
                    cadastroInquilino.cadastrarInquilino();
                    Morador inquilino = cadastroInquilino.getInquilinos().get(
                            cadastroInquilino.getInquilinos().size() - 1);
                    cadastroMorador.getMoradores().add(inquilino);
                    Apartamento apt = cadastroMorador.getApartamentos().stream()
                            .filter(a -> a.getNumero().equals(inquilino.getApartamento()))
                            .findFirst()
                            .orElse(new Apartamento(inquilino.getApartamento()));
                    apt.setMorador(inquilino);
                    if (!cadastroMorador.getApartamentos().contains(apt)) {
                        cadastroMorador.getApartamentos().add(apt);
                        condominio.adicionarApartamento(apt);
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Digite o nome do funcionário: ");
                        String nome = scanner.nextLine();
                        if (nome.trim().isEmpty()) {
                            throw new CondominioException("Nome não pode estar vazio.");
                        }

                        System.out.print("Digite o CPF do funcionário: ");
                        String cpf = scanner.nextLine();
                        if (cpf.trim().isEmpty()) {
                            throw new CondominioException("CPF não pode estar vazio.");
                        }

                        System.out.print("Digite o telefone do funcionário: ");
                        String telefone = scanner.nextLine();

                        System.out.print("Digite o salário base do funcionário: ");
                        double salarioBase = scanner.nextDouble();
                        scanner.nextLine();

                        Funcionario funcionario = new Funcionario(nome, cpf, telefone, salarioBase);
                        funcionarios.add(funcionario);
                        System.out.println("Funcionário cadastrado com sucesso!");
                    } catch (CondominioException ex) {
                        System.out.println("Erro: " + ex.getMessage());
                    }
                    break;

                case 4:
                    cadastroMorador.exibirMoradores();
                    break;

                case 5:
                    cadastroInquilino.exibirInquilinos();
                    break;

                case 6:
                    if (funcionarios.isEmpty()) {
                        System.out.println("Nenhum funcionário cadastrado.");
                    } else {
                        for (Funcionario funcionario : funcionarios) {
                            funcionario.exibirInformacoes();
                        }
                    }
                    break;

                case 7:
                    condominio.listarApartamentos();
                    break;

                case 8:
                    try {
                        System.out.print("Digite o tipo da despesa: ");
                        String tipo = scanner.nextLine();
                        if (tipo.trim().isEmpty()) {
                            throw new CondominioException("Tipo da despesa não pode estar vazio.");
                        }

                        System.out.print("Digite o valor da despesa: ");
                        double valor = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Digite a data (YYYY-MM-DD): ");
                        String dataStr = scanner.nextLine();
                        if (dataStr.trim().isEmpty()) {
                            throw new CondominioException("Data não pode estar vazia.");
                        }

                        LocalDate data = LocalDate.parse(dataStr);
                        condominio.adicionarDespesa(new Despesa(tipo, valor, data));
                        System.out.println("Despesa cadastrada com sucesso!");
                    } catch (CondominioException | java.time.format.DateTimeParseException ex) {
                        System.out.println("Erro: " + ex.getMessage());
                    }
                    break;

                case 9:
                    condominio.listarDespesas();
                    break;

                case 10:
                    try {
                        System.out.print("Digite o mês (1-12): ");
                        int mes = scanner.nextInt();
                        if (mes < 1 || mes > 12) {
                            throw new CondominioException("Mês inválido (use 1-12).");
                        }

                        System.out.print("Digite o ano: ");
                        int ano = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Total de despesas: R$" + condominio.calcularTotalDespesasMes(mes, ano));
                    } catch (CondominioException ex) {
                        System.out.println("Erro: " + ex.getMessage());
                    }
                    break;

                case 11:
                    SwingUtilities.invokeLater(() -> new CondominioGUI(cadastroMorador, condominio).setVisible(true));
                    break;

                case 12:
                    System.out.println("Saindo...");
                    return;

                case 13:
                    System.out.print("Digite o caminho do arquivo CSV: ");
                    String caminho = scanner.nextLine();
                    cadastroMorador.carregarMoradoresDeCSV(caminho);
                    break;
                case 14:
                    System.out.print("Digite o nome do arquivo para salvar (ex: moradores.ser): ");
                    String arqSalvar = scanner.nextLine();
                    cadastroMorador.salvarMoradores(arqSalvar);
                    break;

                case 15:
                    System.out.print("Digite o nome do arquivo para carregar (ex: moradores.ser): ");
                    String arqCarregar = scanner.nextLine();
                    cadastroMorador.carregarMoradoresSalvos(arqCarregar);
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
