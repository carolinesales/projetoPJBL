package src;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastroReservas {
    private List<ReservaAreaComum> reservas;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("pt", "BR"));

    public CadastroReservas() {
        reservas = new ArrayList<>();
    }

    public void adicionarReserva(ReservaAreaComum reserva) throws CondominioException {
        if (reserva == null) throw new CondominioException("Reserva não pode ser nula.");
        for (ReservaAreaComum r : reservas) {
            if (r.getAreaComum().equals(reserva.getAreaComum()) &&
                r.getDataReserva().equals(reserva.getDataReserva()) &&
                r.getHoraReserva().equals(reserva.getHoraReserva())) {
                throw new CondominioException("Conflito de horário para a área " + reserva.getAreaComum());
            }
        }
        reservas.add(reserva);
    }

    public void cancelarReserva(String cpf, String data, String hora, String area) throws CondominioException {
        ReservaAreaComum reserva = reservas.stream()
                .filter(r -> r.getInquilino().getCpf().equals(cpf) &&
                             r.getDataReserva().equals(data) &&
                             r.getHoraReserva().equals(hora) &&
                             r.getAreaComum().equals(area))
                .findFirst()
                .orElseThrow(() -> new CondominioException("Reserva não encontrada."));
        reservas.remove(reserva);
    }

    public void listarReservas() {
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
        } else {
            for (ReservaAreaComum reserva : reservas) {
                reserva.exibirInformacoes();
            }
        }
    }

    public List<ReservaAreaComum> getReservas() {
        return reservas;
    }

    public void salvarReservasCSV() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservas.csv"))) {
            for (ReservaAreaComum reserva : reservas) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s%n",
                        reserva.getInquilino().getNome(),
                        reserva.getInquilino().getCpf(),
                        reserva.getInquilino().getApartamento(),
                        reserva.getInquilino().getTelefone(),
                        reserva.getDataReserva(),
                        reserva.getHoraReserva(),
                        reserva.getAreaComum()));
            }
        }
    }

    public void carregarReservasCSV() throws IOException, CondominioException {
        File file = new File("reservas.csv");
        if (!file.exists()) {
            System.out.println("reservas.csv não encontrado, iniciando com lista vazia.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    System.err.println("Linha " + lineNumber + " vazia em reservas.csv, ignorando.");
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 7) {
                    System.err.println("Linha " + lineNumber + " inválida em reservas.csv: '" + line + "'. Esperado: nome,cpf,apartamento,telefone,data,hora,area (7 colunas).");
                    continue;
                }
                try {
                    String nome = parts[0].trim();
                    String cpf = parts[1].trim();
                    String apartamento = parts[2].trim();
                    String telefone = parts[3].trim();
                    String data = parts[4].trim();
                    String hora = parts[5].trim();
                    String area = parts[6].trim();
                    ReservaAreaComum reserva = new ReservaAreaComum(nome, cpf, apartamento, telefone, data, hora, area);
                    reservas.add(reserva);
                } catch (CondominioException e) {
                    System.err.println("Linha " + lineNumber + " em reservas.csv: erro ao carregar reserva: " + e.getMessage());
                }
            }
        }
    }
}