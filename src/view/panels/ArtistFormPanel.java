package view.panels;

import model.valueobjects.Artista;
import utility.PollyConstants;
import utility.SimpleButton;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;

public class ArtistFormPanel extends JPanel {

    JTextField tName, tImage;
    JButton bCancel, bConfirm, bImage;
    JFormattedTextField tDate;
    JTextArea aBiography, aContacts;
    JFileChooser fileChooser;

    Artista artista;
    MaskFormatter dateFormat = null;

    public ArtistFormPanel(Artista artista) {
        this.artista = artista;
        setBackground(PollyConstants.LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();

//        JPanel auxForm = new JPanel(new GridLayout(1, 2, 20, 0));
//        auxForm.setBackground(PollyConstants.LIGHT);
//        auxForm.add(new ImagePanel());
//        auxForm.add(getInfoPanel());

        add(getTitlePanel());
        add(getInfoPanel());
        add(getBiographyPanel());
        add(getButtonsPanel());

        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PollyConstants.getFrame().goFromTo(ArtistFormPanel.this, PollyConstants.ARTISTS_CARD);           
            }
        });


        if (artista != null)
            populateFields(artista);
    }

    private void populateFields(Artista artista) {
        tName.setText(artista.getNome());
        tDate.setValue(PollyConstants.reformatDate(artista.getDataNascimento()));
        tImage.setText(artista.getImagem());
        aContacts.setText(artista.getContactos());
        aBiography.setText(artista.getBiografia());
    }

    public ArtistFormPanel() {
        this(null);
    }

    private void initComponents() {
        fileChooser = PollyConstants.createImageFileChooser();


        tName = new JTextField();
        tName.setHorizontalAlignment(SwingConstants.CENTER);
        tImage = new JTextField();
        tImage.setHorizontalAlignment(SwingConstants.CENTER);
        tImage.setEditable(false);
        tImage.setBackground(PollyConstants.LIGHT);

        bCancel = new SimpleButton("Cancelar", null, Color.RED, PollyConstants.HIGHLIGHT);
        bConfirm = new SimpleButton("Confirmar", null, Color.GREEN, PollyConstants.HIGHLIGHT);
        bImage = new SimpleButton("Foto", PollyConstants.icon("add_image.png"), Color.WHITE, PollyConstants.HIGHLIGHT);

        aBiography = new JTextArea();
        aContacts = new JTextArea();

        aBiography.setWrapStyleWord(true);
        aBiography.setLineWrap(true);

        aContacts.setWrapStyleWord(true);
        aContacts.setLineWrap(true);


        try {
            dateFormat = new MaskFormatter("## / ## / ####");
            dateFormat.setPlaceholderCharacter('-');

        } catch (ParseException e) {
            e.printStackTrace();
        }

        tDate = new JFormattedTextField(dateFormat);
        tDate.setHorizontalAlignment(SwingConstants.CENTER);


        for (Component c:
                new Component[]{tName, tDate, aBiography, aContacts, tImage, bImage}) {
            c.setFont(PollyConstants.getLightFont(20));
        }

        for (Component c:
                new Component[]{bCancel, bConfirm}) {
            c.setFont(PollyConstants.getBoldFont(16));
        }

        bImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int response = fileChooser.showOpenDialog(ArtistFormPanel.this);

                if (response == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    tImage.setText(file.getPath());
                }
            }
        });



    }
    private JPanel getTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.setBackground(PollyConstants.LIGHT);
        JLabel lTitle = new JLabel("Preencha Seus Dados");
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
        c.gridy = 0; panel.add(createLabel("Nome:"), c);
        c.gridy = 1; panel.add(createLabel("Data de Nascimento:"), c);
        c.gridy = 2; panel.add(bImage, c);

        c.insets = new Insets(0, 10, 10, 0);
        c.weightx = 1;
        c.gridx = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridy = 0; panel.add(tName, c);
        c.gridy = 1; panel.add(tDate, c);
        c.gridy = 2; panel.add(tImage, c);


        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
        pMain.add(panel);
        pMain.add(getContactsPanel());

        return pMain;
    }

    private JPanel getContactsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.setBackground(PollyConstants.LIGHT);
        panel.setPreferredSize(new Dimension(-1, 100));
        JScrollPane sP = new JScrollPane(aContacts);

        panel.add(createLabel("Contactos"), BorderLayout.NORTH);
        panel.add(sP, BorderLayout.CENTER);
        return panel;
    }


    private JPanel getBiographyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0 , 0));
        panel.setPreferredSize(new Dimension(-1, 300));
        panel.setBackground(PollyConstants.LIGHT);

        JScrollPane sP = new JScrollPane(aBiography);

        JLabel lbl = new JLabel("Biografia");
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
        panel.add(bConfirm);

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

    public void addConfirmarActionListener(ActionListener l){
        bConfirm.addActionListener(l);
    }


    public String getNome(){
        return tName.getText();
    }

    public String getDataNascimento(){
        return (tDate.getValue() == null) ? "" : tDate.getValue().toString();
    }

    public String getImagem(){
        return (fileChooser.getSelectedFile() == null) ? null : fileChooser.getSelectedFile().getName();
    }
    public boolean imageChanged(){
        return !tImage.getText().equals(artista.getImagem());
    }
    public File getFileImagem(){
        return fileChooser.getSelectedFile();
    }

    public String getContactos(){
        return aContacts.getText();
    }

    public String getBiografia(){
        return aBiography.getText();
    }



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
