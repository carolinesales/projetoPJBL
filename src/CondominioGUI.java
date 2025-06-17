package src;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

// Tela principal do sistema de condomínio
// Permite o cadastro e visualização de moradores, gerenciamento de despesas,

public class CondominioGUI extends JFrame {
    private CadastroMorador cadastro;
    private Condominio condominio;

    // construtor da tela principal
    // Recebe o cadastro de moradores e a instância do condomínio
    public CondominioGUI(CadastroMorador cadastro, Condominio condominio) {
        this.cadastro = cadastro;
        this.condominio = condominio;
        initializeUI();
    }

    // Inicializa a interface gráfica
    private void initializeUI() {
        setTitle("Sistema de Condomínio");
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // cria um painel com o GridLayout para organizar os botões
        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245));

        // cria os botões e define suas ações
        JButton cadastrarMoradorButton = new JButton("Cadastrar Morador");
        JButton visualizarMoradoresButton = new JButton("Visualizar Moradores");
        JButton despesasButton = new JButton("Gerenciar Despesas");
        JButton cadastrarFuncionarioButton = new JButton("Cadastrar Funcionário");
        JButton visualizarFuncionariosButton = new JButton("Visualizar Funcionários");
        JButton reservasButton = new JButton("Cadastrar Reservas");
        JButton visualizarReservasButton = new JButton("Visualizar Reservas");
        JButton salvarDadosButton = new JButton("Salvar Dados");

        // estiliza os botões
        styleButton(cadastrarMoradorButton);
        styleButton(visualizarMoradoresButton);
        styleButton(despesasButton);
        styleButton(cadastrarFuncionarioButton);
        styleButton(visualizarFuncionariosButton);
        styleButton(reservasButton);
        styleButton(visualizarReservasButton);
        styleButton(salvarDadosButton);

        // adiciona ações aos botões
        cadastrarMoradorButton.addActionListener(e -> new CadastroMoradorGUI(cadastro).setVisible(true));
        visualizarMoradoresButton.addActionListener(e -> new VisualizarMoradoresGUI(cadastro).setVisible(true));
        despesasButton.addActionListener(e -> new DespesasGUI(condominio).setVisible(true));
        cadastrarFuncionarioButton.addActionListener(e -> new CadastroFuncionarioGUI(condominio).setVisible(true));
        visualizarFuncionariosButton.addActionListener(e -> new VisualizarFuncionariosGUI(condominio).setVisible(true));
        reservasButton.addActionListener(e -> new ReservaAreaComumGUI(condominio.getCadastroReservas(), cadastro).setVisible(true));
        visualizarReservasButton.addActionListener(e -> new VisualizarReservasGUI(condominio.getCadastroReservas()).setVisible(true));
        salvarDadosButton.addActionListener(e -> salvarTodosDados());

        // adiciona os botões ao painel
        panel.add(cadastrarMoradorButton);
        panel.add(visualizarMoradoresButton);
        panel.add(despesasButton);
        panel.add(cadastrarFuncionarioButton);
        panel.add(visualizarFuncionariosButton);
        panel.add(reservasButton);
        panel.add(visualizarReservasButton);
        panel.add(salvarDadosButton);
        add(panel);
    }

    // Estiliza os botões com fonte, cor de fundo, cor do texto e efeitos de hover
    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

    // salva todos os dados do condomínio em arquivos CSV e do cadastro de moradores
    private void salvarTodosDados() {
        try {
            condominio.salvarDespesasCSV();
            condominio.salvarFuncionariosCSV();
            condominio.salvarTarefasCSV();
            condominio.salvarReservasCSV();
            cadastro.salvarMoradores("moradores.ser");
            JOptionPane.showMessageDialog(this, "Todos os dados foram salvos com sucesso!", 
                                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados: " + e.getMessage(), 
                                        "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CondominioGUI(new CadastroMorador(), new Condominio()).setVisible(true));
    }
}