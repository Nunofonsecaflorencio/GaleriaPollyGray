package controller;

import model.dao.ArteDAO;
import model.valueobjects.Arte;
import model.valueobjects.Artista;
import utility.PollyConstants;
import utility.PollyImageLoader;
import view.PollyGrayFrame;
import view.auxiliarpanels.FeedArtRenderer;
import view.panels.DetailPanel;
import view.panels.ExplorerPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ExplorerController {
    JPanel[] feedPanels;

    DefaultListModel<Arte> arts;

    ExplorerPanel explorerPanel;

    int counter = 0;

    MouseListener clickToArtListener;

    Artista artista;

    DetailPanel detailPanel;

    public ExplorerController(DefaultListModel<Arte> artesModel, ActionListener listeners[]) {
        /*
            Containers
         */
        PollyGrayFrame frame = PollyConstants.getFrame();
        explorerPanel = frame.getExplorerPanel();
        feedPanels = explorerPanel.getFeedPanels();

        /*
            Data
         */
        arts = artesModel;




        clickToArtListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Arte arte = ((FeedArtRenderer)e.getSource()).getArte();
                PollyConstants.arteSelecionada = arte;
                detailPanel = new DetailPanel(PollyImageLoader.loadedImages.get(PollyConstants.ARTS_IMAGE_DATABASE + arte.getImagem()));
                detailPanel.addUpdateActionListener(listeners[0]);
                detailPanel.addDeleteActionListener(listeners[1]);
                detailPanel.addComprarActionListener(listeners[2]);


                if (arte.getIdArte() != -1) //Significa que ainda não temos toda informação que esta na tebela (Restart)
                artista = ArteDAO.getArtistByArt(arte.getIdArte());

                detailPanel.setTitulo(arte.getTitulo());
                detailPanel.setNomeArtista((arte.getIdArte() == -1)? "--" : artista.getNome());
                detailPanel.setDataPublicacao((arte.getIdArte() == -1)? "--" :PollyConstants.reformatDate(arte.getDataPublicacao()));
                detailPanel.setPreco(arte.getPreco());
                detailPanel.setUnidades(arte.getUnidades());
                detailPanel.setDescricao(arte.getDescricao());

                detailPanel.setEnableBComprar(arte.getUnidades() != 0);

                PollyConstants.getFrame().getCardsPanel().add(detailPanel, PollyConstants.DETAIL_CARD);
                PollyConstants.getFrame().go(PollyConstants.DETAIL_CARD);
                PollyConstants.lastPanel = detailPanel;
            }
        };


        // IDEAL
        for (int i = 0; i < arts.getSize(); i++) {
            scheduleLoading(artesModel.get(i));
        }
    }

    public void scheduleLoading(Arte art){
        PollyImageLoader.load(PollyConstants.ARTS_IMAGE_DATABASE + art.getImagem(),
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showArt(art);
                    }
                });
    }

    public void showArt(Arte art){
        addComponentToFeed(new FeedArtRenderer(art,
                PollyImageLoader.loadedImages.get(PollyConstants.ARTS_IMAGE_DATABASE + art.getImagem()),
                clickToArtListener));
    }

    public void addComponentToFeed(Component c){
        int index = counter % feedPanels.length;
        feedPanels[index].add(c);
        feedPanels[index].revalidate();
        counter++;
    }
}
