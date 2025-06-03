package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public void salvarDespesasCSV() throws IOException, CondominioException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("despesas.csv"))) {
            for (Despesa despesa : despesas) {
                writer.write(despesa.getTipo() + "," + despesa.getValor() + "," + despesa.getData());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new CondominioException("Erro ao salvar arquivo de despesas: " + e.getMessage());
        }
    }

    public void carregarDespesasCSV() throws IOException, CondominioException {
        despesas.clear(); // Limpa a lista para evitar duplicatas
        try (BufferedReader reader = new BufferedReader(new FileReader("despesas.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 3) {
                    throw new CondominioException("Formato inválido no arquivo de despesas.");
                }
                String tipo = partes[0];
                double valor = Double.parseDouble(partes[1]);
                LocalDate data = LocalDate.parse(partes[2]);
                despesas.add(new Despesa(tipo, valor, data));
            }
        } catch (IOException | NumberFormatException | java.time.format.DateTimeParseException e) {
            throw new CondominioException("Erro ao carregar arquivo de despesas: " + e.getMessage());
        }
    }
}