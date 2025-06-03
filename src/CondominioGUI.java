package src;

import javax.swing.*;
import java.awt.*;

public class CondominioGUI extends JFrame {
    private CadastroMorador cadastro;
    private Condominio condominio;

    public CondominioGUI(CadastroMorador cadastro, Condominio condominio) {
        this.cadastro = cadastro;
        this.condominio = condominio;
        setTitle("Sistema de Condomínio");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton cadastrarButton = new JButton("Cadastrar Morador");
        JButton visualizarButton = new JButton("Visualizar Moradores");
        JButton despesasButton = new JButton("Gerenciar Despesas");
        JButton sairButton = new JButton("Sair");

        cadastrarButton.addActionListener(e -> new CadastroMoradorGUI(cadastro).setVisible(true));
        visualizarButton.addActionListener(e -> new VisualizarMoradoresGUI(cadastro).setVisible(true));
        despesasButton.addActionListener(e -> new DespesasGUI(condominio).setVisible(true));
        sairButton.addActionListener(e -> System.exit(0));

        panel.add(cadastrarButton);
        panel.add(visualizarButton);
        panel.add(despesasButton);
        panel.add(sairButton);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CondominioGUI(new CadastroMorador(), new Condominio()).setVisible(true));
    }
}