package teste;

import src.Inquilino;

public class ReservaAreaComum {
    private Inquilino inquilino;
    private String dataReserva;
    private String horaReserva;
    private String areaComum;

    public ReservaAreaComum(String nome, String apartamento, String telefone) {
        this.inquilino = new Inquilino(nome, apartamento, telefone);
    }

    public void exibirInformacoes() {
        System.out.println("Reserva feita por:");
        inquilino.exibirInformacoes();
    }
}
