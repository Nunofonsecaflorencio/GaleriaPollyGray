package view.auxiliarpanels;

import model.valueobjects.Artista;
import utility.PollyConstants;

import javax.swing.*;
import java.awt.*;


public class ArtistRender extends JPanel implements ListCellRenderer<Artista> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Artista> list, Artista value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
    
        JLabel lArtistName = new JLabel(value.getNome());
        JLabel lArtistId = new JLabel();
        if (value.getIdArtista() == Integer.MAX_VALUE)
            lArtistId.setText("--");
        else
            lArtistId.setText(value.getIdArtista()+"");

        lArtistId.setHorizontalAlignment(SwingConstants.RIGHT);

        lArtistName.setFont(PollyConstants.getLightFont(14));
        lArtistId.setFont(PollyConstants.getLightFont(12));
        if (isSelected) {
            panel.setBackground(PollyConstants.HIGHLIGHT);
            panel.setBorder(BorderFactory.createLineBorder(PollyConstants.DARK, 2));
            lArtistName.setForeground(Color.BLACK);
            lArtistId.setForeground(Color.BLACK);
        } else {
            panel.setBackground(PollyConstants.DARK);
            panel.setBorder(BorderFactory.createLineBorder(PollyConstants.LIGHT, 2));
            lArtistName.setForeground(Color.WHITE);
            lArtistId.setForeground(Color.WHITE);
        }
        panel.add(lArtistName, gbc);

        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weightx = .1;
        panel.add(lArtistId, gbc);
        panel.setPreferredSize(new Dimension(0, 50));

        return panel;
    }
}