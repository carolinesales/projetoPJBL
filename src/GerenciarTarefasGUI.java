package src;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Tela para gerenciar tarefas de um funcionário.
 */
public class GerenciarTarefasGUI extends JFrame {
    private Condominio condominio;
    private Funcionario funcionario;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField descricaoField, dataField, statusField;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy", new Locale("pt", "BR"));

    /**
     * Cria a tela de gerenciamento de tarefas.
     * @param condominio Instância do condomínio.
     * @param funcionario Funcionário cujas tarefas serão gerenciadas.
     */
    public GerenciarTarefasGUI(Condominio condominio, Funcionario funcionario) {
        this.condominio = condominio;
        this.funcionario = funcionario;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gerenciar Tarefas - " + funcionario.getNome());
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

        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoField = new JTextField(15);
        JLabel dataLabel = new JLabel("Data (DD/MM/YYYY):");
        dataField = new JTextField(15);
        JLabel statusLabel = new JLabel("Status:");
        statusField = new JTextField(15);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        descricaoLabel.setFont(labelFont);
        dataLabel.setFont(labelFont);
        statusLabel.setFont(labelFont);
        descricaoField.setFont(fieldFont);
        dataField.setFont(fieldFont);
        statusField.setFont(fieldFont);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(descricaoLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(descricaoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(dataLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(dataField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(statusLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(statusField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton addButton = new JButton("Adicionar");
        JButton updateButton = new JButton("Atualizar");
        JButton deleteButton = new JButton("Deletar");
        JButton closeButton = new JButton("Fechar");

        styleButton(addButton);
        styleButton(updateButton);
        styleButton(deleteButton);
        styleButton(closeButton);

        addButton.addActionListener(e -> addTarefa());
        updateButton.addActionListener(e -> updateTarefa());
        deleteButton.addActionListener(e -> deleteTarefa());
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        String[] columns = {"Descrição", "Data", "Status"};
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
                // Forçar formato DD/MM/YYYY
                if (text.length() == 2 || text.length() == 5) {
                    if (c != '/' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                        dataField.setText(text + "/");
                    }
                }
            }
        });
    }

    private void addTarefa() {
        try {
            String descricao = descricaoField.getText().trim();
            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite a descrição!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String dataStr = dataField.getText().trim();
            // Debug: Exibir entrada exata e cada caractere
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
            LocalDate data = LocalDate.parse(dataStr, DATE_FORMATTER);
            System.out.println("Data parseada: " + data);

            Tarefa tarefa = new Tarefa(descricao, data, funcionario);
            condominio.adicionarTarefa(tarefa);
            updateTable();
            JOptionPane.showMessageDialog(this, "Tarefa adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            descricaoField.setText("");
            dataField.setText("");
            statusField.setText("");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use DD/MM/YYYY (ex.: 25/06/2025 ou 5/6/2025). Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar tarefa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTarefa() {
        try {
            String descricao = descricaoField.getText().trim();
            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite a descrição!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String status = statusField.getText().trim();
            if (status.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o status!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Tarefa tarefa = null;
            for (Tarefa t : condominio.getTarefas()) {
                if (t.getDescricao().equals(descricao) && t.getFuncionario().equals(funcionario)) {
                    tarefa = t;
                    break;
                }
            }

            if (tarefa == null) {
                JOptionPane.showMessageDialog(this, "Tarefa não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tarefa.setStatus(status);
            updateTable();
            JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            descricaoField.setText("");
            dataField.setText("");
            statusField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tarefa!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTarefa() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma tarefa na tabela!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String descricao = (String) tableModel.getValueAt(selectedRow, 0);
            condominio.removerTarefa(descricao, funcionario.getCpf());
            updateTable();
            JOptionPane.showMessageDialog(this, "Tarefa deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            descricaoField.setText("");
            dataField.setText("");
            statusField.setText("");
        } catch (CondominioException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar tarefa!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Tarefa tarefa : condominio.getTarefas()) {
            if (tarefa.getFuncionario().equals(funcionario)) {
                tableModel.addRow(new Object[]{
                        tarefa.getDescricao(),
                        tarefa.getData().format(DATE_FORMATTER),
                        tarefa.getStatus()
                });
            }
        }
    }
}