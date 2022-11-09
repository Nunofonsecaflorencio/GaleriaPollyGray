package view.auxiliarpanels;

import utility.PollyConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer extends JPanel {
    BufferedImage image;
    Container sizeReference, parent;
    public ImageRenderer(BufferedImage image, Container parent) {
        this.image = image;
        this.parent = parent;
        setBackground(PollyConstants.LIGHT);
    }

    public void setSizeReference(Container sizeReference) {
        this.sizeReference = sizeReference;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (parent == null && sizeReference == null) return;

        double r = (double) image.getWidth() / (double)image.getHeight();
        int W;
        if (sizeReference == null)
            W = parent.getParent().getWidth();
        else
            W = sizeReference.getWidth();

        int H = (int)(W / r);
        g2d.drawImage(image, 0, 0, W, H, null);
        revalidate();
        setPreferredSize(new Dimension(-1, H));
    }
}