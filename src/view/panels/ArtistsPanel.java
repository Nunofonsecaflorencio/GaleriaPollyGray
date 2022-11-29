package view.panels;

import utility.PollyConstants;
import utility.SimpleButton;
import view.auxiliarpanels.ArtistRender;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import view.PollyGrayFrame;

public class ArtistsPanel extends JPanel {

    JTextField tSearch;
    JButton bSearch, bPublish, bProfile, bUpdate, bCreate, bDelete;
    JList lArtists;

    public ArtistsPanel() {
        setBackground(PollyConstants.LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout());
        initComponents();
        add(getHeaderPanel(), BorderLayout.NORTH);
        add(getListPanel(), BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);

    }
    public void addCreateActionListener(ActionListener l){
        bCreate.addActionListener(l);
    }

    public void addArtistListSelectionListener(ListSelectionListener l){
        lArtists.addListSelectionListener(l);
    }

    private void initComponents() {
        tSearch = new JTextField(15);
        tSearch.setFont(PollyConstants.getLightFont(18));


        lArtists = new JList();
        lArtists.setVisibleRowCount(-1);
        lArtists.setSelectionBackground(PollyConstants.HIGHLIGHT);
        lArtists.setCellRenderer(new ArtistRender());
        lArtists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //bSearch = new SimpleButton(null, PollyConstants.icon("search.png"), Color.WHITE, PollyConstants.HIGHLIGHT);
        bPublish = new SimpleButton("Publicar Arte", PollyConstants.icon("publish.png"), PollyConstants.LIGHT, PollyConstants.HIGHLIGHT);
        bProfile = new SimpleButton("Ver Perfil", null, PollyConstants.LIGHT, PollyConstants.HIGHLIGHT);
        bUpdate = new SimpleButton("Actualizar Dados", null, PollyConstants.LIGHT, PollyConstants.HIGHLIGHT);
        bDelete = new SimpleButton("Apagar Artista", null, Color.RED, PollyConstants.HIGHLIGHT);
        bCreate = new SimpleButton("Criar Artista", null, PollyConstants.LIGHT, PollyConstants.HIGHLIGHT);


        for (Component c:
             new Component[]{bPublish, bProfile, bUpdate, bCreate, bDelete}) {
            c.setFont(PollyConstants.getBoldFont(13));
        }

        for (Component c:
                new Component[]{bPublish, bProfile, bUpdate, bDelete}) {
            c.setEnabled(false);
        }

    }

    private JPanel getHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.setBackground(PollyConstants.LIGHT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel lTitle = new JLabel("Artistas");
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

    private JScrollPane getListPanel() {
        JScrollPane panel = new JScrollPane(lArtists);
        panel.setBackground(PollyConstants.LIGHT);
        panel.setBorder(null);

        return panel;
    }

    private JPanel getButtonsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PollyConstants.LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.insets = new Insets(0, 0, 0, 5);
        panel.add(bPublish, gbc);
        gbc.insets = new Insets(0, 5, 0, 5);
        panel.add(bProfile, gbc);
        panel.add(bCreate, gbc);
        panel.add(bUpdate, gbc);
        gbc.insets = new Insets(0, 5, 0, 0);
        panel.add(bDelete, gbc);

        return panel;
    }


    /*
        For Controllers Objects
     */
    public void setArtistsListModel(ListModel listModel) {
        lArtists.setModel(listModel);
    }


    public JList getlArtists() {
        return lArtists;
    }

    public void addPublishActionListener(ActionListener l){
        bPublish.addActionListener(l);
    }


    public void addProfileActionListener(ActionListener l){
        bProfile.addActionListener(l);
    }


    public void addUpdateActionListener(ActionListener l){
        bUpdate.addActionListener(l);
    }


    public void addDeleteActionListener(ActionListener l){
        bDelete.addActionListener(l);
    }

    public void setButtonPublishEnabled(boolean e){
        bPublish.setEnabled(e);
    }


    public void setButtonProfileEnabled(boolean e){
        bProfile.setEnabled(e);
    }


    public void setButtonUpdateEnabled(boolean e){
        bUpdate.setEnabled(e);
    }


    public void setButtonDeleteEnabled(boolean e){
        bDelete.setEnabled(e);
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
