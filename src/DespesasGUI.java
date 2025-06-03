package src;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class DespesasGUI extends JFrame {
    private Condominio condominio;

    public DespesasGUI(Condominio condominio) {
        this.condominio = condominio;
        setTitle("Gerenciar Despesas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"Tipo", "Valor", "Data"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        atualizarTabela(model);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel tipoLabel = new JLabel("Tipo:");
        JTextField tipoField = new JTextField();
        JLabel valorLabel = new JLabel("Valor:");
        JTextField valorField = new JTextField();
        JLabel dataLabel = new JLabel("Data (YYYY-MM-DD):");
        JTextField dataField = new JTextField();
        JButton adicionarButton = new JButton("Adicionar Despesa");

        adicionarButton.addActionListener(e -> {
            try {
                String tipo = tipoField.getText();
                double valor = Double.parseDouble(valorField.getText());
                LocalDate data = LocalDate.parse(dataField.getText());
                condominio.adicionarDespesa(new Despesa(tipo, valor, data));
                atualizarTabela(model);
                JOptionPane.showMessageDialog(this, "Despesa cadastrada com sucesso!");
                tipoField.setText("");
                valorField.setText("");
                dataField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        formPanel.add(tipoLabel);
        formPanel.add(tipoField);
        formPanel.add(valorLabel);
        formPanel.add(valorField);
        formPanel.add(dataLabel);
        formPanel.add(dataField);
        formPanel.add(new JLabel());
        formPanel.add(adicionarButton);

        JButton totalButton = new JButton("Calcular Total do Mês");
        totalButton.addActionListener(e -> {
            try {
                String mesAno = JOptionPane.showInputDialog(this, "Digite mês e ano (MM/YYYY):");
                String[] partes = mesAno.split("/");
                int mes = Integer.parseInt(partes[0]);
                int ano = Integer.parseInt(partes[1]);
                double total = condominio.calcularTotalDespesasMes(mes, ano);
                JOptionPane.showMessageDialog(this, "Total de despesas: R$" + total);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(totalButton, BorderLayout.SOUTH);
        add(panel);
    }

    private void atualizarTabela(DefaultTableModel model) {
        model.setRowCount(0);
        for (Despesa despesa : condominio.getDespesas()) {
            model.addRow(new Object[]{despesa.getTipo(), despesa.getValor(), despesa.getData()});
        }
    }
}