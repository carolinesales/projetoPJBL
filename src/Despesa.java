package src;

import java.time.LocalDate;

public class Despesa {
    private String tipo;
    private double valor;
    private LocalDate data;

    public Despesa(String tipo, double valor, LocalDate data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void exibirInformacoes() {
        System.out.println("Despesa: " + tipo + ", Valor: R$" + valor + ", Data: " + data);
    }
}