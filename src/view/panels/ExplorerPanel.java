package view.panels;

import utility.PollyConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExplorerPanel extends JPanel {


    JPanel[] feedPanels;
    public ExplorerPanel() {
        initComponents();
        setLayout(new BorderLayout());
        add(new HeaderPanel(), BorderLayout.NORTH);

        JScrollPane sP = new JScrollPane(getFeedPanel());
        sP.setBorder(null);
        sP.getVerticalScrollBar().setUI(new BasicScrollBarUI());
        sP.getHorizontalScrollBar().setUI(new BasicScrollBarUI());
        sP.getVerticalScrollBar().setUnitIncrement(16);
        //sP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(sP, BorderLayout.CENTER);

    }

    private void initComponents() {
        feedPanels = new JPanel[4];
        for (int i = 0; i < feedPanels.length; i++) {
            feedPanels[i] = new JPanel();
            feedPanels[i].setLayout(new BoxLayout(feedPanels[i], BoxLayout.Y_AXIS));
            feedPanels[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            feedPanels[i].setBackground(PollyConstants.LIGHT);
        }
        

    }

    public JPanel[] getFeedPanels() {
        return feedPanels;
    }

    public JPanel getFeedPanel(int i) {
        return feedPanels[i];
    }

    private JPanel getFeedPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        //panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(PollyConstants.LIGHT);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weighty = 1; gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;

        for (int i = 0; i < feedPanels.length; i++) {
            panel.add(feedPanels[i], gbc);
        }

        return panel;
    }


    private class HeaderPanel extends JPanel {

        BufferedImage image;

        public HeaderPanel() {
            try {
                image = ImageIO.read(new File(PollyConstants.ASSETSPATH + "images\\pollygray_header.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            setBackground(PollyConstants.LIGHT);
            setPreferredSize(new Dimension(image.getWidth(), 200));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int W, H;
            double ratio = (double) image.getWidth() / (double) image.getHeight();

            if (HeaderPanel.this.getWidth() > image.getWidth()) {
                W = HeaderPanel.this.getWidth();
                H = (int) (W / ratio);
            } else {
                H = HeaderPanel.this.getHeight();
                W = (int) (H * ratio);
            }
            int x = HeaderPanel.this.getX() + HeaderPanel.this.getWidth() / 2 - W / 2;
            int y = HeaderPanel.this.getY() + HeaderPanel.this.getHeight() / 2 - H / 2;
            g2d.drawImage(image.getScaledInstance(W, H, Image.SCALE_SMOOTH), x, y, null);
        }
    }
}
