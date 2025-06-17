package src;

import java.time.LocalDate;

// define a classe Despesa que representa uma despesa do condomínio
public class Despesa {
    private final String tipo;
    private final double valor;
    private final LocalDate data;

    // Cria uma nova despesa
    public Despesa(String tipo, double valor, LocalDate data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    // Obtém o tipo da despesa
    public String getTipo() {
        return tipo;
    }

    // Obtém o valor da despesa
    public double getValor() {
        return valor;
    }

    // Obtém a data da despesa
    public LocalDate getData() {
        return data;
    }

    // Exibe as informações da despesa
    public void exibirInformacoes() {
        System.out.printf("Despesa: %s, Valor: R$%.2f, Data: %s%n", tipo, valor, data);
    }
}