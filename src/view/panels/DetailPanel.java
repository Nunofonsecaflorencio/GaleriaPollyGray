package view.panels;

import utility.PollyConstants;
import utility.SimpleButton;
import view.auxiliarpanels.ImageRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class DetailPanel extends JPanel {

    JButton bDeletar, bEditar, bComprar;
    JTextArea aTitulo, aDescricao;
    JLabel lArtista, lDataPublicacao, lPreco, lUnidades;


    public DetailPanel(BufferedImage image) {
        initComponents();
        setBackground(PollyConstants.LIGHT);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        setLayout(new GridLayout(1,2, 10, 0));

        JPanel imagePanel = new JPanel(new BorderLayout());
        add(imagePanel);
        add(getInfoPanel());

        ImageRenderer renderer = new ImageRenderer(image, imagePanel);
        renderer.setSizeReference(imagePanel);
        imagePanel.add(renderer, BorderLayout.CENTER);
    }


    private JPanel getInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PollyConstants.LIGHT);

        panel.add(getDetailsPanel());
        panel.add(getButtonsPanel());
        panel.add(getBuyPanel());
        panel.add(getDescriptionPanel());
        
        return panel;
    }


    private JPanel getDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PollyConstants.LIGHT);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridx = 0;

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridy = 0; panel.add(PollyConstants.createLabel("Detalhes", PollyConstants.getBoldFont(30)), gbc);

        gbc.gridwidth = 1;
        //gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = .1;
        gbc.weighty = .2;
        gbc.gridy = 2; panel.add(PollyConstants.createLabel("Artista: ", PollyConstants.getBoldFont(15)), gbc);
        gbc.gridy = 3; panel.add(PollyConstants.createLabel("Publicado em: ", PollyConstants.getBoldFont(15)), gbc);
        gbc.gridx = 1;
        gbc.weighty = .3;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 2; panel.add(lArtista, gbc);
        gbc.gridy = 3; panel.add(lDataPublicacao, gbc);

        gbc.gridx = 0;
        gbc.ipady = 20;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridy = 1; panel.add(aTitulo, gbc);

        return panel;
    }

    private JPanel getButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 8, 10, 0));
        //panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        panel.setBackground(PollyConstants.LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(bComprar);
        panel.add(bEditar);
        panel.add(bDeletar);


        for (int i = 0; i < 5; i++) {
            panel.add(new JLabel());
        }

        return panel;
    }

    private JPanel getBuyPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PollyConstants.LIGHT);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridx = 0;

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridy = 0; panel.add(PollyConstants.createLabel("Compra", PollyConstants.getBoldFont(25)), gbc);
        gbc.weightx = .1;
        gbc.gridwidth = 1;
        gbc.weighty = .2;
        gbc.gridy = 1; panel.add(PollyConstants.createLabel("Preço(MT): ", PollyConstants.getBoldFont(15)), gbc);
        gbc.gridy = 2; panel.add(PollyConstants.createLabel("Unidades: ", PollyConstants.getBoldFont(15)), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1; panel.add(lPreco, gbc);
        gbc.gridy = 2; panel.add(lUnidades, gbc);


        return panel;
    }

    private JPanel getDescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0 , 0));
        panel.setPreferredSize(new Dimension(-1, 200));
        panel.setBackground(PollyConstants.LIGHT);

        JScrollPane sP = new JScrollPane(aDescricao);
        sP.setBackground(PollyConstants.LIGHT);
        sP.setBorder(BorderFactory.createEmptyBorder(20, 0, 0 , 0));
        panel.add(PollyConstants.createLabel("Descrição da arte", PollyConstants.getBoldFont(25)), BorderLayout.NORTH);
        panel.add(sP, BorderLayout.CENTER);
        return panel;
    }


    private void initComponents() {

        bEditar = new SimpleButton(null, PollyConstants.icon("edit_art.png"), Color.ORANGE, PollyConstants.HIGHLIGHT);
        bDeletar = new SimpleButton(null, PollyConstants.icon("delete_art.png"), Color.RED, PollyConstants.HIGHLIGHT);
        bComprar = new SimpleButton(null, PollyConstants.icon("add_shopping_cart.png"), Color.GREEN, PollyConstants.HIGHLIGHT);

        bComprar.setToolTipText("Comprar Esta Arte");
        bDeletar.setToolTipText("Apagar Esta Arte");
        bEditar.setToolTipText("Editar Esta Arte");

        aTitulo = new JTextArea();
        aDescricao = new JTextArea();


        for (JTextArea t:
             new JTextArea[]{aTitulo, aDescricao}) {
            t.setBorder(null);
            t.setWrapStyleWord(true);
            t.setLineWrap(true);
            t.setEditable(false);
            t.setFont(PollyConstants.getLightFont(18));
        }

        lArtista = PollyConstants.createLabel("", PollyConstants.getLightFont(15));
        lDataPublicacao = PollyConstants.createLabel("", PollyConstants.getLightFont(15));
        lPreco = PollyConstants.createLabel("", PollyConstants.getLightFont(15));
        lUnidades = PollyConstants.createLabel("", PollyConstants.getLightFont(15));

        for (JLabel l:
             new JLabel[]{lArtista,lDataPublicacao, lPreco, lUnidades}) {
            l.setHorizontalAlignment(SwingConstants.LEFT);
        }
}



    public void setEnableBComprar(boolean b){ bComprar.setEnabled(b);}

    public void setTitulo(String titulo) {
        aTitulo.setText(titulo);
    }

    public void setNomeArtista(String nomeArtista) {
        lArtista.setText(nomeArtista);
    }

    public void setDataPublicacao(String dataPublicacao) {
        lDataPublicacao.setText(dataPublicacao);
    }

    public void setPreco(float preco) {
        lPreco.setText(preco+"");
    }

    public void setUnidades(int unidades) {
        lUnidades.setText(unidades+"");
    }

    public void setDescricao(String descricao) {
        aDescricao.setText(descricao);
    }

    public void addDeleteActionListener(ActionListener l) {
        bDeletar.addActionListener(l);
    }

    public void addUpdateActionListener(ActionListener l) {
        bEditar.addActionListener(l);
    }

    public void addComprarActionListener(ActionListener l) {
        bComprar.addActionListener(l);
    }
}
