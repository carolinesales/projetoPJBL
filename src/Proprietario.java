package src;

public class Proprietario extends Morador {
    public Proprietario(String nome, String cpf, String apartamento, String telefone) {
        super(nome, cpf, apartamento, telefone);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Proprietário: " + nome + ", Apartamento: " + apartamento + ", Telefone: " + telefone + ", CPF: " + cpf);
    }

    @Override
    public double calcularPagamento() {
        return 500.0; // Valor fixo para o pagamento do proprietário
    }
}
