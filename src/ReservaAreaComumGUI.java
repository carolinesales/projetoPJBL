package src;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

//Tela para cadastro de reservas de áreas comuns

public class ReservaAreaComumGUI extends JFrame {
    private final CadastroReservas cadastroReservas;
    private JTextField nomeField, cpfField, apartamentoField, telefoneField, dataField, horaField;
    private JComboBox<String> areaComboBox;

    //Cria a tela de cadastro de reservas

    public ReservaAreaComumGUI(CadastroReservas cadastroReservas, CadastroMorador cadastroMorador) {
        this.cadastroReservas = cadastroReservas;
        initializeUI();
    }
    //Inicializa a interface gráfica
    //Define o título, tamanho, localização e comportamento de fechamento da janela
    private void initializeUI() {
        setTitle("Cadastrar Reserva de Área Comum");
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Cria um painel com layout GridBagLayout para organizar os componentes
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // fontes para os rótulos e campos de texto
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // cria os campos de entrada 
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

        // campo para área comum
        JLabel areaLabel = new JLabel("Área Comum:");
        areaLabel.setFont(labelFont);
        String[] areas = {"Salão de Festas", "Piscina", "Churrasqueira", "Academia"};
        areaComboBox = new JComboBox<>(areas);
        areaComboBox.setFont(fieldFont);

        // campo para data
        JLabel dataLabel = new JLabel("Data (DD/MM/AAAA):");
        dataLabel.setFont(labelFont);
        dataField = new JTextField(20);
        dataField.setFont(fieldFont);

        // campo para hora
        JLabel horaLabel = new JLabel("Hora (HH:MM):");
        horaLabel.setFont(labelFont);
        horaField = new JTextField(20);
        horaField.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nomeLabel, gbc);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(cpfLabel, gbc);
        gbc.gridx = 1;
        panel.add(cpfField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(apartamentoLabel, gbc);
        gbc.gridx = 1;
        panel.add(apartamentoField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(telefoneLabel, gbc);
        gbc.gridx = 1;
        panel.add(telefoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(areaLabel, gbc);
        gbc.gridx = 1;
        panel.add(areaComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(dataLabel, gbc);
        gbc.gridx = 1;
        panel.add(dataField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(horaLabel, gbc);
        gbc.gridx = 1;
        panel.add(horaField, gbc);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        styleButton(salvarButton);
        styleButton(cancelarButton);

        salvarButton.addActionListener(e -> salvarReserva());
        cancelarButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelarButton);
        buttonPanel.add(salvarButton);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        addInputValidation();
    }

    // estilização 
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

    // adiciona validação de entrada para os campos de CPF e telefone
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

    // metodo para salvar a reserva
    private void salvarReserva() {
        try {
            if (nomeField.getText().trim().isEmpty() ||
                cpfField.getText().trim().isEmpty() ||
                apartamentoField.getText().trim().isEmpty() ||
                telefoneField.getText().trim().isEmpty() ||
                dataField.getText().trim().isEmpty() ||
                horaField.getText().trim().isEmpty()) {
                throw new CondominioException("Todos os campos obrigatórios devem ser preenchidos.");
            }
            if (!cpfField.getText().matches("\\d{11}")) {
                throw new CondominioException("CPF deve conter 11 dígitos numéricos.");
            }
            if (!telefoneField.getText().matches("\\d{10,11}")) {
                throw new CondominioException("Telefone deve conter 10 ou 11 dígitos numéricos.");
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(dataField.getText());
            if (!horaField.getText().matches("\\d{2}:\\d{2}")) {
                throw new CondominioException("Hora deve estar no formato HH:MM");
            }
            String area = (String) areaComboBox.getSelectedItem();
            ReservaAreaComum reserva = new ReservaAreaComum(
                nomeField.getText(), cpfField.getText(), apartamentoField.getText(),
                telefoneField.getText(), dataField.getText(), horaField.getText(), area);
            cadastroReservas.adicionarReserva(reserva);
            JOptionPane.showMessageDialog(this, "Reserva cadastrada com sucesso!", 
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato DD/MM/AAAA", 
                                        "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (CondominioException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}