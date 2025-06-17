package src;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Condominio {
    private CadastroMorador cadastroMorador;
    private List<Apartamento> apartamentos = new ArrayList<>();
    private List<Funcionario> funcionarios = new ArrayList<>();
    private List<Tarefa> tarefas = new ArrayList<>();
    private CadastroReservas cadastroReservas;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("pt", "BR"));

    public Condominio() {
        this.cadastroMorador = new CadastroMorador();
        this.cadastroReservas = new CadastroReservas();
    }

    public void adicionarMorador(Morador morador) throws CondominioException {
        cadastroMorador.cadastrarMorador(morador);
    }

    public void adicionarApartamento(Apartamento apartamento) {
        apartamentos.add(apartamento);
    }

    public void adicionarDespesa(Despesa despesa) throws CondominioException {
        if (despesa.getValor() <= 0) {
            throw new CondominioException("O valor da despesa deve ser maior que zero.");
        }
        despesas.add(despesa);
    }
public class Despesa {
    private String tipo;
    private double valor;
    private LocalDate data;

    public Despesa(String tipo, double valor, LocalDate data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }
}

    public void adicionarFuncionario(Funcionario funcionario) throws CondominioException {
        if (funcionarios.stream().anyMatch(f -> f.getCpf().equals(funcionario.getCpf()))) {
            throw new CondominioException("Funcionário com CPF já cadastrado.");
        }
        funcionarios.add(funcionario);
    }

    public void adicionarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
    }

    public void removerTarefa(String descricao, String cpf) throws CondominioException {
        boolean removed = tarefas.removeIf(t -> t.getDescricao().equals(descricao) && t.getFuncionario().getCpf().equals(cpf));
        if (!removed) {
            throw new CondominioException("Tarefa não encontrada para remoção.");
        }
    }

    public void adicionarReserva(ReservaAreaComum reserva) throws CondominioException {
        if (cadastroReservas == null) {
            throw new CondominioException("Cadastro de reservas não inicializado.");
        }
        cadastroReservas.adicionarReserva(reserva);
    }

    public double calcularTotalDespesasMes(int mes, int ano) {
        return despesas.stream()
                .filter(d -> d.getData().getMonthValue() == mes && d.getData().getYear() == ano)
                .mapToDouble(Despesa::getValor)
                .sum();
    }

    public double calcularTotalPagamentos() {
        double totalTaxas = apartamentos.stream()
                .filter(a -> a.getMorador() != null)
                .mapToDouble(a -> a.getMorador().calcularPagamento())
                .sum();
        double totalSalarios = funcionarios.stream()
                .mapToDouble(Funcionario::calcularPagamento)
                .sum();
        return totalTaxas + totalSalarios;
    }

    public void salvarDespesasCSV() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("despesas.csv"))) {
            for (Despesa despesa : despesas) {
                writer.write(String.format("%s,%.2f,%s%n",
                        despesa.getTipo(),
                        despesa.getValor(),
                        despesa.getData().format(DATE_FORMATTER)));
                System.out.println("Salvando despesa: " + despesa.getTipo() + ", " + despesa.getValor() + ", " + despesa.getData().format(DATE_FORMATTER));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar despesas.csv: " + e.getMessage());
            throw e;
        }
    }

private List<Despesa> despesas = new ArrayList<>();

public void removerDespesa(String tipo, double valor, LocalDate data) throws CondominioException {
    Iterator<Despesa> iterator = despesas.iterator();
    boolean removido = false;
    while (iterator.hasNext()) {
        Despesa despesa = iterator.next();
        if (despesa.getTipo().equalsIgnoreCase(tipo) &&
            Math.abs(despesa.getValor() - valor) < 0.01 &&
            despesa.getData().equals(data)) {
            iterator.remove();
            removido = true;
            System.out.println("Despesa removida: " + despesa);
            break;
        }
    }
    if (!removido) {
        throw new CondominioException("Despesa não encontrada.");
    }
}

    public void carregarDespesasCSV() throws IOException, CondominioException {
        File file = new File("despesas.csv");
        if (!file.exists()) {
            System.out.println("despesas.csv não encontrado, iniciando com lista vazia.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    System.err.println("Linha " + lineNumber + " vazia em despesas.csv, ignorando.");
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.err.println("Linha " + lineNumber + " inválida em despesas.csv: '" + line + "'. Esperado: tipo,valor,data (3 colunas).");
                    continue;
                }
                try {
                    String tipo = parts[0].trim();
                    if (tipo.isEmpty()) {
                        System.err.println("Linha " + lineNumber + " em despesas.csv: tipo vazio em '" + line + "'.");
                        continue;
                    }
                    double valor = Double.parseDouble(parts[1].trim());
                    LocalDate data = LocalDate.parse(parts[2].trim(), DATE_FORMATTER);
                    despesas.add(new Despesa(tipo, valor, data));
                    System.out.println("Carregando despesa (linha " + lineNumber + "): " + tipo + ", " + valor + ", " + data);
                } catch (NumberFormatException e) {
                    System.err.println("Linha " + lineNumber + " em despesas.csv: valor inválido em '" + line + "'. Erro: " + e.getMessage());
                } catch (DateTimeParseException e) {
                    System.err.println("Linha " + lineNumber + " em despesas.csv: data inválida em '" + line + "'. Erro: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler despesas.csv: " + e.getMessage());
            throw e;
        }
    }

    public void salvarFuncionariosCSV() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("funcionarios.csv"))) {
            for (Funcionario funcionario : funcionarios) {
                writer.write(String.format("%s,%s,%s,%s,%.2f%n",
                        funcionario.getNome(),
                        funcionario.getCpf(),
                        funcionario.getTelefone(),
                        funcionario.getCargo(),
                        funcionario.getSalarioBase()));
            }
        }
    }

    public void carregarFuncionariosCSV() throws IOException, CondominioException {
        File file = new File("funcionarios.csv");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) continue;
                try {
                    String nome = parts[0];
                    String cpf = parts[1];
                    String telefone = parts[2];
                    String cargo = parts[3];
                    double salarioBase = Double.parseDouble(parts[4]);
                    Funcionario funcionario = new Funcionario(nome, cpf, telefone, cargo, salarioBase);
                    funcionarios.add(funcionario);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao parsear linha em funcionarios.csv: " + line);
                }
            }
        }
    }

    public void salvarTarefasCSV() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tarefas.csv"))) {
            for (Tarefa tarefa : tarefas) {
                writer.write(String.format("%s,%s,%s,%s%n",
                        tarefa.getDescricao(),
                        tarefa.getData().format(DATE_FORMATTER),
                        tarefa.getStatus(),
                        tarefa.getFuncionario().getCpf()));
                System.out.println("Salvando tarefa: " + tarefa.getDescricao() + ", " + tarefa.getData().format(DATE_FORMATTER) + ", " + tarefa.getStatus());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas.csv: " + e.getMessage());
            throw e;
        }
    }

    public void carregarTarefasCSV() throws IOException, CondominioException {
        File file = new File("tarefas.csv");
        if (!file.exists()) {
            System.out.println("tarefas.csv não encontrado, iniciando com lista vazia.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    System.err.println("Linha " + lineNumber + " vazia em tarefas.csv, ignorando.");
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.err.println("Linha " + lineNumber + " inválida em tarefas.csv: '" + line + "'. Esperado: descricao,data,status,cpf (4 colunas).");
                    continue;
                }
                try {
                    String descricao = parts[0].trim();
                    LocalDate data = LocalDate.parse(parts[1].trim(), DATE_FORMATTER);
                    String status = parts[2].trim();
                    String cpf = parts[3].trim();
                    Funcionario funcionario = funcionarios.stream()
                            .filter(f -> f.getCpf().equals(cpf))
                            .findFirst()
                            .orElse(null);
                    if (funcionario == null) {
                        System.err.println("Linha " + lineNumber + " em tarefas.csv: funcionário não encontrado para tarefa: '" + line + "'.");
                        continue;
                    }
                    Tarefa tarefa = new Tarefa(descricao, data, funcionario);
                    tarefa.setStatus(status);
                    tarefas.add(tarefa);
                    System.out.println("Carregando tarefa (linha " + lineNumber + "): " + descricao + ", " + data + ", " + status);
                } catch (DateTimeParseException e) {
                    System.err.println("Linha " + lineNumber + " em tarefas.csv: data inválida em '" + line + "'. Erro: " + e.getMessage());
                }
            }
        }
    }

    public void salvarReservasCSV() throws IOException {
        if (cadastroReservas == null) {
            throw new IOException("Cadastro de reservas não inicializado.");
        }
        cadastroReservas.salvarReservasCSV();
    }

    public void carregarReservasCSV() throws IOException, CondominioException {
        if (cadastroReservas == null) {
            throw new IOException("Cadastro de reservas não inicializado.");
        }
        cadastroReservas.carregarReservasCSV();
    }

    public List<Morador> getMoradores() {
        return cadastroMorador.getMoradores();
    }

    public List<Apartamento> getApartamentos() {
        return apartamentos;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public CadastroReservas getCadastroReservas() {
        return cadastroReservas;
    }

    public void listarDespesas() {
        if (despesas.isEmpty()) {
            System.out.println("Não há despesas cadastradas.");
            return;
        }
        for (Despesa despesa : despesas) {
            System.out.printf("Despesa: %s,%.2f,%s%n",
                    despesa.getTipo(),
                    despesa.getValor(),
                    despesa.getData().format(DATE_FORMATTER));
        }
    }

    public void listarFuncionarios() {
        if (funcionarios.isEmpty()) {
            System.out.println("Não há funcionários cadastrados.");
            return;
        }
        for (Funcionario funcionario : funcionarios) {
            System.out.printf("Funcionário: %s, CPF: %s, Telefone: %s, Cargo: %s, Salário: %.2f%n",
                    funcionario.getNome(),
                    funcionario.getCpf(),
                    funcionario.getTelefone(),
                    funcionario.getCargo(),
                    funcionario.getSalarioBase());
        }
    }

    public void listarTarefas() {
        if (tarefas.isEmpty()) {
            System.out.println("Não há nenhuma tarefa cadastrada.");
            return;
        }
        for (Tarefa tarefa : tarefas) {
            System.out.printf("Tarefa: %s, Data: %s, Status: %s, Nome: %s%n",
                    tarefa.getDescricao(),
                    tarefa.getData().format(DATE_FORMATTER),
                    tarefa.getStatus(),
                    tarefa.getFuncionario().getNome());
        }
    }

    public void listarApartamentos() {
        if (apartamentos.isEmpty()) {
            System.out.println("Não há apartamentos registrados.");
            return;
        }
        for (Apartamento apartamento : apartamentos) {
            System.out.printf("Apartamento %s, %s%n",
                    apartamento.getNumero(), // Substitua por um método válido da sua classe Apartamento
                    apartamento.getMorador() == null ? "" : apartamento.getMorador().getNome());
        }
    }

    public void listarMoradores() {
        if (cadastroMorador.getMoradores().isEmpty()) {
            System.out.println("Não há moradores registrados.");
            return;
        }
        for (Morador morador : cadastroMorador.getMoradores()) {
            // Exibe informações básicas do morador; ajuste conforme os métodos disponíveis em Morador
            System.out.printf("Morador: %s, CPF: %s, Telefone: %s%n",
                    morador.getNome(),
                    morador.getCpf(),
                    morador.getTelefone());
        }
    }

    public void listarReservas() {
        if (cadastroReservas == null) {
            System.out.println("Cadastro de reservas não inicializado.");
            return;
        }
        cadastroReservas.listarReservas();
    }
}