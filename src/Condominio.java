package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Condominio {
    private List<Apartamento> apartamentos;
    private List<Despesa> despesas;

    public Condominio() {
        apartamentos = new ArrayList<>();
        despesas = new ArrayList<>();
    }

    public void adicionarApartamento(Apartamento apartamento) {
        apartamentos.add(apartamento);
    }

    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }

    public void listarApartamentos() {
        if (apartamentos.isEmpty()) {
            System.out.println("Nenhum apartamento cadastrado.");
        } else {
            for (Apartamento apt : apartamentos) {
                apt.exibirInformacoes();
            }
        }
    }

    public void listarDespesas() {
        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa cadastrada.");
        } else {
            for (Despesa despesa : despesas) {
                despesa.exibirInformacoes();
            }
        }
    }

    public double calcularTotalDespesasMes(int mes, int ano) {
        return despesas.stream()
                .filter(d -> d.getData().getMonthValue() == mes && d.getData().getYear() == ano)
                .mapToDouble(Despesa::getValor)
                .sum();
    }

    public List<Apartamento> getApartamentos() {
        return apartamentos;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }
}