package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Tabela para visualização de reservas de áreas comuns do condomínio
public class VisualizarReservasGUI extends JFrame {
    private final CadastroReservas cadastroReservas;
    private final DefaultTableModel tableModel;
    private final JTable tabela;
    private final JComboBox<String> filtroArea;
    private java.util.List<ReservaAreaComum> reservasFiltradas;

    // Construtor da tela de visualização de reservas
    public VisualizarReservasGUI(CadastroReservas cadastroReservas) {
        this.cadastroReservas = cadastroReservas;
        this.reservasFiltradas = new java.util.ArrayList<>();
        this.tableModel = new DefaultTableModel(new String[]{"Nome", "CPF", "Apartamento", "Área Comum", "Data", "Hora"}, 0);
        this.tabela = new JTable(tableModel);
        this.filtroArea = new JComboBox<>(new String[]{"Todas", "Salão de Festas", "Piscina", "Churrasqueira", "Academia"});
        initializeUI();
    }

    // Inicializa a interface gráfica
    private void initializeUI() {
        setTitle("Visualizar Reservas");
        setSize(800, 400);
        setMinimumSize(new Dimension(700, 350));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        atualizarTabela("Todas");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(new Color(245, 245, 245));
        JLabel filtroLabel = new JLabel("Filtrar por Área:");
        filtroArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filtroArea.addActionListener(e -> atualizarTabela((String) filtroArea.getSelectedItem()));

        inputPanel.add(filtroLabel);
        inputPanel.add(filtroArea);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        JButton cancelarButton = new JButton("Cancelar Reserva");

        styleButton(cancelarButton);

        // Ação para cancelar reserva
        cancelarButton.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow >= 0) {
                Object[] options = {"Sim", "Não"};
                int confirm = JOptionPane.showOptionDialog(
                        this,
                        "Deseja cancelar a reserva selecionada?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        ReservaAreaComum reserva = reservasFiltradas.get(selectedRow);
                        cadastroReservas.cancelarReserva(
                                reserva.getInquilino().getCpf(),
                                reserva.getDataReserva(),
                                reserva.getHoraReserva(),
                                reserva.getAreaComum()
                        );
                        atualizarTabela((String) filtroArea.getSelectedItem());
                        JOptionPane.showMessageDialog(
                                this,
                                "Reserva cancelada com sucesso!",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (CondominioException ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Erro: " + ex.getMessage(),
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma reserva para cancelar.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        buttonPanel.add(cancelarButton);

        // Configura o layout e adiciona os componentes
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    // Estiliza os botões com fonte, cor de fundo, cor do texto e efeitos de hover
    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
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
                button.setBackground(new Color(0, 123, 255));
            }
        });
    }

    // Atualiza a tabela com as reservas filtradas pela área comum
    private void atualizarTabela(String areaFiltro) {
        tableModel.setRowCount(0);
        reservasFiltradas.clear();
        for (ReservaAreaComum reserva : cadastroReservas.getReservas()) {
            if (areaFiltro.equals("Todas") || reserva.getAreaComum().equals(areaFiltro)) {
                reservasFiltradas.add(reserva);
                tableModel.addRow(new Object[]{
                        reserva.getInquilino().getNome(),
                        reserva.getInquilino().getCpf(),
                        reserva.getInquilino().getApartamento(),
                        reserva.getAreaComum(),
                        reserva.getDataReserva(),
                        reserva.getHoraReserva()
                });
            }
        }
    }
}