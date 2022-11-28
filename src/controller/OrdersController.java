package controller;

import model.dao.ArtistaDAO;
import model.dao.CompraDAO;
import model.valueobjects.Artista;
import utility.PollyConstants;
import view.PollyGrayFrame;
import view.panels.ArtistFormPanel;
import view.panels.ArtistsPanel;
import view.panels.OrdersPanel;
import view.panels.ProfilePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrdersController {


    DefaultTableModel model;

    public OrdersController() {
        /*
            Containers
         */
        PollyGrayFrame frame = PollyConstants.getFrame();
        OrdersPanel ordersPanel = frame.getOrdersPanel();

        /*
            Data
         */
        ordersPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                CompraDAO dao = new CompraDAO();
                model = dao.read();
                ordersPanel.setOrdersTableModel(model);
            }
        });



        /*
            Create
         */
        ActionListener printReportListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Imprimir Relatório");

            }
        };




        /*
            Others
         */



        /*
            Filtering
         */
        ordersPanel.addTypingFilterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = ordersPanel.getFilterText().toLowerCase();
                DefaultTableModel filteredOrders = new DefaultTableModel();
                filteredOrders.setColumnIdentifiers(new String[]{
                        "NOME DO CLIENTE", "ID DA ARTE", "DATA", "UNIDADES", "PREÇO TOTAL"
                });

                Vector data = model.getDataVector();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).toString().toLowerCase().contains(text)){
                        filteredOrders.addRow((Vector) data.get(i));
                    }
                }
                ordersPanel.setOrdersTableModel(filteredOrders);
            }
        });


    }

}
