package src;

public class Inquilino extends Morador {

    public Inquilino(String nome, String apartamento, String telefone) {
        super(nome, apartamento, telefone);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Inquilino: " + nome + ", Apartamento: " + apartamento + ", Telefone: " + telefone);
    }
}
