package src;

public class ReservaAreaComum extends Inquilino {

    public Inquilino(String nome, String apartamento, String telefone) {
        super(nome, apartamento, telefone);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Morador: " + nome + "Inquilino: " + nome + ", Apartamento: " + apartamento + ", Telefone: " + telefone);
    }
} 
