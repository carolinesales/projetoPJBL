package src;

import java.time.LocalDate;

// define a classe Despesa que representa uma despesa do condomínio
public class Despesa {
    private final String tipo;
    private final double valor;
    private final LocalDate data;

    // Cria uma nova despesa
    public Despesa(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = LocalDate.now();
    }

    // Obtém o tipo da despesa
    public String getTipoDespesa() {
        return tipo;
    }

    // Obtém o valor da despesa
    public double getValorDespesa() {
        return valor;
    }

    // Obtém a data da despesa
    public LocalDate getDataDespesa() {
        return data;
    }

    // Exibe as informações da despesa
    public void exibirInformações() {
        System.out.printf("Despesa: %s,%.2f,%s%n", tipo, valor, data);
    }

    // Retorna uma representação em string da despesa
    @Override
    public String toString() {
        return String.format("Despesa[tipo=%s, valor=%.2f, data=%s]", tipo, valor, data);
    }
}