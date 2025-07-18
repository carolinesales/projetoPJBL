package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// tela para cadastro de moradores
public class CadastroMoradorGUI extends JFrame {
    private CadastroMorador cadastro;
    private JTextField nomeField, cpfField, apartamentoField, telefoneField;
    private JComboBox<String> tipoComboBox;

    // cria a tela de cadastro de moradores
    public CadastroMoradorGUI(CadastroMorador cadastro) {
        this.cadastro = cadastro;
        initializeUI();
    }

    // inicializa a interface gráfica
    private void initializeUI() {
        setTitle("Cadastro de Morador");
        setSize(400, 400);
        setMinimumSize(new Dimension(350, 350));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // cria um painel com layout GridBagLayout para organizar os componentes
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // fontes para os rótulos e campos de texto
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // campo para nome
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(labelFont);
        nomeField = new JTextField(20);
        nomeField.setFont(fieldFont);

        // campo para CPF
        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(labelFont);
        cpfField = new JTextField(20);
        cpfField.setFont(fieldFont);

        // campo para apartamento
        JLabel apartamentoLabel = new JLabel("Apartamento:");
        apartamentoLabel.setFont(labelFont);
        apartamentoField = new JTextField(20);
        apartamentoField.setFont(fieldFont);

        // campo para telefone
        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setFont(labelFont);
        telefoneField = new JTextField(20);
        telefoneField.setFont(fieldFont);

        // campo para tipo de morador (inquilino ou proprietário)
        JLabel tipoLabel = new JLabel("Tipo:");
        tipoLabel.setFont(labelFont);
        tipoComboBox = new JComboBox<>(new String[]{"Inquilino", "Proprietário"});
        tipoComboBox.setFont(fieldFont);

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
        panel.add(tipoComboBox, gbc);

        // painel para os botões Salvar e Cancelar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        styleButton(salvarButton);
        styleButton(cancelarButton);

        salvarButton.addActionListener(e -> salvarMorador());
        cancelarButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelarButton);
        buttonPanel.add(salvarButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        addInputValidation();
    }

    // estilização dos botões

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(button.getText().equals("Salvar") ? new Color(0, 123, 255) : new Color(220, 220, 220));
        button.setForeground(button.getText().equals("Salvar") ? Color.WHITE : Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(button.getText().equals("Salvar") ? new Color(0, 123, 255) : new Color(220, 220, 220));
            }
        });
    }

    // validação de entrada para CPF e telefone
    private void addInputValidation() {
        cpfField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) e.consume();
            }
        });

        telefoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) e.consume();
            }
        });
    }

    // método para salvar o morador
    private void salvarMorador() {
        try {
            if (nomeField.getText().trim().isEmpty() ||
                cpfField.getText().trim().isEmpty() ||
                apartamentoField.getText().trim().isEmpty() ||
                telefoneField.getText().trim().isEmpty()) {
                throw new CondominioException("Todos os campos obrigatórios devem ser preenchidos.");
            }
            if (!cpfField.getText().matches("\\d{11}")) {
                throw new CondominioException("CPF deve conter 11 dígitos numéricos.");
            }
            if (!telefoneField.getText().matches("\\d{10,11}")) {
                throw new CondominioException("Telefone deve conter 10 ou 11 dígitos numéricos.");
            }
            Morador morador = tipoComboBox.getSelectedItem().equals("Inquilino")
                    ? new Inquilino(nomeField.getText(), cpfField.getText(),
                                    apartamentoField.getText(), telefoneField.getText())
                    : new Proprietario(nomeField.getText(), cpfField.getText(),
                                       apartamentoField.getText(), telefoneField.getText());
            cadastro.getMoradores().add(morador);
            Apartamento apt = new Apartamento(apartamentoField.getText());
            apt.setMorador(morador);
            cadastro.getApartamentos().add(apt);
            JOptionPane.showMessageDialog(this, "Morador cadastrado com sucesso!", 
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (CondominioException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}