package src;

import java.io.Serializable;

public class Inquilino extends Morador implements Serializable {
    private static final double TAXA_PADRAO_INQUILINO = 600.0;
    private static final long serialVersionUID = 1L;

    public Inquilino(String nome, String cpf, String apartamento, String telefone) throws IllegalArgumentException {
        super(validarNome(nome), validarCpf(cpf), validarApartamento(apartamento), validarTelefone(telefone));
    }

    // Validações
    private static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        String nomeNormalizado = nome.trim().replaceAll("\\s+", " ");
        if (nomeNormalizado.length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres.");
        }
        return nomeNormalizado;
    }

    private static String validarCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos numéricos.");
        }
        return cpf;
    }

    private static String validarApartamento(String apartamento) {
        if (apartamento == null || apartamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Número do apartamento não pode ser nulo ou vazio.");
        }
        return apartamento.trim();
    }

    private static String validarTelefone(String telefone) {
        if (telefone == null || !telefone.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Telefone deve conter 10 ou 11 dígitos numéricos.");
        }
        return telefone;
    }

    @Override
    public void exibirInformacoes() {
        System.out.printf("Inquilino: %s, Apartamento: %s, Telefone: %s, CPF: %s%n",
                nome, apartamento, telefone, cpf);
    }

    @Override
    public double calcularPagamento() {
        return TAXA_PADRAO_INQUILINO; // Taxa fixa para inquilinos
    }

    // Método para permitir ajuste dinâmico da taxa, se necessário
    public static double getTaxaPadrao() {
        return TAXA_PADRAO_INQUILINO;
    }

    // Método toString para depuração e logging
    @Override
    public String toString() {
        return String.format("Inquilino{nome='%s', cpf='%s', apartamento='%s', telefone='%s'}",
                nome, cpf, apartamento, telefone);
    }

    // Método equals para comparar inquilinos (baseado no CPF)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Inquilino)) return false;
        Inquilino outro = (Inquilino) obj;
        return cpf.equals(outro.cpf);
    }

    // Método hashCode consistente com equals
    @Override
    public int hashCode() {
        return cpf.hashCode();
    }
}
