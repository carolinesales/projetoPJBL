package src;

import java.util.ArrayList;
import java.util.List;

// classe que representa um funcionário do condomínio
public class Funcionario implements Pagavel {
    private String nome;
    private String cpf;
    private String telefone;
    private String cargo;
    private double salarioBase;
    private List<Tarefa> tarefas;

    // construtor que inicializa os atributos do funcionário
    public Funcionario(String nome, String cpf, String telefone, String cargo, double salarioBase) throws CondominioException {
        this.nome = validarNome(nome);
        this.cpf = validarCpf(cpf);
        this.telefone = validarTelefone(telefone);
        this.cargo = validarCargo(cargo);
        this.salarioBase = validarSalario(salarioBase);
        this.tarefas = new ArrayList<>();
    }

    // métodos de validação para os atributos do funcionário
    private String validarNome(String nome) throws CondominioException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new CondominioException("Nome não pode ser nulo ou vazio.");
        }
        return nome.trim();
    }

    // valida o CPF do funcionário
    private String validarCpf(String cpf) throws CondominioException {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new CondominioException("CPF deve conter exatamente 11 dígitos numéricos.");
        }
        return cpf;
    }

    // valida o telefone do funcionário
    private String validarTelefone(String telefone) throws CondominioException {
        if (telefone == null || !telefone.matches("\\d{10,11}")) {
            throw new CondominioException("Telefone deve conter 10 ou 11 dígitos numéricos.");
        }
        return telefone;
    }

    // valida o cargo do funcionário
    private String validarCargo(String cargo) throws CondominioException {
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new CondominioException("Cargo não pode ser nulo ou vazio.");
        }
        return cargo.trim();
    }

    // valida o salário do funcionário
    private double validarSalario(double salario) throws CondominioException {
        if (salario <= 0) {
            throw new CondominioException("Salário deve ser maior que zero.");
        }
        return salario;
    }

    // método para adicionar uma tarefa ao funcionário
    public void adicionarTarefa(Tarefa tarefa) {
        if (tarefa != null) tarefas.add(tarefa);
    }

    // método para exibir as informações do funcionário
    public void exibirInformacoes() {
        System.out.printf("Funcionário: %s, CPF: %s, Telefone: %s, Cargo: %s, Salário: R$%.2f%n",
                nome, cpf, telefone, cargo, salarioBase);
    }

    @Override
    public double calcularPagamento() { // calcula o pagamento do funcionário
        return salarioBase;
    }

    @Override
    public String toString() { 
        return "Funcionário: " + nome + ", CPF: " + cpf + ", Cargo: " + cargo;
    }

    public String getNome() { // nome do funcionário
        return nome;
    }

    public String getCpf() { // CPF do funcionário
        return cpf;
    }

    public String getTelefone() { // telefone do funcionário
        return telefone;
    }

    public String getCargo() { // cargo do funcionário
        return cargo;
    }

    public double getSalarioBase() { // salário base do funcionário
        return salarioBase;
    }

    public List<Tarefa> getTarefas() { // lista de tarefas atribuídas ao funcionário
        return tarefas;
    }
}