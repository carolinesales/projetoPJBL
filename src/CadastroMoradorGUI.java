package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CadastroMoradorGUI extends JFrame {
    private CadastroMorador cadastro;
    private JTextField nomeField, cpfField, apartamentoField, telefoneField, tipoField;

    public CadastroMoradorGUI(CadastroMorador cadastro) {
        this.cadastro = cadastro;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Cadastro de Morador");
        setSize(400, 350);
        setMinimumSize(new Dimension(350, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

       
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(labelFont);
        nomeField = new JTextField(20);
        nomeField.setFont(fieldFont);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(labelFont);
        cpfField = new JTextField(20);
        cpfField.setFont(fieldFont);

        JLabel apartamentoLabel = new JLabel("Apartamento:");
        apartamentoLabel.setFont(labelFont);
        apartamentoField = new JTextField(20);
        apartamentoField.setFont(fieldFont);

        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setFont(labelFont);
        telefoneField = new JTextField(20);
        telefoneField.setFont(fieldFont);

        JLabel tipoLabel = new JLabel("Tipo (P/I):");
        tipoLabel.setFont(labelFont);
        tipoField = new JTextField(20);
        tipoField.setFont(fieldFont);

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nomeLabel, gbc);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(cpfLabel, gbc);
        gbc.gridx = 1;
        panel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(apartamentoLabel, gbc);
        gbc.gridx = 1;
        panel.add(apartamentoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(telefoneLabel, gbc);
        gbc.gridx = 1;
        panel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(tipoLabel, gbc);
        gbc.gridx = 1;
        panel.add(tipoField, gbc);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton salvarButton = new JButton("Salvar");
        salvarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        salvarButton.setBackground(new Color(0, 123, 255));
        salvarButton.setForeground(Color.WHITE);
        salvarButton.setFocusPainted(false);
        salvarButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        salvarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelarButton.setBackground(new Color(220, 220, 220));
        cancelarButton.setForeground(Color.BLACK);
        cancelarButton.setFocusPainted(false);
        cancelarButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        cancelarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botões
        salvarButton.addActionListener(e -> salvarMorador());
        cancelarButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelarButton);
        buttonPanel.add(salvarButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        
        styleButton(salvarButton);
        styleButton(cancelarButton);

        addInputValidation();
    }

    private void styleButton(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.getText().equals("Salvar")) {
                    button.setBackground(new Color(0, 123, 255));
                } else {
                    button.setBackground(new Color(220, 220, 220));
                }
            }
        });
    }

    private void addInputValidation() {
        // validação de CPF
        cpfField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != '-') {
                    e.consume();
                }
            }
        });

        
        tipoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(c == 'P' || c == 'p' || c == 'I' || c == 'i') || tipoField.getText().length() >= 1) {
                    e.consume();
                }
            }
        });
    }

    private void salvarMorador() {
        try {
            // Validação dos campos
            if (nomeField.getText().trim().isEmpty() ||
                cpfField.getText().trim().isEmpty() ||
                apartamentoField.getText().trim().isEmpty() ||
                telefoneField.getText().trim().isEmpty() ||
                tipoField.getText().trim().isEmpty()) {
                throw new Exception("Todos os campos devem ser preenchidos.");
            }

            Morador morador = null;
            String tipo = tipoField.getText().toUpperCase();
            if (tipo.equals("P")) {
                morador = new Proprietario(nomeField.getText(), cpfField.getText(),
                        apartamentoField.getText(), telefoneField.getText());
            } else if (tipo.equals("I")) {
                morador = new Inquilino(nomeField.getText(), cpfField.getText(),
                        apartamentoField.getText(), telefoneField.getText());
            } else {
                throw new Exception("Tipo de morador inválido (use P ou I).");
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

            JOptionPane.showMessageDialog(this, "Morador cadastrado com sucesso!", 
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), 
                                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}