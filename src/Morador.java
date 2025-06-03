package src;

public abstract class Morador {
    protected String nome;
    protected String apartamento;
    protected String telefone;
    protected String cpf; 

    public Morador(String nome, String cpf, String apartamento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
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

    public String getCpf() {
        return cpf;
    }

    public abstract void exibirInformacoes();
    public abstract double calcularPagamento();

    public String dados() {
        return "Nome: " + nome + "\nCPF: " + cpf + "\nApartamento: " + apartamento + "\nTelefone: " + telefone;
    }
}