/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.PollyGrayFrame;

/**
 *
 * @author 22K
 */
public class Main {

    PollyGrayFrame janela;
    ArtistsController artistsController;
    ArtsController artsController;
    public Main() {
        janela = new PollyGrayFrame();
        janela.setVisible(true);
        artistsController = new ArtistsController();
        artsController = new ArtsController();
    }

    public static void main(String[] args) {
        Main controller = new Main();

    }
}
