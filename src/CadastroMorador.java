package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroMorador implements Serializable {
    private List<Morador> moradores;
    private List<Apartamento> apartamentos;

    public CadastroMorador() {
        moradores = new ArrayList<>();
        apartamentos = new ArrayList<>();
    }

    //  Método para cadastrar manualmente
    public void cadastrarMorador() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do morador: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF do morador: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o número do apartamento: ");
        String numeroApartamento = scanner.nextLine();

        System.out.print("Digite o telefone do morador: ");
        String telefone = scanner.nextLine();

        System.out.print("O morador é proprietário ou inquilino? (P/I): ");
        String tipo = scanner.nextLine();

        Morador morador = criarMorador(nome, cpf, numeroApartamento, telefone, tipo);

        if (morador != null) {
            adicionarMoradorAoSistema(morador);
            System.out.println("Morador cadastrado com sucesso!");
        } else {
            System.out.println("Tipo de morador inválido.");
        }
    }

    //  Carregar moradores de um CSV
    public void carregarMoradoresDeCSV(String caminhoArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length != 5) {
                    System.out.println("Linha inválida (esperado: nome,cpf,apartamento,tipo,telefone): " + linha);
                    continue;
                }

                String nome = partes[0];
                String cpf = partes[1];
                String apartamento = partes[2];
                String tipo = partes[3];
                String telefone = partes[4];

                Morador morador = criarMorador(nome, cpf, apartamento, telefone, tipo);

                if (morador != null) {
                    adicionarMoradorAoSistema(morador);
                }
            }
            System.out.println("Moradores carregados com sucesso do arquivo!");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }

    //  Salvar moradores em um arquivo .ser
    public void salvarMoradores(String caminhoArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(moradores);
            System.out.println("Moradores salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar moradores: " + e.getMessage());
        }
}
    @SuppressWarnings("unchecked")
    public void carregarMoradoresSalvos(String caminhoArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            moradores = (List<Morador>) ois.readObject();

           
            apartamentos.clear();
            for (Morador morador : moradores) {
                Apartamento apt = new Apartamento(morador.getApartamento()); 
                apt.setMorador(morador); 
                apartamentos.add(apt);
            }

            System.out.println("Moradores carregados com sucesso do arquivo!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar moradores: " + e.getMessage());
        }
    }


    private Morador criarMorador(String nome, String cpf, String apartamento, String telefone, String tipo) {
        if (tipo.equalsIgnoreCase("P")) {
            return new Proprietario(nome, cpf, apartamento, telefone);
        } else if (tipo.equalsIgnoreCase("I")) {
            return new Inquilino(nome, cpf, apartamento, telefone);
        } else {
            return null;
        }
    }

    private void adicionarMoradorAoSistema(Morador morador) {
        moradores.add(morador);

        Apartamento apt = apartamentos.stream()
                .filter(a -> a.getNumero().equals(morador.getApartamento()))
                .findFirst()
                .orElse(new Apartamento(morador.getApartamento()));

        apt.setMorador(morador);

        if (!apartamentos.contains(apt)) {
            apartamentos.add(apt);
        }
    }

    public void exibirMoradores() {
        if (moradores.isEmpty()) {
            System.out.println("Nenhum morador cadastrado.");
        } else {
            for (Morador morador : moradores) {
                morador.exibirInformacoes();
            }
        }
    }

    public void exibirApartamentos() {
        if (apartamentos.isEmpty()) {
            System.out.println("Nenhum apartamento cadastrado.");
        } else {
            for (Apartamento apartamento : apartamentos) {
                apartamento.exibirInformacoes();
            }
        }
    }

    public List<Morador> getMoradores() {
        return moradores;
    }

    public List<Apartamento> getApartamentos() {
        return apartamentos;
    }
}
