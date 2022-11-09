package view.panels;

import model.entity.Artista;
import utility.PollyConstants;
import view.auxiliarpanels.ImageRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfilePanel extends JPanel {

    JTextField tName, tImage,tDate;
    JTextArea aBiography, aContacts;
    Artista artista;

    public ProfilePanel(Artista artista, BufferedImage image) {
        this.artista = artista;

        setBackground(PollyConstants.LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new GridLayout(1, 2, 10, 0));
        initComponents();


        JPanel sec = new JPanel();
        sec.setLayout(new BoxLayout(sec, BoxLayout.Y_AXIS));

        sec.add(getTitlePanel());
        sec.add(getInfoPanel());
        sec.add(getBiographyPanel());

        JPanel imagePanel = new JPanel(new BorderLayout());

        add(imagePanel);
        add(sec);

        ImageRenderer renderer = new ImageRenderer(image, imagePanel);
        renderer.setSizeReference(imagePanel);
        imagePanel.add(renderer, BorderLayout.CENTER);
    }

    private void initComponents() {
        tName = new JTextField(artista.getNome());
        tName.setHorizontalAlignment(SwingConstants.CENTER);
        tName.setEditable(false);

        tImage = new JTextField(artista.getImagem());
        tImage.setHorizontalAlignment(SwingConstants.CENTER);
        tImage.setEditable(false);
        tImage.setBackground(PollyConstants.LIGHT);

        aBiography = new JTextArea(artista.getBiografia());
        aContacts = new JTextArea(artista.getContactos());

        aBiography.setEditable(false);
        aContacts.setEditable(false);

        aBiography.setWrapStyleWord(true);
        aBiography.setLineWrap(true);

        aContacts.setWrapStyleWord(true);
        aContacts.setLineWrap(true);


        tDate = new JTextField(PollyConstants.reformatDate(artista.getDataNascimento()));
        tDate.setHorizontalAlignment(SwingConstants.CENTER);
        tDate.setEditable(false);


        for (Component c:
                new Component[]{tName, tDate, aBiography, aContacts, tImage}) {
            c.setFont(PollyConstants.getLightFont(20));
        }


    }
    private JPanel getTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.setBackground(PollyConstants.LIGHT);
        JLabel lTitle = new JLabel(artista.getNome());
        lTitle.setFont(PollyConstants.getBoldFont(28));
        panel.add(lTitle);
        return panel;
    }

    private JPanel getInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PollyConstants.LIGHT);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.insets = new Insets(0, 0, 10, 0);
        c.weighty = 1;
        c.gridx = 1;
        c.weightx = .1;
        c.gridy = 1; panel.add(createLabel("Data de Nascimento:"), c);

        c.insets = new Insets(0, 10, 10, 0);
        c.weightx = 1;
        c.gridx = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridy = 1; panel.add(createLabel(artista.getDataNascimento()), c);



        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
        pMain.add(panel);
        pMain.add(getContactsPanel());

        return pMain;
    }

    private JPanel getContactsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.setBackground(PollyConstants.LIGHT);
        //panel.setPreferredSize(new Dimension(-1, 100));
        JScrollPane sP = new JScrollPane(aContacts);
        sP.setBorder(BorderFactory.createEmptyBorder(20, 0, 0 , 0));
        sP.setBackground(PollyConstants.LIGHT);
        JLabel lbl = new JLabel("Contactos");
        lbl.setFont(PollyConstants.getBoldFont(20));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(sP, BorderLayout.CENTER);
        return panel;
    }


    private JPanel getBiographyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0 , 0));
        panel.setPreferredSize(new Dimension(-1, 300));
        panel.setBackground(PollyConstants.LIGHT);

        JScrollPane sP = new JScrollPane(aBiography);
        sP.setBackground(PollyConstants.LIGHT);
        sP.setBorder(BorderFactory.createEmptyBorder(20, 0, 0 , 0));
        JLabel lbl = new JLabel("Biografia");
        lbl.setFont(PollyConstants.getBoldFont(20));
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(sP, BorderLayout.CENTER);
        return panel;
    }


    private JLabel createLabel(String s) {
        JLabel lbl = new JLabel(s);
        lbl.setFont(PollyConstants.getLightFont(15));
        return lbl;
    }


    /*
        Controller
     */



//    private class ImagePanel extends JPanel {
//        BufferedImage image, placeHolderImage;
//        public ImagePanel() {
//            setBackground(PollyConstants.LIGHT);
//
//            new SwingWorker() {
//                @Override
//                protected Object doInBackground() throws Exception {
//                    image = ImageIO.read(new File(PollyConstants.ASSETSPATH + "images\\artist_test.jpg"));
//                    return null;
//                }
//            }.execute();
//
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2d = (Graphics2D) g;
//
//            if (image == null){
//
//            }
//
//            double r = (double) image.getWidth() / image.getHeight();
//
//            int W, H = ImagePanel.this.getHeight();
//            W = (int) (r * H);
//
//            int x = ImagePanel.this.getWidth() / 2 - W / 2;
//            int y = ImagePanel.this.getHeight() / 2 - H / 2;
//
//
//            g2d.drawImage(image, x, y, W, H, null);
//        }
//    }
}



