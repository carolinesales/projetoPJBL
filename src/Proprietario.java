package src;

import java.io.Serializable;

public class Proprietario extends Morador {
    public Proprietario(String nome, String cpf, String apartamento, String telefone) {
        super(nome, cpf, apartamento, telefone);
    }

    @Override
    public void exibirInformacoes() {
        System.out.printf("Proprietário: %s, CPF: %s, Apartamento: %s, Telefone: %s%n",
                getNome(), getCpf(), getApartamento(), getTelefone());
    }

    @Override
    public double calcularPagamento() {
        return 500.0;
    }
}