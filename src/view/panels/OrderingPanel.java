package view.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import utility.PollyConstants;
import utility.SimpleButton;

/**
 * @author Belars Wonder
 */
public class OrderingPanel extends JDialog {
    
    JLabel lArte;
    JTextField tNome, tEndereco;
    JButton bConfirmarCompra, bCancelarCompra;
    JSpinner sUnidades;

    public OrderingPanel(Frame owner, boolean modal) {
        super(owner, modal);
        setUndecorated(true);

        initComponents();
        add(getInfoPanel());

        pack();
        setLocationRelativeTo(null);

    }


    private JPanel getInfoPanel() {
        JPanel panel = new JPanel();

        panel.setBackground(PollyConstants.LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        c.gridx = 0; c.gridy = 0;
        panel.add(lArte, c);
        
        c.insets = new Insets(20, 0, 0, 0);
         
        // Top
        c.gridwidth = 2;
        c.gridy = 2; panel.add(tNome, c);
        c.gridy = 4; panel.add(tEndereco, c);
        c.gridwidth =  1;
        
        c.gridy = 1; panel.add(createLabel("Nome do Cliente:"), c);
        c.gridy = 3; panel.add(createLabel("Endereço do Cliente:"), c);
        
        //Down
        c.gridy = 5; panel.add(createLabel("Unidades:"), c);
        c.gridy = 6; panel.add(createLabel("Preço Unitário"), c);
        c.gridy = 7; panel.add(createLabel("Preço Total"), c);
        
        c.gridx = 1;
        c.gridy = 5; panel.add(sUnidades, c);
        c.gridy = 6; panel.add(createLabel("Units Price Comes Here"), c);
        c.gridy = 7; panel.add(createLabel("Total Price Comes Here"), c);
        
        
        //Button
        c.gridx = 0; c.gridy = 8; c.gridwidth = 2;
        panel.add(bConfirmarCompra, c);
        c.gridy = 9;
        panel.add(bCancelarCompra, c);
        
        
        return panel;
    }

    private void initComponents() {
        sUnidades = new JSpinner();
        
        lArte = createLabel("Compra");
        lArte.setFont(PollyConstants.getBoldFont(20));
        
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

    public void setUnidadesModel(SpinnerModel m){
        sUnidades.setModel(m);
    }
    
    
    public void addConfirmActionListener(ActionListener l) {
        bConfirmarCompra.addActionListener(l);
    }


}
