package controller;

import model.dao.ArtistaDAO;
import model.dao.CategoriaDAO;
import model.entity.Arte;
import model.entity.Artista;
import model.entity.Categoria;
import utility.PollyConstants;
import view.PollyGrayFrame;
import view.panels.ArtistFormPanel;
import view.panels.ArtistsPanel;
import view.panels.ProfilePanel;
import view.panels.PublishPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ArtistsController{

    ArtistFormPanel formPanel;
    Artista artistaSelecionado;

    DefaultListModel<Artista> artistModel;

    Map<Artista, BufferedImage> loadedImages;
    Queue<Artista> wainting;

    public ArtistsController() {
        /*
            Containers
         */
        PollyGrayFrame frame = PollyConstants.getFrame();
        ArtistsPanel artistsPanel = frame.getArtistsPanel();

        /*
            Data
         */
        ArtistaDAO dao = new ArtistaDAO();
        artistModel = dao.read();
        artistsPanel.setArtistsListModel(artistModel);

        /*
            Images
         */
        loadedImages = new ConcurrentHashMap<>();
        wainting = new LinkedList<>();
        /*
            Create
         */
        ActionListener createArtistListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Artista artista = new Artista();

                artista.setIdArtista(Integer.MAX_VALUE);
                artista.setNome(formPanel.getNome());
                artista.setDataNascimento(PollyConstants.formatDate(formPanel.getDataNascimento()));
                artista.setImagem(formPanel.getImagem());
                artista.setContactos(formPanel.getContactos());
                artista.setBiografia(formPanel.getBiografia());

                if (isValidArtist(artista)) {
                    dao.create(artista);
                    addArtist(artista);

                    PollyConstants.copyFile(formPanel.getFileImagem(),
                            new File(PollyConstants.ARTISTS_IMAGE_DATABASE + artista.getImagem()));


                    PollyConstants.getFrame().goFromTo(formPanel, PollyConstants.ARTISTS_CARD);

                }
            }
        };

        artistsPanel.addCreateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formPanel = new ArtistFormPanel();
                formPanel.addConfirmarActionListener(createArtistListener);

                PollyConstants.getFrame().getCardsPanel().add(formPanel, PollyConstants.ARTISTFORM_CARD);
                PollyConstants.getFrame().go(PollyConstants.ARTISTFORM_CARD);
                PollyConstants.lastPanel = formPanel;
            }
        });


        /*
            Update
         */
        ActionListener updateArtistListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Artista artista = artistaSelecionado;

                artista.setNome(formPanel.getNome());
                artista.setDataNascimento(PollyConstants.formatDate(formPanel.getDataNascimento()));
                if (formPanel.imageChanged())
                    artista.setImagem(formPanel.getImagem());
                artista.setContactos(formPanel.getContactos());
                artista.setBiografia(formPanel.getBiografia());

                if (isValidArtist(artista)) {
                    dao.update(artista);

                    if (formPanel.imageChanged()) {
                        PollyConstants.copyFile(formPanel.getFileImagem(),
                                new File(PollyConstants.ARTISTS_IMAGE_DATABASE + artista.getImagem()));
                    }

                    PollyConstants.getFrame().goFromTo(formPanel, PollyConstants.ARTISTS_CARD);
                }
            }
        };

        artistsPanel.addUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formPanel = new ArtistFormPanel(artistaSelecionado);
                formPanel.addConfirmarActionListener(updateArtistListener);
                PollyConstants.getFrame().getCardsPanel().add(formPanel, PollyConstants.ARTISTFORM_CARD);
                PollyConstants.getFrame().go(PollyConstants.ARTISTFORM_CARD);
                PollyConstants.lastPanel = formPanel;
            }
        });


        /*
            Delete
         */
        artistsPanel.addDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Artista artista = (Artista) artistsPanel.getlArtists().getSelectedValue();
                int response = JOptionPane.showConfirmDialog(artistsPanel,
                        "Remover " + artista.getNome() + "?");
                if (response == JOptionPane.YES_OPTION){
                    dao.delete(artista);
                    removeArtist(artista);
                    PollyConstants.deleteFile(new File(PollyConstants.ARTISTS_IMAGE_DATABASE + artista.getImagem()));
                }
            }
        });

        /*
            Others
         */

        artistsPanel.addArtistListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() || artistsPanel.getlArtists().isSelectionEmpty()){
                    artistsPanel.setButtonPublishEnabled(false);
                    artistsPanel.setButtonProfileEnabled(false);
                    artistsPanel.setButtonUpdateEnabled(false);
                    artistsPanel.setButtonDeleteEnabled(false);
                }else{
                    artistsPanel.setButtonPublishEnabled(true);
                    artistsPanel.setButtonProfileEnabled(true);
                    artistsPanel.setButtonUpdateEnabled(true);
                    artistsPanel.setButtonDeleteEnabled(true);


                    artistaSelecionado = (Artista) artistsPanel.getlArtists().getSelectedValue();
                }
            }
        });


        artistsPanel.addProfileActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage imagem = loadedImages.get(artistaSelecionado);
                if (imagem == null){
                    addArtistaToWait(artistaSelecionado);
                    processWaitingImages();
                    return;
                }
                ProfilePanel profile = new ProfilePanel(artistaSelecionado, imagem);
                PollyConstants.getFrame().getCardsPanel().add(profile, PollyConstants.PROFILE_CARD);
                PollyConstants.getFrame().go(PollyConstants.PROFILE_CARD);
                PollyConstants.lastPanel = profile;
            }
        });


        /*
            Filtering
         */
        artistsPanel.addTypingFilterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = artistsPanel.getFilterText().toLowerCase();
                DefaultListModel<Artista> filteredArtists = new DefaultListModel<>();
                for (int i = 0; i < artistModel.getSize(); i++) {
                    if (artistModel.getElementAt(i).getNome().toLowerCase().contains(text)){
                        filteredArtists.addElement(artistModel.getElementAt(i));
                    }
                }
                artistsPanel.setArtistsListModel(filteredArtists);
            }
        });


    }

    public void addArtistaToWait(Artista a){
        wainting.add(a);
    }
    public void processWaitingImages(){
        while (!wainting.isEmpty()){
            Artista a = wainting.poll();
            Worker worker = new Worker(a);
            worker.execute();
        }
    }

    public BufferedImage loadImage(Artista artista) throws IOException {
        BufferedImage image = ImageIO.read(new File(PollyConstants.ARTISTS_IMAGE_DATABASE + artista.getImagem()));
        loadedImages.put(artista, image);
        return image;
    }

    class Worker extends SwingWorker{
        Artista artista;

        public Worker(Artista artista) {
            this.artista = artista;
        }

        @Override
        protected Object doInBackground() throws Exception {
            loadImage(artista);
            return null;
        }
    }

    private void removeArtist(Artista artista) {
        artistModel.removeElement(artista);
    }

    private boolean isValidArtist(Artista artista) {
        // TODO: Validate Artist
        return true;
    }

    private void addArtist(Artista artist) {
        artistModel.insertElementAt(artist, 0);
    }

}