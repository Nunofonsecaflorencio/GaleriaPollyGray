package view.panels;

import utility.PollyConstants;
import utility.SimpleButton;
import view.auxiliarpanels.ArtistRender;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrdersPanel extends JPanel {

    JTextField tSearch;
    JButton bPrintReport;
    JTable tableOrders;

    public OrdersPanel() {
        setBackground(PollyConstants.LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());
        initComponents();
        add(getHeaderPanel(), BorderLayout.NORTH);
        add(getTablePanel(), BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);

    }

    private void initComponents() {
        tSearch = new JTextField(15);
        tSearch.setFont(PollyConstants.getLightFont(18));


        tableOrders = new JTable();
        tableOrders.setSelectionBackground(PollyConstants.HIGHLIGHT);
        tableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        bPrintReport = new SimpleButton("Imprimir Relatório", null, PollyConstants.LIGHT, PollyConstants.HIGHLIGHT);
        bPrintReport.setFont(PollyConstants.getBoldFont(13));


    }

    private JPanel getHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.setBackground(PollyConstants.LIGHT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel lTitle = new JLabel("Compras");
        lTitle.setFont(PollyConstants.getBoldFont(28));
        p1.add(lTitle);

        JPanel p2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 5, 0, 5);

        gbc.weightx = 1;
        JLabel l = new JLabel();
        l.setPreferredSize(new Dimension(-1, 40));
        p2.add(l, gbc);
        gbc.weightx = .4;
        p2.add(tSearch, gbc);
        gbc.weightx = .1;
        //p2.add(bSearch, gbc);

        panel.add(p1);
        panel.add(p2);
        p1.setBackground(p1.getParent().getBackground());
        p2.setBackground(p1.getParent().getBackground());
        return panel;
    }

    private JScrollPane getTablePanel() {
        JScrollPane panel = new JScrollPane(tableOrders);
        panel.setBackground(PollyConstants.LIGHT);
        panel.setBorder(null);

        return panel;
    }

    private JPanel getButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        panel.setBackground(PollyConstants.LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        panel.add(bPrintReport);

        return panel;
    }


    /*
        For Controllers Objects
     */
    public void setOrdersTableModel(TableModel tableModel) {
        tableOrders.setModel(tableModel);
    }


    public JTable getTableOrders() {
        return tableOrders;
    }


    public void adbPrintReportActionListener(ActionListener l){
        bPrintReport.addActionListener(l);
    }


    public String getFilterText(){
        return tSearch.getText();
    }

    public void addTypingFilterListener(ActionListener l){
        tSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                l.actionPerformed(null);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                l.actionPerformed(null);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                l.actionPerformed(null);
            }
        });
    }
}
