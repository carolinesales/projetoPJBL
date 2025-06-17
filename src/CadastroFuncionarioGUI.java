package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Tela para cadastro de funcionários
public class CadastroFuncionarioGUI extends JFrame { 
    private Condominio condominio; 
    private JTextField nomeField, cpfField, telefoneField, salarioField; 
    private JComboBox<String> cargoComboBox; 

    // Cria a tela de cadastro de funcionários
    public CadastroFuncionarioGUI(Condominio condominio) {
        this.condominio = condominio;
        initializeUI();
    }

    // Inicializa a interface gráfica
    private void initializeUI() {
        setTitle("Cadastro de Funcionário"); 
        setSize(400, 400); 
        setMinimumSize(new Dimension(350, 350)); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        // Cria um painel com layout GridBagLayout para organizar os componentes
        JPanel panel = new JPanel(new GridBagLayout()); 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        panel.setBackground(new Color(245, 245, 245)); 
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        //fontes para os rótulos e campos de texto
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // campo para nome
        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(labelFont);
        nomeField = new JTextField(20);
        nomeField.setFont(fieldFont);

        //  Campo para CPF
        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setFont(labelFont);
        cpfField = new JTextField(20);
        cpfField.setFont(fieldFont);

        // campo para telefone
        JLabel telefoneLabel = new JLabel("Telefone:");
        telefoneLabel.setFont(labelFont);
        telefoneField = new JTextField(20);
        telefoneField.setFont(fieldFont);

        //  campo para cargo
        JLabel cargoLabel = new JLabel("Cargo:");
        cargoLabel.setFont(labelFont);
        String[] cargos = {"Zelador", "Porteiro", "Jardineiro", "Limpeza", "Administrativo"};
        cargoComboBox = new JComboBox<>(cargos);
        cargoComboBox.setFont(fieldFont);

        //  campo para salário base 
        JLabel salarioLabel = new JLabel("Salário Base:");
        salarioLabel.setFont(labelFont);
        salarioField = new JTextField(20);
        salarioField.setFont(fieldFont);

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
        panel.add(telefoneLabel, gbc);
        gbc.gridx = 1;
        panel.add(telefoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(cargoLabel, gbc);
        gbc.gridx = 1;
        panel.add(cargoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(salarioLabel, gbc);
        gbc.gridx = 1;
        panel.add(salarioField, gbc);

        // Criação do painel de botões para salvar e cancelar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        styleButton(salvarButton);
        styleButton(cancelarButton);

        salvarButton.addActionListener(e -> salvarFuncionario());
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

    // Método para estilizar os botões
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

    // Adiciona validação de entrada para os campos de texto
    private void addInputValidation() {
        cpfField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) e.consume();
            }
        });

        // Validação para o campo CPF, permitindo apenas dígitos
        salarioField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') e.consume();
            }
        });

        // Validação para o campo salário, permitindo apenas dígitos e ponto decimal
        telefoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) e.consume();
            }
        });
    }

    // Método para salvar o funcionário
    private void salvarFuncionario() { 
        try { 
            if (nomeField.getText().trim().isEmpty() ||
                cpfField.getText().trim().isEmpty() ||
                telefoneField.getText().trim().isEmpty() ||
                salarioField.getText().trim().isEmpty()) {
                throw new CondominioException("Todos os campos obrigatórios devem ser preenchidos.");
            }
            if (!cpfField.getText().matches("\\d{11}")) {
                throw new CondominioException("CPF deve conter 11 dígitos numéricos.");
            }
            if (!telefoneField.getText().matches("\\d{10,11}")) {
                throw new CondominioException("Telefone deve conter 10 ou 11 dígitos numéricos.");
            }
            double salarioBase = Double.parseDouble(salarioField.getText());
            String cargo = (String) cargoComboBox.getSelectedItem();
            Funcionario funcionario = new Funcionario(
                nomeField.getText(), cpfField.getText(), telefoneField.getText(), cargo, salarioBase);
            condominio.adicionarFuncionario(funcionario);
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!", 
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (CondominioException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}