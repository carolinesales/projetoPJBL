package src;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static Condominio condominio;
    private static Scanner scanner = new Scanner(System.in);
    private static CadastroMorador cadastroMorador;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("pt", "BR"));

    public static void main(String[] args) {
        cadastroMorador = new CadastroMorador();
        condominio = new Condominio();
        try {
            carregarDadosIniciais();
        } catch (IOException | CondominioException e) {
            System.err.println("Erro ao carregar dados iniciais: " + e.getMessage());
        }

        while (true) {
            exibirMenu();
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                if (opcao == 0) {
                    salvarTodosDados();
                    System.out.println("Saindo do programa...");
                    break;
                }
                executarOpcao(opcao);
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida! Digite um número.");
                scanner.nextLine();
            } catch (CondominioException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== Sistema de Gerenciamento de Condomínio ===");
        System.out.println("1. Cadastrar Morador");
        System.out.println("2. Cadastrar Apartamento");
        System.out.println("3. Cadastrar Funcionário");
        System.out.println("4. Cadastrar Reserva de Área Comum");
        System.out.println("5. Adicionar Tarefa");
        System.out.println("6. Visualizar Tarefas");
        System.out.println("7. Listar Funcionários");
        System.out.println("8. Deletar Tarefa");
        System.out.println("9. Adicionar Despesa");
        System.out.println("10. Listar Despesas");
        System.out.println("11. Listar Moradores");
        System.out.println("12. Listar Apartamentos");
        System.out.println("13. Listar Reservas");
        System.out.println("14. Calcular Total de Despesas do Mês");
        System.out.println("15. Calcular Total de Pagamentos");
        System.out.println("16. Carregar Todos os Dados");
        System.out.println("17. Carregar Dados de Exemplo");
        System.out.println("18. Salvar Todos os Dados");
        System.out.println("19. Abrir Interface Gráfica");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcao(int opcao) throws CondominioException {
        switch (opcao) {
            case 1:
                cadastrarMorador();
                break;
            case 2:
                cadastrarApartamento();
                break;
            case 3:
                cadastrarFuncionario();
                break;
            case 4:
                cadastrarReserva();
                break;
            case 5:
                adicionarTarefa();
                break;
            case 6:
                condominio.listarTarefas();
                break;
            case 7:
                condominio.listarFuncionarios();
                break;
            case 8:
                deletarTarefa();
                break;
            case 9:
                adicionarDespesa();
                break;
            case 10:
                condominio.listarDespesas();
                break;
            case 11:
                condominio.listarMoradores();
                break;
            case 12:
                condominio.listarApartamentos();
                break;
            case 13:
                condominio.listarReservas();
                break;
            case 14:
                calcularTotalDespesasMes();
                break;
            case 15:
                calcularTotalPagamentos();
                break;
            case 16:
                carregarTodosDados();
                break;
            case 17:
                carregarDadosExemplo();
                break;
            case 18:
                salvarTodosDados();
                break;
            case 19:
                abrirInterfaceGrafica();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private static void carregarDadosIniciais() throws IOException, CondominioException {
        try {
            condominio.carregarFuncionariosCSV();
            condominio.carregarDespesasCSV();
            condominio.carregarTarefasCSV();
            condominio.carregarReservasCSV();
            cadastroMorador.carregarMoradoresSalvos("moradores.ser");
        } catch (IOException e) {
            throw new IOException("Erro ao carregar dados: " + e.getMessage(), e);
        } catch (CondominioException e) {
            throw new CondominioException("Erro ao carregar dados: " + e.getMessage(), e);
        }
    }

    private static void carregarTodosDados() throws CondominioException {
        try {
            condominio.carregarFuncionariosCSV();
            condominio.carregarDespesasCSV();
            condominio.carregarTarefasCSV();
            condominio.carregarReservasCSV();
            cadastroMorador.carregarMoradoresSalvos("moradores.ser");
            System.out.println("Todos os dados foram carregados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private static void salvarTodosDados() {
        try {
            condominio.salvarFuncionariosCSV();
            condominio.salvarDespesasCSV();
            condominio.salvarTarefasCSV();
            condominio.salvarReservasCSV();
            cadastroMorador.salvarMoradores("moradores.ser");
            System.out.println("Todos os dados foram salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void carregarDadosExemplo() throws CondominioException {
        Funcionario func = new Funcionario("João Silva", "12345678901", "11987654321", "Zelador", 2000.0);
        condominio.adicionarFuncionario(func);
        Tarefa tarefa = new Tarefa("Limpar piscina", LocalDate.now(), func);
        condominio.adicionarTarefa(tarefa);
        Condominio.Despesa despesa = condominio.new Despesa("Luz", 300.50, LocalDate.now());
        condominio.adicionarDespesa(despesa);
        System.out.println("Dados de exemplo carregados com sucesso!");
    }

    private static void cadastrarMorador() throws CondominioException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Apartamento: ");
        String apartamento = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Tipo (1 - Inquilino, 2 - Proprietário): ");
        String tipo = scanner.nextLine();

        Morador morador;
        if (tipo.equals("1")) {
            morador = new Inquilino(nome, cpf, apartamento, telefone);
        } else if (tipo.equals("2")) {
            morador = new Proprietario(nome, cpf, apartamento, telefone);
        } else {
            throw new CondominioException("Tipo de morador inválido.");
        }

        condominio.adicionarMorador(morador);
        Apartamento apt = new Apartamento(apartamento);
        apt.setMorador(morador);
        condominio.adicionarApartamento(apt);
        System.out.println("Morador cadastrado com sucesso!");
    }

    private static void cadastrarApartamento() {
        System.out.print("Número do apartamento: ");
        String numero = scanner.nextLine();
        System.out.print("CPF do morador (ou deixe vazio): ");
        String cpf = scanner.nextLine();
        Morador morador = null;
        if (!cpf.isEmpty()) {
            morador = condominio.getMoradores().stream()
                    .filter(m -> m.getCpf().equals(cpf))
                    .findFirst()
                    .orElse(null);
            if (morador == null) {
                System.out.println("Morador não encontrado!");
                return;
            }
        }
        Apartamento apartamento = new Apartamento(numero);
        if (morador != null) {
            apartamento.setMorador(morador);
        }
        condominio.adicionarApartamento(apartamento);
        System.out.println("Apartamento cadastrado com sucesso!");
    }

    private static void cadastrarFuncionario() throws CondominioException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Salário base: ");
        double salarioBase;
        try {
            salarioBase = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new CondominioException("Salário inválido!");
        }
        Funcionario funcionario = new Funcionario(nome, cpf, telefone, cargo, salarioBase);
        condominio.adicionarFuncionario(funcionario);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void cadastrarReserva() throws CondominioException {
        System.out.print("Nome da área: ");
        String nomeArea = scanner.nextLine();
        System.out.print("Data (DD/MM/YYYY): ");
        String dataStr = scanner.nextLine();
        if (!dataStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new CondominioException("Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025).");
        }
        System.out.print("Hora (HH:MM): ");
        String horaStr = scanner.nextLine();
        System.out.print("Nome do inquilino: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Apartamento: ");
        String apartamento = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        ReservaAreaComum reserva = new ReservaAreaComum(nome, cpf, apartamento, telefone, dataStr, horaStr, nomeArea);
        condominio.adicionarReserva(reserva);
        System.out.println("Reserva cadastrada com sucesso!");
    }

    private static void adicionarTarefa() throws CondominioException {
        System.out.print("CPF do funcionário: ");
        String cpf = scanner.nextLine();
        Funcionario funcionario = condominio.getFuncionarios().stream()
                .filter(f -> f.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
        if (funcionario == null) {
            throw new CondominioException("Funcionário não encontrado!");
        }
        System.out.print("Descrição da tarefa: ");
        String descricao = scanner.nextLine();
        System.out.print("Data (DD/MM/YYYY): ");
        String dataStr = scanner.nextLine();
        if (!dataStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new CondominioException("Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025).");
        }
        LocalDate data;
        try {
            data = LocalDate.parse(dataStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new CondominioException("Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025). Erro: " + e.getMessage());
        }
        Tarefa tarefa = new Tarefa(descricao, data, funcionario);
        condominio.adicionarTarefa(tarefa);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private static void deletarTarefa() throws CondominioException {
        System.out.print("Descrição da tarefa: ");
        String descrição = scanner.nextLine();
        System.out.print("CPF do funcionário: ");
        String cfp = scanner.nextLine();
        condominio.removerTarefa(descrição, cfp);
        System.out.println("Tarefa deletada com sucesso!");
    }

    private static void adicionarDespesa() throws CondominioException {
        System.out.print("Tipo da despesa: ");
        String tipoDespesa = scanner.nextLine();
        System.out.print("Valor: ");
        double valorDespesa;
        try {
            valorDespesa = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new CondominioException("Valor inválido!");
        }
        System.out.print("Data (DD/MM/YYYY): ");
        String dataStrDespesa = scanner.nextLine();
        if (!dataStrDespesa.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new CondominioException("Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025).");
        }
        LocalDate dataDespesa;
        try {
            dataDespesa = LocalDate.parse(dataStrDespesa, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new CondominioException("Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025). Erro: " + e.getMessage());
        }
        Condominio.Despesa despesa = condominio.new Despesa(tipoDespesa, valorDespesa, dataDespesa);
        condominio.adicionarDespesa(despesa);
        System.out.println("Despesa adicionada com sucesso!");
    }

private static void calcularTotalDespesasMes() {
    System.out.print("Mês (1-12): ");
    int mesInt;
    try {
        mesInt = scanner.nextInt();
        scanner.nextLine();
    } catch (InputMismatchException e) {
        scanner.nextLine();
        System.out.println("Mês inválido!");
        return;
    }
        System.out.print("Ano: ");
        int anoInt;
        try {
            anoInt = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Ano inválido!");
            return;
        }
        // Aqui você pode chamar o método do condominio para calcular o total de despesas do mês
        double totalDespesas = condominio.calcularTotalDespesasMes(mesInt, anoInt);
        System.out.printf("Total de despesas do mês %d/%d: R$ %.2f%n", mesInt, anoInt, totalDespesas);
    }
    
        private static void calcularTotalPagamentos() {
            double totalPagamento = condominio.calcularTotalPagamentos();
            System.out.printf("Total de pagamentos: R$ %.2f%n", totalPagamento);
        }

    private static void abrirInterfaceGrafica() {
        new CondominioGUI(cadastroMorador, condominio).setVisible(true);
        
    }

    private static void carregarMoradoresSalvos() {
        cadastroMorador.carregarMoradoresSalvos("moradores.ser");
        System.out.println("Moradores carregados com sucesso!");
    }
}