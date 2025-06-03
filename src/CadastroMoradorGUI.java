package src;

import javax.swing.*;
import java.awt.*;

public class CadastroMoradorGUI extends JFrame {
    private CadastroMorador cadastro;

    public CadastroMoradorGUI(CadastroMorador cadastro) {
        this.cadastro = cadastro;
        setTitle("Cadastrar Morador");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();
        JLabel cpfLabel = new JLabel("CPF:");
        JTextField cpfField = new JTextField();
        JLabel apartamentoLabel = new JLabel("Apartamento:");
        JTextField apartamentoField = new JTextField();
        JLabel telefoneLabel = new JLabel("Telefone:");
        JTextField telefoneField = new JTextField();
        JLabel tipoLabel = new JLabel("Tipo (P/I):");
        JTextField tipoField = new JTextField();

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> {
            try {
                Morador morador = null;
                String tipo = tipoField.getText();
                if (tipo.equalsIgnoreCase("P")) {
                    morador = new Proprietario(nomeField.getText(), cpfField.getText(),
                            apartamentoField.getText(), telefoneField.getText());
                } else if (tipo.equalsIgnoreCase("I")) {
                    morador = new Inquilino(nomeField.getText(), cpfField.getText(),
                            apartamentoField.getText(), telefoneField.getText());
                } else {
                    throw new Exception("Tipo de morador inválido.");
                }
                cadastro.getMoradores().add(morador);
                Apartamento apt = cadastro.getApartamentos().stream()
                        .filter(a -> a.getNumero().equals(apartamentoField.getText()))
                        .findFirst()
                        .orElse(new Apartamento(apartamentoField.getText()));
                apt.setMorador(morador);
                if (!cadastro.getApartamentos().contains(apt)) {
                    cadastro.getApartamentos().add(apt);
                }
                JOptionPane.showMessageDialog(this, "Morador cadastrado com sucesso!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(nomeLabel);
        add(nomeField);
        add(cpfLabel);
        add(cpfField);
        add(apartamentoLabel);
        add(apartamentoField);
        add(telefoneLabel);
        add(telefoneField);
        add(tipoLabel);
        add(tipoField);
        add(new JLabel());
        add(salvarButton);
    }
}