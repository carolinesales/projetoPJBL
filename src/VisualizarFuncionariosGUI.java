package src;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Tela para visualização de funcionários do condomínio
public class VisualizarFuncionariosGUI extends JFrame {
    private final Condominio condominio;

    // Construtor da tela de visualização de funcionários
    public VisualizarFuncionariosGUI(Condominio condominio) {
        this.condominio = condominio;
        initializeUI();
    }

    // Inicializa a interface gráfica
    private void initializeUI() {
        setTitle("Visualizar Funcionários");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"Nome", "CPF", "Telefone", "Cargo", "Salário"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        atualizarTabela(model);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton removerButton = new JButton("Remover Funcionário");
        JButton tarefasButton = new JButton("Gerenciar Tarefas");

        styleButton(removerButton);
        styleButton(tarefasButton);

        removerButton.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow >= 0) {
                Object[] options = {"Sim", "Não"};
                int confirm = JOptionPane.showOptionDialog(
                        this,
                        "Deseja remover o funcionário selecionado?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    condominio.getFuncionarios().remove(selectedRow);
                    atualizarTabela(model);
                    JOptionPane.showMessageDialog(
                            this,
                            "Funcionário removido com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um funcionário para remover.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Adiciona ação para o botão de gerenciar tarefas
        tarefasButton.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow >= 0) {
                Funcionario funcionario = condominio.getFuncionarios().get(selectedRow);
                new GerenciarTarefasGUI(condominio, funcionario).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um funcionário para gerenciar tarefas.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttonPanel.add(tarefasButton);
        buttonPanel.add(removerButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }

    // Estiliza os botões com fonte, cor de fundo, cor do texto e efeitos de hover
    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0, 123, 255));
            }
        });
    }

    // Atualiza a tabela com os funcionários do condomínio
    private void atualizarTabela(DefaultTableModel model) {
        model.setRowCount(0);
        for (Funcionario funcionario : condominio.getFuncionarios()) {
            model.addRow(new Object[]{
                    funcionario.getNome(),
                    funcionario.getCpf(),
                    funcionario.getTelefone(),
                    funcionario.getCargo(),
                    funcionario.getSalarioBase()
            });
        }
    }
}