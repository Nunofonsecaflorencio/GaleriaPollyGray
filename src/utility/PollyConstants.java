package utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;

import model.entity.Arte;
import model.entity.Artista;
import view.PollyGrayFrame;

public class PollyConstants {


    public static Color BROWN = new Color(118, 65, 19);
    public static Color LIGHT = new Color(255, 255, 255);
    public static Color HIGHLIGHT = new Color(253, 234, 196);
    public static Color DARK = new Color(26, 24, 24);
    public static Color LIGHT_CYAN = new Color(243, 246, 251);

    public static Font lightFont, boldFont;

    public static String ASSETSPATH = "src\\assets\\";
    public static String IMAGE_DATABASE = ASSETSPATH + "img_database\\";
    public static String ARTISTS_IMAGE_DATABASE = IMAGE_DATABASE + "artists\\";
    public static String ARTS_IMAGE_DATABASE = IMAGE_DATABASE  + "arts\\";
    
    static HashMap<String, BufferedImage> images = new HashMap<>();
    
    static PollyGrayFrame frame;
    public static JPanel lastPanel;
    
    public static String EXPLORER_CARD = "Explorar";
    public static String ARTISTS_CARD = "Artistas";
    public static String PUBLISH_CARD = "Publicar";
    public static String ARTISTFORM_CARD = "Form";
    public static String PROFILE_CARD = "Perfil";
    public static final String DETAIL_CARD = "Detalhe";
    
    
    public static PollyGrayFrame getFrame() {
        return frame;
    }

    public static void setFrame(PollyGrayFrame frame) {
        PollyConstants.frame = frame;
    }


    public static Font getBoldFont(float size){
        if (boldFont.getSize() == size)
            return boldFont;
        return boldFont.deriveFont(size);
    }

    public static Font getLightFont(float size){
        if (lightFont.getSize() == size)
            return lightFont;
        return lightFont.deriveFont(size);
    }

    public static ImageIcon icon(String name){
        return new ImageIcon(PollyConstants.ASSETSPATH + "icons\\" + name);
    }

    public static BufferedImage readImage(String imageName) {
        BufferedImage image = null;
        
        if (images.containsKey(imageName))
            return images.get(imageName);
        
        try {
            System.out.println("READING " + imageName);
            image = ImageIO.read(new File(IMAGE_DATABASE + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("READING DONE " + imageName);
        
        images.put(imageName, image);
        return image;
    }

    static {
        try {
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new File(ASSETSPATH  + "fonts\\futur.ttf"));
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(ASSETSPATH  + "fonts\\futur.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        try {
            lightFont = Font.createFont(Font.TRUETYPE_FONT, new File(ASSETSPATH  + "fonts\\futuralight.ttf"));
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(ASSETSPATH  + "fonts\\futuralight.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }


    }

    public static String formatDate(String s){
        String[] tokens = s.split(" / ");
        return tokens[2] + "-" + tokens[1] + "-" + tokens[0];
    }

    public static String reformatDate(String s){
        String[] tokens = s.split("-");
        return tokens[2] + " / " + tokens[1] + " / " + tokens[0];
    }

    public static JFileChooser createImageFileChooser() {

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        return  fileChooser;
    }

    public static void copyFile(File from, File to){
        try {
            Files.copy(from.toPath(),
                    to.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File f){
        try {
            Files.delete(f.toPath());;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JLabel createLabel(String s, Font font) {
        JLabel l = new JLabel(s);
        l.setFont(font);
        return l;
    }
}
