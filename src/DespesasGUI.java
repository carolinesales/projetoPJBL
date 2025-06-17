package src;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DespesasGUI extends JFrame {
    private Condominio condominio;
    private JTextField tipoField, valorField, dataField;
    private JTable tabela;
    private DefaultTableModel model;
    private JButton adicionarButton, removerButton, atualizarButton;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("pt", "BR"));

    public DespesasGUI(Condominio condominio) {
        this.condominio = condominio;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gerenciamento de Despesas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel tipoLabel = new JLabel("Tipo da Despesa:");
        tipoField = new JTextField(20);
        JLabel valorLabel = new JLabel("Valor (R$):");
        valorField = new JTextField(20);
        JLabel dataLabel = new JLabel("Data (DD/MM/YYYY):");
        dataField = new JTextField(20);

        inputPanel.add(tipoLabel);
        inputPanel.add(tipoField);
        inputPanel.add(valorLabel);
        inputPanel.add(valorField);
        inputPanel.add(dataLabel);
        inputPanel.add(dataField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        adicionarButton = new JButton("Adicionar Despesa");
        removerButton = new JButton("Remover Despesa");
        atualizarButton = new JButton("Atualizar Tabela");

        styleButton(adicionarButton);
        styleButton(removerButton);
        styleButton(atualizarButton);

        buttonPanel.add(adicionarButton);
        buttonPanel.add(removerButton);
        buttonPanel.add(atualizarButton);

        String[] colunas = {"Tipo", "Valor (R$)", "Data"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        adicionarButton.addActionListener(e -> adicionarDespesa());
        removerButton.addActionListener(e -> removerDespesa());
        atualizarButton.addActionListener(e -> atualizarTabela(model));

        atualizarTabela(model);

        getRootPane().registerKeyboardAction(
            e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    private void adicionarDespesa() {
        try {
            String tipo = tipoField.getText().trim();
            if (tipo.isEmpty()) {
                throw new CondominioException("Tipo da despesa não pode ser vazio.");
            }

            double valor;
            try {
                valor = Double.parseDouble(valorField.getText().trim());
                if (valor <= 0) {
                    throw new CondominioException("Valor deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                throw new CondominioException("Valor inválido.");
            }

            String dataStr = dataField.getText().trim();
            if (!dataStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new CondominioException("Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025).");
            }
            LocalDate data;
            try {
                data = LocalDate.parse(dataStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                throw new CondominioException("Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025).");
            }

            Condominio.Despesa despesa = condominio.new Despesa(tipo, valor, data);
            condominio.adicionarDespesa(despesa);
            condominio.salvarDespesasCSV();
            atualizarTabela(model);
            JOptionPane.showMessageDialog(this, "Despesa adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            tipoField.setText("");
            valorField.setText("");
            dataField.setText("");
        } catch (CondominioException | IOException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

private void removerDespesa() {
    int selectedRow = tabela.getSelectedRow();
    if (selectedRow >= 0) {
        Object[] options = {"Sim", "Não"};
        int confirm = JOptionPane.showOptionDialog(
            this,
            "Deseja remover a despesa selecionada?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]
        );
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String tipo = String.valueOf(model.getValueAt(selectedRow, 0));
                String valorStr = String.valueOf(model.getValueAt(selectedRow, 1));
                double valor = Double.parseDouble(valorStr.replace(",", "."));
                String dataStr = String.valueOf(model.getValueAt(selectedRow, 2));
                LocalDate data = LocalDate.parse(dataStr, DATE_FORMATTER);

                System.out.println("Tentando remover: tipo=" + tipo + ", valor=" + valor + ", data=" + dataStr);

                // Verificar se a despesa existe
                boolean exists = false;
                for (Condominio.Despesa d : condominio.getDespesas()) {
                    if (d.getTipo().equalsIgnoreCase(tipo) &&
                        Math.abs(d.getValor() - valor) < 0.01 &&
                        d.getData().equals(data)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    throw new CondominioException("Despesa não encontrada na lista interna.");
                }

                condominio.removerDespesa(tipo, valor, data);
                condominio.salvarDespesasCSV();
                atualizarTabela(model);
                JOptionPane.showMessageDialog(this, "Despesa removida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Erro: Valor inválido na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Erro: Data inválida na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | CondominioException e) {
                JOptionPane.showMessageDialog(this, "Erro ao remover a despesa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione uma despesa para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    private void atualizarTabela(DefaultTableModel model) {
        try {
            condominio.carregarDespesasCSV();
            model.setRowCount(0);
            for (Condominio.Despesa despesa : condominio.getDespesas()) {
                model.addRow(new Object[]{
                    despesa.getTipo(),
                    String.format("%.2f", despesa.getValor()),
                    despesa.getData().format(DATE_FORMATTER)
                });
            }
        } catch (IOException | CondominioException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tabela: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Condominio condominio = new Condominio();
            new DespesasGUI(condominio).setVisible(true);
        });
    }
}
