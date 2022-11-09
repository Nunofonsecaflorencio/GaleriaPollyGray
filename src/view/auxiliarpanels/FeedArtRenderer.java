package view.auxiliarpanels;

import model.entity.Arte;
import utility.PollyConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class FeedArtRenderer extends JPanel {
    Arte arte;
    ImageRenderer pImage;

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

        JLabel title = new JLabel(arte.getTitulo());
        title.setFont(PollyConstants.getLightFont(14));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEADING));
        p.setBackground(PollyConstants.LIGHT);
        p.setBorder(BorderFactory.createEmptyBorder(2, 0, 15, 0));
        p.add(title);
        add(p, BorderLayout.SOUTH);
        title.setMaximumSize(new Dimension(getWidth() - 10, 99));
    }

    public Arte getArte() {
        return arte;
    }


}

