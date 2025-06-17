package src;

import java.io.*;
import java.util.*;

public class CadastroMorador {
    private List<Morador> moradores;
    private List<Apartamento> apartamentos;

    public CadastroMorador() {
        moradores = new ArrayList<>();
        apartamentos = new ArrayList<>();
    }

    public void cadastrarMorador(Morador morador) throws CondominioException {
        if (morador == null) {
            throw new CondominioException("Morador não pode ser nulo.");
        }
        for (Morador m : moradores) {
            if (m.getCpf().equals(morador.getCpf())) {
                throw new CondominioException("CPF já registrado.");
            }
        }
        for (Apartamento apt : apartamentos) {
            if (apt.getNumero().equals(morador.getApartamento()) && apt.getMorador() != null) {
                throw new CondominioException("Apartamento já está ocupado.");
            }
        }
        moradores.add(morador);
        Apartamento apt = new Apartamento(morador.getApartamento());
        apt.setMorador(morador);
        apartamentos.add(apt);
    }

    public void cadastrarMorador() throws IOException, CondominioException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Nome: ");
        String nomeStr = reader.readLine();

        System.out.print("CPF (11 dígitos): ");
        String cpfStr = reader.readLine();

        System.out.print("Apartamento: ");
        String apartamentoStr = reader.readLine();

        System.out.print("Telefone (10-11 dígitos): ");
        String telefoneStr = reader.readLine();

        System.out.print("Tipo (1 - Inquilino, 2 - Proprietário): ");
        String tipoStr = reader.readLine();

        Morador morador;
        if (tipoStr.equals("1")) {
            morador = new Inquilino(nomeStr, cpfStr, apartamentoStr, telefoneStr);
        } else if (tipoStr.equals("2")) {
            morador = new Proprietario(nomeStr, cpfStr, apartamentoStr, telefoneStr);
        } else {
            throw new CondominioException("Tipo de morador inválido.");
        }

        cadastrarMorador(morador);
        System.out.println("Morador cadastrado com sucesso!");
    }

    public void exibirMoradores() {
        if (moradores.isEmpty()) {
            System.out.println("Não há moradores cadastrados.");
            return;
        }
        for (Morador morador : moradores) {
            morador.exibirInformacoes();
        }
    }

    public void carregarMoradoresSalvos(String arquivo) {
        File file = new File(arquivo);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            moradores = (List<Morador>) ois.readObject();
            apartamentos.clear();
            for (Morador morador : moradores) {
                Apartamento apt = new Apartamento(morador.getApartamento());
                apt.setMorador(morador);
                apartamentos.add(apt);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar moradores: " + e.getMessage());
        }
    }

    public void salvarMoradores(String arquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(moradores);
        } catch (IOException e) {
            System.out.println("Erro ao salvar moradores: " + e.getMessage());
        }
    }

    public List<Morador> getMoradores() { return moradores; }
    public List<Apartamento> getApartamentos() { return apartamentos; }
}