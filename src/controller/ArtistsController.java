package controller;

import model.dao.ArtistaDAO;
import model.valueobjects.Artista;
import utility.PollyConstants;
import utility.PollyImageLoader;
import view.PollyGrayFrame;
import view.panels.ArtistFormPanel;
import view.panels.ArtistsPanel;
import view.panels.ProfilePanel;

import java.util.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class ArtistsController {

    ArtistFormPanel formPanel;
    Artista artistaSelecionado;

    DefaultListModel<Artista> artistModel;

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

        for (int i = 0; i < artistModel.size(); i++) {
            String path = PollyConstants.ARTISTS_IMAGE_DATABASE + artistModel.get(i).getImagem();
            PollyImageLoader.load(path, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

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

                    String path = PollyConstants.ARTISTS_IMAGE_DATABASE + artista.getImagem();
                    PollyImageLoader.load(path, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
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
                if (formPanel.imageChanged()) {
                    artista.setImagem(formPanel.getImagem());
                }
                artista.setContactos(formPanel.getContactos());
                artista.setBiografia(formPanel.getBiografia());

                if (isValidArtist(artista)) {
                    dao.update(artista);

                    if (formPanel.imageChanged()) {
                        PollyConstants.copyFile(formPanel.getFileImagem(),
                                new File(PollyConstants.ARTISTS_IMAGE_DATABASE + artista.getImagem()));

                        String path = PollyConstants.ARTISTS_IMAGE_DATABASE + artista.getImagem();
                        PollyImageLoader.load(path, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                            }
                        });
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
                if (response == JOptionPane.YES_OPTION) {
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
                if (e.getValueIsAdjusting() || artistsPanel.getlArtists().isSelectionEmpty()) {
                    artistsPanel.setButtonPublishEnabled(false);
                    artistsPanel.setButtonProfileEnabled(false);
                    artistsPanel.setButtonUpdateEnabled(false);
                    artistsPanel.setButtonDeleteEnabled(false);
                } else {
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
                String path = PollyConstants.ARTISTS_IMAGE_DATABASE + artistaSelecionado.getImagem();
                BufferedImage imagem = PollyImageLoader.loadedImages.get(path);
                if (imagem == null) {

                    PollyImageLoader.load(path, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                        }
                    });

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
                    if (artistModel.getElementAt(i).getNome().toLowerCase().contains(text)) {
                        filteredArtists.addElement(artistModel.getElementAt(i));
                    }
                }
                artistsPanel.setArtistsListModel(filteredArtists);
            }
        });

    }

    private void removeArtist(Artista artista) {
        artistModel.removeElement(artista);
    }

    private void addArtist(Artista artist) {
        artistModel.insertElementAt(artist, 0);
    }

    private boolean isValidArtist(Artista artista) {
        boolean validName = true, validImage = true, validBirthday = true;
        String ERROS = "";

        if (artista.getNome().isEmpty()) {
            validName = false;
            ERROS += "NOME DO ARTISTA NÃO PODE SER VAZIO. \n";
        } else if (artista.getNome().length() > 45) {
            validName = false;
            ERROS += "NOME DO ARTISTA NÃO PODE TER MAIS DE 45 CARACTERES. \n";
        }
        
        if (artista.getImagem().isEmpty()) {
            validImage = false;
            ERROS += "ADICIONE UMA IMAGEM. \n";
        }
        
        String[] tokens = artista.getDataNascimento().split("-");
        if (artista.getDataNascimento().isEmpty()) {
            validBirthday = false;
            ERROS += "ADICIONE A DATA DE NASCIMENTO. \n";
        } else if (Integer.parseInt(tokens[0]) > 2022 || (Integer.parseInt(tokens[1]) < 0 || Integer.parseInt(tokens[1]) > 12) ||
                (Integer.parseInt(tokens[2]) < 0 || Integer.parseInt(tokens[2]) > 31)) {
            validBirthday = false;
            ERROS += "DATA INVÁLIDA \n";
        }

        if (!(validName && validImage && validBirthday))
            JOptionPane.showMessageDialog(PollyConstants.getFrame(), ERROS);
        
        return validName && validImage && validBirthday;
    }
}
