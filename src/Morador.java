package src;

import java.io.Serializable;

public abstract class Morador implements Pagavel, Serializable {
    private String nome;
    private String cpf;
    private String apartamento;
    private String telefone;

    public Morador(String nome, String cpf, String apartamento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.apartamento = apartamento;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getApartamento() {
        return apartamento;
    }

    public String getTelefone() {
        return telefone;
    }

    @Override
    public String toString() {
        return "Morador: " + nome + ", CPF: " + cpf + ", Apartamento: " + apartamento;
    }

    public abstract void exibirInformacoes();

    public String dados() {
        return "Nome: " + nome + "\nCPF: " + cpf + "\nApartamento: " + apartamento + "\nTelefone: " + telefone;
    }
}