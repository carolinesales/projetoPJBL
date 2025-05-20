package src;

public abstract class Morador {
    protected String nome;
    protected String apartamento;
    protected String telefone;

    public Morador(String nome, String apartamento, String telefone) {
        this.nome = nome;
        this.apartamento = apartamento;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getApartamento() {
        return apartamento;
    }

    public String getTelefone() {
        return telefone;
    }

    public abstract void exibirInformacoes();

    public String dados() {
        return "Nome: " + nome + "\nApartamento: " + apartamento + "\nTelefone: " + telefone;
    }
}