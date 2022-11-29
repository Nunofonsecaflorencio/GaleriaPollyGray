package utility;


import model.valueobjects.Arte;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PollyImageLoader {

    public static Map<String, BufferedImage> loadedImages;

    static  {
        loadedImages = new ConcurrentHashMap<>();
    }

    public static void load(String path, ActionListener ifDone){


        new Worker(path, ifDone).execute();

    }


    static class Worker extends SwingWorker {
        String path;
        ActionListener ifDone;
        BufferedImage image;

        public Worker(String path, ActionListener ifDone) {
            this.path = path;
            this.ifDone = ifDone;
        }

        @Override
        protected Object doInBackground() throws Exception {
            if (!loadedImages.containsKey(path))
                image =  ImageIO.read(new File(path));
            return null;
        }

        @Override
        protected void done() {
            if (!loadedImages.containsKey(path)){
                loadedImages.put(path, image);
            }

            ifDone.actionPerformed(null);
        }
    }
}
