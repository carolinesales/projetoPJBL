package src;
public class Apartamento {
    private String numero; // identificador único do apartamento
    private Morador morador; // referência ao morador, inicialmente será nulo

    // construtor que define o número do apartamento
    public Apartamento(String numero) {
        this.numero = numero;
        this.morador = null; // sem morador ao criar
    }

    // associa um morador ao apartamento
    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    // retorna o número do apartamento
    public String getNumero() {
        return numero;
    }

    // retorna o morador ou null se vazio
    public Morador getMorador() {
        return morador;
    }

    // exibe dados do apartamento, verificando se há morador
    public void exibirInformacoes() {
        System.out.printf("Apartamento: %s, Morador: %s%n",
                numero, morador != null ? morador.getNome() : "Vazio");
    }
}