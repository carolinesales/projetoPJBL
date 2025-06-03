package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VisualizarMoradoresGUI extends JFrame {
    private CadastroMorador cadastro;

    public VisualizarMoradoresGUI(CadastroMorador cadastro) {
        this.cadastro = cadastro;
        setTitle("Visualizar Moradores e Apartamentos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"Nome", "CPF", "Apartamento", "Telefone", "Tipo"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        atualizarTabela(model);

        JButton removerButton = new JButton("Remover Morador");
        removerButton.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow >= 0) {
                String apartamento = (String) model.getValueAt(selectedRow, 2);
                cadastro.getMoradores().remove(selectedRow);
                cadastro.getApartamentos().stream()
                        .filter(apt -> apt.getNumero().equals(apartamento))
                        .findFirst()
                        .ifPresent(apt -> apt.setMorador(null));
                atualizarTabela(model);
                JOptionPane.showMessageDialog(this, "Morador removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um morador para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        panel.add(removerButton, BorderLayout.SOUTH);
        add(panel);
    }

    private void atualizarTabela(DefaultTableModel model) {
        model.setRowCount(0);
        for (Morador morador : cadastro.getMoradores()) {
            String tipo = morador instanceof Proprietario ? "Proprietário" : "Inquilino";
            model.addRow(new Object[]{morador.getNome(), morador.getCpf(), morador.getApartamento(), morador.getTelefone(), tipo});
        }
    }
}