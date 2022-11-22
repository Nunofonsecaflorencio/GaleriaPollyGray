package view.panels;

import model.entity.Arte;
import model.entity.Artista;
import utility.PollyConstants;
import utility.SimpleButton;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import view.PollyGrayFrame;

public class PublishPanel extends JPanel {

    JTextField tTitle, tImage;
    JSpinner sUnits;
    JComboBox cCategory;
    JFormattedTextField tPrice;
    JTextArea aDescription;
    JButton bCancel, bPublish, bImage;


    public PublishPanel(Arte arte) {
        setBackground(PollyConstants.LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initComponents();

        add(getTitlePanel());
        add(getFormPanel());
        add(getBiographyPanel());
        add(getButtonsPanel());
        
        
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PollyConstants.getFrame().goFromTo(PublishPanel.this, PollyConstants.ARTISTS_CARD);   
            }
        });

        if (arte != null)
            populateFields(arte);
    }

    public PublishPanel() {
        this(null);
    }

    private void populateFields(Arte arte) {

        tTitle.setText(arte.getTitulo());
        cCategory.setSelectedIndex(-1);
        cCategory.setEnabled(false);
        sUnits.setValue(arte.getUnidades());
        tPrice.setValue(arte.getPreco());
        tImage.setText(arte.getImagem());
        aDescription.setText(arte.getDescricao());

    }

    private void initComponents() {

        tTitle = new JTextField();
        tImage = new JTextField();
        tImage.setEditable(false);
        tImage.setBackground(PollyConstants.LIGHT);
        sUnits = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        cCategory = new JComboBox<>();
        cCategory.setBackground(PollyConstants.LIGHT);
        tPrice = new JFormattedTextField(new Float(0));
        tPrice.setHorizontalAlignment(SwingConstants.CENTER);

        aDescription = new JTextArea();
        aDescription.setWrapStyleWord(true);
        aDescription.setLineWrap(true);

        bCancel = new SimpleButton("Cancelar", null, Color.RED, PollyConstants.HIGHLIGHT);
        bPublish = new SimpleButton("Publicar", null, Color.ORANGE, PollyConstants.HIGHLIGHT);
        bImage = new SimpleButton("Imagem", PollyConstants.icon("add_image.png"), Color.WHITE, PollyConstants.HIGHLIGHT);

        for (Component c:
             new Component[]{tTitle, tImage, sUnits, cCategory, tPrice, aDescription, bImage}) {
            c.setFont(PollyConstants.getLightFont(20));
        }
        for (Component c:
                new Component[]{bCancel, bPublish}) {
            c.setFont(PollyConstants.getBoldFont(16));
            //c.setForeground(PollyConstants.LIGHT);
        }
    }

    private JPanel getTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.setBackground(PollyConstants.LIGHT);
        JLabel lTitle = new JLabel("Publicar Arte");
        lTitle.setFont(PollyConstants.getBoldFont(28));
        panel.add(lTitle);

        return panel;
    }

    private JPanel getFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.setBackground(PollyConstants.LIGHT);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.weightx = .1;
        gbc.gridx = 0;
        gbc.gridy = 0; panel.add(createLabel("Título:"), gbc);
        gbc.gridy = 1; panel.add(createLabel("Categoria:"), gbc);
        gbc.gridy = 2; panel.add(createLabel("Unidades:"), gbc);
        gbc.gridx = 3; panel.add(createLabel(""), gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 4; panel.add(createLabel("Preço:"), gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0; gbc.gridy = 3; panel.add(bImage, gbc);


        gbc.insets = new Insets(20, 10, 20, 0);
        gbc.gridy = 2;
        gbc.gridx = 1; panel.add(sUnits, gbc);
        gbc.gridx = 5; panel.add(tPrice, gbc);


        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0; panel.add(tTitle, gbc);
        gbc.gridy = 1; panel.add(cCategory, gbc);
        gbc.gridy = 3; panel.add(tImage, gbc);

        return panel;
    }

    private JPanel getBiographyPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setPreferredSize(new Dimension(-1, 300));
        panel.setBackground(PollyConstants.LIGHT);

        JScrollPane sP = new JScrollPane(aDescription);

        JLabel lbl = new JLabel("Descrição Da Arte");
        lbl.setFont(PollyConstants.getBoldFont(20));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(sP, BorderLayout.CENTER);
        return panel;
    }

    private JPanel getButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        panel.setBackground(PollyConstants.LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(bCancel);
        panel.add(bPublish);

        return panel;
    }

    private JLabel createLabel(String s) {
        JLabel lbl = new JLabel(s);
        lbl.setFont(PollyConstants.getLightFont(15));
        return lbl;
    }


    /*
        For Controller
     */

    public void setCategoriesModel(ComboBoxModel model){
        cCategory.setModel(model);
    }

    public void addCategoriaItemListener(ItemListener l){
        cCategory.addItemListener(l);
    }

    public void addPublishActionListener(ActionListener l){
        bPublish.addActionListener(l);
    }


    public void addImageActionListener(ActionListener l){
        bImage.addActionListener(l);
    }

    public void setImageText(String t) {tImage.setText(t);}

    public String getTitulo(){
        return tTitle.getText();
    }

    public Object getCategoria(){
        return cCategory.getSelectedItem();
    }

    public int getUnidades(){
        return (int)sUnits.getModel().getValue();
    }

    public float getPreco(){
        return (float) (tPrice.getValue());
    }

    public String getDescricao(){
        return aDescription.getText();
    }
}
