package view.auxiliarpanels;

import model.valueobjects.Arte;
import utility.PollyConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class FeedArtRenderer extends JPanel {
    Arte arte;
    ImageRenderer pImage;
    JTextArea aTitulo;

    JPanel titlePanel;

    public FeedArtRenderer(Arte arte, BufferedImage image, MouseListener listener) {
        this.arte = arte;
        addMouseListener(listener);
        pImage = new ImageRenderer(image, this);

        setBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(PollyConstants.HIGHLIGHT, 2, true)));
        setBackground(PollyConstants.LIGHT);
        setLayout(new BorderLayout());
        add(pImage, BorderLayout.CENTER);
        
        aTitulo = new JTextArea();
        aTitulo.setBorder(null);
        aTitulo.setWrapStyleWord(true);
        aTitulo.setLineWrap(true);
        aTitulo.setEditable(false);
        aTitulo.setFont(PollyConstants.getLightFont(16));
        aTitulo.setColumns(20);

        aTitulo.setText(
                        ((arte.getIdArte() >= 0) ? arte.getIdArte() + ". " : "")
                        + arte.getTitulo() +
                        ((arte.getUnidades() == 0) ? "   [ESGOTADO]" : "")
        );



        titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        titlePanel.setBackground(PollyConstants.LIGHT);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 15, 0));
        titlePanel.add(aTitulo);
        add(titlePanel, BorderLayout.SOUTH);
    }

    public Arte getArte() {
        return arte;
    }


}

