package view.panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utility.PollyConstants;
import utility.SimpleButton;

/**
 * @author Belars Wonder
 */
public class OrderingPanel extends JDialog {
    
    JLabel lArte, lPrecoUnitario, lPrecoTotal;
    JTextField tNome, tEndereco;
    JButton bConfirmarCompra, bCancelarCompra;
    JSpinner sUnidades;

    public OrderingPanel(Frame owner, boolean modal) {
        super(owner, modal);
        setUndecorated(true);

        initComponents();
        add(getInfoPanel());

        pack();
        setLocationRelativeTo(PollyConstants.getFrame());


    }

    private JPanel getInfoPanel() {
        JPanel panel = new JPanel();

        panel.setBackground(PollyConstants.LIGHT);
        panel.setPreferredSize(new Dimension(460, 540));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1; c.weighty = 1;

        c.gridx = 0; c.gridy = 0;
        panel.add(lArte, c);
        
        c.insets = new Insets(20, 0, 0, 0);
         
        // Top
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridy = 2; panel.add(tNome, c);
        c.gridy = 4; panel.add(tEndereco, c);
        c.gridy = 1; panel.add(createLabel("Nome do Cliente:"), c);
        c.gridy = 3; panel.add(createLabel("Endereço do Cliente:"), c);

        c.gridwidth =  1;
        c.weightx = .1;
        //Down
        c.gridy = 5; panel.add(createLabel("Unidades:"), c);
        c.gridy = 6; panel.add(createLabel("Preço Unitário"), c);
        c.gridy = 7; panel.add(createLabel("Preço Total"), c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 5; panel.add(sUnidades, c);
        c.gridy = 6; panel.add(lPrecoUnitario, c);
        c.gridy = 7; panel.add(lPrecoTotal, c);
        
        
        //Button
        c.gridx = 0; c.gridy = 8; c.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(bConfirmarCompra, c);
        c.gridy = 9;
        panel.add(bCancelarCompra, c);

        return panel;
    }

    private void initComponents() {
        sUnidades = new JSpinner();
        
        lArte = createLabel("Compra");
        lArte.setFont(PollyConstants.getBoldFont(20));

        lPrecoUnitario = createLabel("0 MT");
        lPrecoTotal = createLabel("0 MT");

        lPrecoUnitario.setHorizontalAlignment(SwingConstants.RIGHT);
        lPrecoTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        tNome = new JTextField();
        tEndereco = new JTextField();
        
        bConfirmarCompra = new SimpleButton("Efectuar Compra", null, PollyConstants.LIGHT, PollyConstants.HIGHLIGHT);
        bCancelarCompra = new SimpleButton("Cancelar Compra", null, Color.RED, PollyConstants.HIGHLIGHT);
        // Some settings
        bConfirmarCompra.setFont(PollyConstants.getBoldFont(20));
        bCancelarCompra.setFont(PollyConstants.getBoldFont(20));
        
        for (Component c:
             new Component[]{tNome, tEndereco, sUnidades}) {
            c.setFont(PollyConstants.getLightFont(20));
        }

        bCancelarCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderingPanel.this.dispose(); // Fechar só o Dialog e não o sistema (System.exit(0))
            }
        });


        sUnidades.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setPrecoTotal(getUnidades() * getPrecoUnitario());
            }
        });
    }
    
    private JLabel createLabel(String s) {
        JLabel lbl = new JLabel(s);
        lbl.setFont(PollyConstants.getLightFont(20));
        return lbl;
    }



    public String getNome(){
        return tNome.getText();
    }

    public void setNome(String n){
        tNome.setText(n);
    }

    public String getEndereco(){
        return tEndereco.getText();
    }

    public void setEndereco(String n){
        tEndereco.setText(n);
    }

    public int getUnidades(){
        return (int) sUnidades.getValue();
    }

    public void setPrecoUnitario(float p){
        lPrecoUnitario.setText(
                p + " MT"
        );
    }

    public float getPrecoUnitario(){
        return Float.parseFloat(lPrecoUnitario.getText().split(" ")[0]);
    }


    public void setPrecoTotal(float p){
        lPrecoTotal.setText(
                p + " MT"
        );
    }

    public float getPrecoTotal(){
        return Float.parseFloat(lPrecoTotal.getText().split(" ")[0]);
    }

    public void setUnidadesModel(SpinnerModel m){
        sUnidades.setModel(m);
    }
    
    
    public void addConfirmActionListener(ActionListener l) {
        bConfirmarCompra.addActionListener(l);
    }


}
