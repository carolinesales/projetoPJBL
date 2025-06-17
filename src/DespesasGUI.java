package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Tela para gerenciar despesas do condomínio.
 */
public class DespesasGUI extends JFrame {
    private Condominio condominio;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField tipoField, valorField, dataField;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("pt", "BR"));

    /**
     * Cria a tela de gerenciamento de despesas.
     * @param condominio Instância do condomínio.
     */
    public DespesasGUI(Condominio condominio) {
        this.condominio = condominio;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gerenciar Despesas");
        setSize(600, 400);
        setMinimumSize(new Dimension(500, 350));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoField = new JTextField(15);
        JLabel valorLabel = new JLabel("Valor (R$):");
        valorField = new JTextField(15);
        JLabel dataLabel = new JLabel("Data (DD/MM/YYYY):");
        dataField = new JTextField(15);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        tipoLabel.setFont(labelFont);
        valorLabel.setFont(labelFont);
        dataLabel.setFont(labelFont);
        tipoField.setFont(fieldFont);
        valorField.setFont(fieldFont);
        dataField.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(tipoLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(tipoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(valorLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(valorField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(dataLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(dataField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton addButton = new JButton("Adicionar");
        JButton deleteButton = new JButton("Deletar");
        JButton saveCsvButton = new JButton("Salvar em CSV");
        JButton clearButton = new JButton("Limpar Campos"); // Novo botão adicionado
        JButton closeButton = new JButton("Fechar");

        styleButton(addButton);
        styleButton(deleteButton);
        styleButton(saveCsvButton);
        styleButton(clearButton); // Estilizar o novo botão
        styleButton(closeButton);

        addButton.addActionListener(e -> addDespesa());
        deleteButton.addActionListener(e -> deleteDespesa());
        saveCsvButton.addActionListener(e -> saveToCsv());
        clearButton.addActionListener(e -> clearFields()); // Ação para o novo botão
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveCsvButton);
        buttonPanel.add(clearButton); // Adicionar o novo botão ao painel
        buttonPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        String[] columns = {"Tipo", "Valor", "Data"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        updateTable();

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(panel);

        addInputValidation();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(button.getText().equals("Fechar") ? new Color(220, 220, 220) : new Color(0, 123, 255));
        button.setForeground(button.getText().equals("Fechar") ? Color.BLACK : Color.WHITE);
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
                button.setBackground(button.getText().equals("Fechar") ? new Color(220, 220, 220) : new Color(0, 123, 255));
            }
        });
    }

    private void addInputValidation() {
        dataField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = dataField.getText();
                if (!Character.isDigit(c) && c != '/' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
                if (text.length() >= 10 && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
                if (text.length() == 2 || text.length() == 5) {
                    if (c != '/' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                        dataField.setText(text + "/");
                    }
                }
            }
        });

        valorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
                String text = valorField.getText();
                if (text.contains(".") && c == '.') {
                    e.consume();
                }
            }
        });
    }

    // Novo método para limpar os campos de entrada
    private void clearFields() {
        tipoField.setText("");
        valorField.setText("");
        dataField.setText("");
    }

    private void addDespesa() {
        try {
            String tipo = tipoField.getText().trim();
            if (tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o tipo!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

 “

            String valorStr = valorField.getText().trim();
            if (valorStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o valor!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double valor;
            try {
                valor = Double.parseDouble(valorStr);
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(this, "Valor deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String dataStr = dataField.getText().trim();
            System.out.println("Data inserida: '" + dataStr + "' (tamanho: " + dataStr.length() + ")");
            System.out.print("Caracteres: ");
            for (char c : dataStr.toCharArray()) {
                System.out.print("[" + c + "(" + (int) c + ")] ");
            }
            System.out.println();
            if (!dataStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate data;
            try {
                data = LocalDate.parse(dataStr, DATE_FORMATTER);
                System.out.println("Data parseada: " + data);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025). Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Despesa despesa = new Despesa(tipo, valor, data);
            condominio.adicionarDespesa(despesa);
            updateTable();
            JOptionPane.showMessageDialog(this, "Despesa adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            tipoField.setText("");
            valorField.setText("");
            dataField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar despesa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDespesa() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma despesa na tabela!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipo = (String) tableModel.getValueAt(selectedRow, 0);
            double valor = ((Number) tableModel.getValueAt(selectedRow, 1)).doubleValue();
            String dataStr = (String) tableModel.getValueAt(selectedRow, 2);
            LocalDate data = LocalDate.parse(dataStr, DATE_FORMATTER);

            condominio.removerDespesa(tipo, valor, data);
            updateTable();
            JOptionPane.showMessageDialog(this, "Despesa deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            tipoField.setText("");
            valorField.setText("");
            dataField.setText("");
        } catch (CondominioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar despesa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToCsv() {
        try {
            condominio.salvarDespesasCSV();
            JOptionPane.showMessageDialog(this, "Despesas salvas com sucesso em despesas.csv!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar despesas.csv: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.err.println("Erro ao salvar despesas.csv: " + e.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Despesa despesa : condominio.getDespesas()) {
            tableModel.addRow(new Object[]{
                    despesa.getTipo(),
                    despesa.getValor(),
                    despesa.getData().format(DATE_FORMATTER)
            });
        }
    }
}