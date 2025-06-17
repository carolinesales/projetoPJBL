package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa uma tarefa atribuída a um funcionário.
 */
public class Tarefa {
    private String descricao;
    private LocalDate data;
    private String status;
    private Funcionario funcionario;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor da classe Tarefa.
     * @param descricao Descrição da tarefa.
     * @param data Data da tarefa.
     * @param funcionario Funcionário responsável pela tarefa.
     */
    public Tarefa(String descricao, LocalDate data, Funcionario funcionario) {
        this.descricao = descricao;
        this.data = data;
        this.status = "Pendente";
        this.funcionario = funcionario;
    }

    // Getters
    public String getDescricao() { return descricao; }
    public LocalDate getData() { return data; }
    public String getStatus() { return status; }
    public Funcionario getFuncionario() { return funcionario; }

    // Setter
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Tarefa: " + descricao + ", Data: " + data.format(DATE_FORMATTER) + ", Status: " + status + ", Funcionário: " + funcionario.getNome();
    }
}