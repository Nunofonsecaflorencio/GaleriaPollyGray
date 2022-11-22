package controller;

import model.dao.ArteDAO;
import model.entity.Arte;
import model.entity.Artista;
import utility.PollyConstants;
import view.PollyGrayFrame;
import view.auxiliarpanels.FeedArtRenderer;
import view.panels.ArtistFormPanel;
import view.panels.DetailPanel;
import view.panels.ExplorerPanel;
import view.panels.PublishPanel;

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

    Map<Arte, BufferedImage> loadedImages;
    Queue<Arte> wainting;
    DefaultListModel<Arte> arts;

    ExplorerPanel explorerPanel;

    int counter = 0;

    MouseListener clickToArtListener;

    Artista artista;

    public ExplorerController(DefaultListModel<Arte> artesModel, ActionListener updateArtListener) {
        /*
            Containers
         */
        PollyGrayFrame frame = PollyConstants.getFrame();
        explorerPanel = frame.getExplorerPanel();
        feedPanels = explorerPanel.getFeedPanels();

        loadedImages = new ConcurrentHashMap<>();
        wainting = new LinkedList<>();

        /*
            Data
         */
        arts = artesModel;




        clickToArtListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Arte arte = ((FeedArtRenderer)e.getSource()).getArte();
                PollyConstants.arteSelecionada = arte;
                DetailPanel detailPanel = new DetailPanel(loadedImages.get(arte));
                detailPanel.addUpdateActionListener(updateArtListener);

                artista = ArteDAO.getArtistByArt(arte.getIdArte());

                detailPanel.setTitulo(arte.getTitulo());
                detailPanel.setNomeArtista(artista.getNome());
                detailPanel.setDataPublicacao(PollyConstants.reformatDate(arte.getDataPublicacao()));
                detailPanel.setPreco(arte.getPreco());
                detailPanel.setUnidades(arte.getUnidades());
                detailPanel.setDescricao(arte.getDescricao());

                PollyConstants.getFrame().getCardsPanel().add(detailPanel, PollyConstants.DETAIL_CARD);
                PollyConstants.getFrame().go(PollyConstants.DETAIL_CARD);
                PollyConstants.lastPanel = detailPanel;
            }
        };

        for (int i = 0; i < arts.getSize(); i++) {
            addArtToWait(arts.elementAt(i));
        }

        processWaitingImages();
    }

    public void addArtToWait(Arte art){
        wainting.add(art);
    }
    public void processWaitingImages(){
        while (!wainting.isEmpty()){
            Arte art = wainting.poll();
            Worker worker = new Worker(art);
            worker.execute();
        }
    }

    public void showArt(Arte art){
        addComponentToFeed(new FeedArtRenderer(art, loadedImages.get(art), clickToArtListener));
    }

    public void addComponentToFeed(Component c){
        int index = counter % feedPanels.length;
        feedPanels[index].add(c);
        feedPanels[index].revalidate();
        counter++;
    }


    public BufferedImage loadImage(Arte art) throws IOException {
        BufferedImage image = ImageIO.read(new File(PollyConstants.ARTS_IMAGE_DATABASE + art.getImagem()));
        loadedImages.put(art, image);
        return image;
    }

    class Worker extends SwingWorker{
        Arte art;

        public Worker(Arte art) {
            this.art = art;
        }

        @Override
        protected Object doInBackground() throws Exception {
            loadImage(art);
            return null;
        }

        @Override
        protected void done() {
            showArt(art);
        }
    }
}
