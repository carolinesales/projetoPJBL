package src;

public class ReservaAreaComum {
    private Inquilino inquilino;
    private String dataReserva;
    private String horaReserva;
    private String areaComum;

    public ReservaAreaComum(String nome, String cpf, String apartamento, String telefone,
                            String dataReserva, String horaReserva, String areaComum) {
        this.inquilino = new Inquilino(nome, cpf, apartamento, telefone);
        this.dataReserva = dataReserva;
        this.horaReserva = horaReserva;
        this.areaComum = areaComum;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public String getDataReserva() {
        return dataReserva;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public String getAreaComum() {
        return areaComum;
    }

    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public void setAreaComum(String areaComum) {
        this.areaComum = areaComum;
    }

    public void exibirInformacoes() {
        System.out.println("Reserva de Área Comum:");
        System.out.println("Área: " + areaComum);
        System.out.println("Data: " + dataReserva);
        System.out.println("Hora: " + horaReserva);
        System.out.println("Inquilino:");
        inquilino.exibirInformacoes();
    }
}