package src;

public class Proprietario extends Morador {

    public Proprietario(String nome, String apartamento, String telefone) {
        super(nome, apartamento, telefone);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Proprietário: " + nome + ", Apartamento: " + apartamento + ", Telefone: " + telefone);
    }
}
