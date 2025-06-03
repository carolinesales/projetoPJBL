package src;

public class Inquilino extends Morador {
    public Inquilino(String nome, String cpf, String apartamento, String telefone) {
        super(nome, cpf, apartamento, telefone);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Inquilino: " + nome + ", Apartamento: " + apartamento + ", Telefone: " + telefone + ", CPF: " + cpf);
    }

    @Override
    public double calcularPagamento() {
        return 600.0; // Taxa diferente para inquilino (exemplo)
    }
}