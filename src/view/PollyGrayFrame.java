/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author 22K
 */
import controller.OrdersController;
import utility.PollyConstants;
import utility.PollyDatabase;
import utility.SimpleButton;
import view.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class PollyGrayFrame extends JFrame {

    CardLayout cards;
    JPanel cardsPanel;
    ExplorerPanel pExplorer;
    ArtistsPanel pArtists;
    OrdersPanel pOrders;

    public PollyGrayFrame() {
        PollyConstants.setFrame(this);
        initComponents();
        configFrame();


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    PollyDatabase.getConnection().close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }

    private void configFrame() {
        JPanel pGlobal = new JPanel(new BorderLayout());
        pGlobal.setPreferredSize(new Dimension(1080, 720));
        setMinimumSize(new Dimension(955, 600));

        pGlobal.add(getSideMenuPanel(), BorderLayout.WEST);
        pGlobal.add(getMainPanel(), BorderLayout.CENTER);

        add(pGlobal);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Galeria Polly Gray");
        setIconImage(PollyConstants.icon("favicon.png").getImage());
    }

    private void initComponents() {
        cards = new CardLayout();
        pExplorer = new ExplorerPanel();
        pArtists = new ArtistsPanel();
        pOrders = new OrdersPanel();
    }

    private JPanel getMainPanel() {
        cardsPanel = new JPanel();
        cardsPanel.setLayout(cards);
        cardsPanel.add(pExplorer, PollyConstants.EXPLORER_CARD);
        cardsPanel.add(pArtists, PollyConstants.ARTISTS_CARD);
        cardsPanel.add(pOrders, PollyConstants.ORDERS_CARD);

        return cardsPanel;
    }

    private JPanel getSideMenuPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PollyConstants.LIGHT);
        panel.setLayout(new GridLayout(11, 1, 0, 2));

        panel.add(createMenuButton("explore.png", "Explorar"));
        panel.add(createMenuButton("createArtist.png", "Artistas"));
        panel.add(createMenuButton("shopping_cart.png", "Compras"));

        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(200, 0));
        panel.add(lbl);

        return panel;
    }

    private SimpleButton createMenuButton(String iconName, String text) {
        SimpleButton btn = new SimpleButton(text,
                PollyConstants.icon(iconName),
                PollyConstants.LIGHT, PollyConstants.HIGHLIGHT);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cards.show(cardsPanel, text);
            }
        });
        btn.setBorder(BorderFactory.createMatteBorder(1, 8, 1, 1, PollyConstants.HIGHLIGHT));
        btn.setFont(PollyConstants.getBoldFont(15));

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popLastPanel();
            }

        });

        return btn;
    }


    /*
        For Controller Objects
     */
    public CardLayout getCards() {
        return cards;
    }

    public JPanel getCardsPanel() {
        return cardsPanel;
    }

    public ArtistsPanel getArtistsPanel() {
        return pArtists;
    }

    public void go(String page) {
        cards.show(cardsPanel, page);
    }

    public void popLastPanel() {
        if (PollyConstants.lastPanel != null) {
            cardsPanel.remove(PollyConstants.lastPanel);
        }
        //System.out.println("[DEBUG] Panels In Card: " + cardsPanel.getComponents().length);
    }

    public void goFromTo(JPanel from, String page) {
        PollyConstants.lastPanel = from;
        go(page);
        popLastPanel();
    }

    public ExplorerPanel getExplorerPanel() {
        return pExplorer;
    }

    public OrdersPanel getOrdersPanel() {
        return pOrders;
    }
}
