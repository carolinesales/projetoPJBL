package src;

public class Inquilino extends Morador {
    public Inquilino(String nome, String cpf, String apartamento, String telefone) {
        super(nome, cpf, apartamento, telefone);
    }

    @Override
    public void exibirInformacoes() {
        System.out.printf("Inquilino: %s, CPF: %s, Apartamento: %s, Telefone: %s%n",
                getNome(), getCpf(), getApartamento(), getTelefone());
    }

    @Override
    public double calcularPagamento() {
        return 600.0;
    }
}