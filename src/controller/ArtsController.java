package controller;

import model.dao.ArteDAO;
import model.dao.CategoriaDAO;
import model.entity.Arte;
import model.entity.Artista;
import model.entity.Categoria;
import utility.PollyConstants;
import view.PollyGrayFrame;
import view.panels.ArtistFormPanel;
import view.panels.ArtistsPanel;
import view.panels.ExplorerPanel;
import view.panels.PublishPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static javax.swing.JOptionPane.showConfirmDialog;
import view.panels.OrderingPanel;

public class ArtsController {

    DefaultListModel<Arte> artesModel;
    DefaultComboBoxModel<Categoria> categoriaModel;
    PublishPanel publishPanel;
    JFileChooser fileChooser;
    OrderingPanel orderingPanel;
    ExplorerController explorerController;

    public ArtsController() {
        /*
            Containers
         */
        PollyGrayFrame frame = PollyConstants.getFrame();
        ArtistsPanel artistsPanel = frame.getArtistsPanel();


        /*
            Data
         */
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaModel = categoriaDAO.read();
        ArteDAO arteDAO = new ArteDAO();
        artesModel = arteDAO.read();



        fileChooser = PollyConstants.createImageFileChooser();


        /*
            Create
         */
        ActionListener createArtListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Arte arte = new Arte();
                Artista artista = (Artista) artistsPanel.getlArtists().getSelectedValue();

                arte.setIdArte(artista.getIdArtista()); // NOTE: Dont send to database (Let it auto increment)
                arte.setTitulo(publishPanel.getTitulo());
                arte.setUnidades(publishPanel.getUnidades());
                arte.setPreco(publishPanel.getPreco());
                arte.setImagem((fileChooser.getSelectedFile() != null ) ? fileChooser.getSelectedFile().getName() : ""); // Check if not null
                arte.setDescricao(publishPanel.getDescricao());
                arte.setDataPublicacao(((Categoria)publishPanel.getCategoria()).getIdCategoria()+""); // NOTE: Dont send to database (Let default now() do the djob)
                arte.setEsgotado(false);

                if (isArtValid(arte)) {

                    PollyConstants.copyFile(fileChooser.getSelectedFile(),
                            new File(PollyConstants.ARTS_IMAGE_DATABASE + arte.getImagem()));
                    arteDAO.create(arte);
                    arte.setIdArte(-1);

                    artesModel.insertElementAt(arte, 0);
                    explorerController.addArtToWait(arte);
                    explorerController.processWaitingImages();


                    PollyConstants.getFrame().goFromTo(publishPanel, PollyConstants.ARTISTS_CARD);
                }
            }
        };



        artistsPanel.addPublishActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publishPanel = new PublishPanel();
                publishPanel.setCategoriesModel(categoriaModel);
                publishPanel.addPublishActionListener(createArtListener);
                publishPanel.addImageActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int response = fileChooser.showOpenDialog(publishPanel);

                        if (response == JFileChooser.APPROVE_OPTION){
                            publishPanel.setImageText(fileChooser.getSelectedFile().getPath());
                        }
                    }
                });

                PollyConstants.getFrame().getCardsPanel().add(publishPanel, PollyConstants.PUBLISH_CARD);
                PollyConstants.getFrame().go(PollyConstants.PUBLISH_CARD);
                PollyConstants.lastPanel = publishPanel;
            }
        });

        ActionListener updateArt= new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Arte arte = PollyConstants.arteSelecionada;
                String oldImage = arte.getImagem();

                arte.setTitulo(publishPanel.getTitulo());
                arte.setUnidades(publishPanel.getUnidades());
                arte.setPreco(publishPanel.getPreco());
                if (fileChooser.getSelectedFile() != null){
                    arte.setImagem(fileChooser.getSelectedFile().getName());
                }
                arte.setDescricao(publishPanel.getDescricao());

                if (isArtValid(arte)) {

                    if (fileChooser.getSelectedFile() != null){
                        PollyConstants.deleteFile(new File(PollyConstants.ARTS_IMAGE_DATABASE + oldImage));
                        PollyConstants.copyFile(fileChooser.getSelectedFile(),
                                new File(PollyConstants.ARTS_IMAGE_DATABASE + arte.getImagem()));

                        explorerController.addArtToWait(arte);
                        explorerController.processWaitingImages();
                    }

                    arteDAO.update(arte);


                    PollyConstants.getFrame().goFromTo(publishPanel, PollyConstants.EXPLORER_CARD);
                }
            }
        };

        ActionListener updateArtListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publishPanel = new PublishPanel(PollyConstants.arteSelecionada);
                publishPanel.setCategoriesModel(categoriaModel);
                publishPanel.addPublishActionListener(updateArt);

                PollyConstants.getFrame().getCardsPanel().add(publishPanel, PollyConstants.PUBLISH_CARD);
                PollyConstants.getFrame().go(PollyConstants.PUBLISH_CARD);
                PollyConstants.lastPanel = publishPanel;
            }
        };


        ActionListener deleteArtListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Arte arte = PollyConstants.arteSelecionada;
                arteDAO.delete(arte);
                removeArt(arte);
                PollyConstants.deleteFile(new File(PollyConstants.ARTS_IMAGE_DATABASE + arte.getImagem()));

                PollyConstants.getFrame().goFromTo(publishPanel, PollyConstants.EXPLORER_CARD);
            }
        };
        
        ActionListener buyArtListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderingPanel = new OrderingPanel();
                //JOptionPane.showConfirmDialog(null, orderingPanel, "Confirmação de Compra", JOptionPane.PLAIN_MESSAGE);
                
                orderingPanel.addConfirmActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("DEBUG");
                    }
                });
            }
            
        };

        
        ActionListener[] listeners = new ActionListener[]{
                updateArtListener,
                deleteArtListener,
                buyArtListener
        };

        explorerController = new ExplorerController(artesModel, listeners);

    }

    private void removeArt(Arte arte) {
        artesModel.removeElement(arte);
        JPanel[] feedPanel = PollyConstants.getFrame().getExplorerPanel().getFeedPanels();
        for (int i = 0; i < feedPanel.length; i++) {
            feedPanel[i].removeAll();
        }

        explorerController.counter = 0;
        for (int i = 0; i < artesModel.size(); i++) {
            explorerController.showArt(artesModel.elementAt(i));
        }
    }

    private boolean isArtValid(Arte arte) {
        // TODO: Validade This art
        return true;
    }

}
