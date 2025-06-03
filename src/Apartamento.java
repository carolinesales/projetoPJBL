package src;

public class Apartamento {
    private String numero;
    private Morador morador;

    public Apartamento(String numero) {
        this.numero = numero;
        this.morador = null; // Inicialmente sem morador
    }

    public String getNumero() {
        return numero;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public void exibirInformacoes() {
        System.out.println("Apartamento: " + numero);
        if (morador != null) {
            morador.exibirInformacoes();
        } else {
            System.out.println("Nenhum morador cadastrado neste apartamento.");
        }
    }
}