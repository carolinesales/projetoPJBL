package src;

public class Funcionario extends Pessoa {
    private double salarioBase;

    public Funcionario(String nome, String cpf, String telefone, double salarioBase) {
        super(nome, cpf, telefone);
        this.salarioBase = salarioBase;
    }

    @Override
    public double calcularPagamento() {
        return salarioBase; // Exemplo: pagamento é o salário base
    }

    public void exibirInformacoes() {
        System.out.println("Funcionário: " + nome + ", CPF: " + cpf + ", Telefone: " + telefone + ", Salário: R$" + salarioBase);
    }
}