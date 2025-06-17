package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservaAreaComum {
    private Inquilino inquilino;
    private String areaComum;
    private String dataReserva;
    private String horaReserva;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");

    public ReservaAreaComum(String nome, String cpf, String apartamento, String telefone,
                            String dataReserva, String horaReserva, String areaComum) throws CondominioException {
        this.inquilino = new Inquilino(nome, cpf, apartamento, telefone);
        this.areaComum = validarAreaComum(areaComum);
        this.dataReserva = validarData(dataReserva);
        this.horaReserva = validarHora(horaReserva);
    }

    private String validarAreaComum(String area) throws CondominioException {
        if (area == null || area.trim().isEmpty()) {
            throw new CondominioException("Área comum não pode ser nula ou vazia.");
        }
        return area.trim();
    }

    private String validarData(String data) throws CondominioException {
        if (data == null || !data.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new CondominioException("Data deve estar no formato DD/MM/YYYY.");
        }
        try {
            LocalDate.parse(data, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new CondominioException("Data inválida: " + e.getMessage());
        }
        return data;
    }

    private String validarHora(String hora) throws CondominioException {
        if (hora == null || !hora.matches("\\d{2}:\\d{2}")) {
            throw new CondominioException("Hora deve estar no formato HH:MM.");
        }
        return hora;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public String getAreaComum() {
        return areaComum;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void exibirInformacoes() {
        System.out.printf("Reserva: %s, Inquilino: %s, CPF: %s, Data: %s, Hora: %s%n",
                areaComum, inquilino.getNome(), inquilino.getCpf(), dataReserva, horaReserva);
    }
}