package utility;

import javax.swing.*;
import java.awt.*;

public class PollyDialogs {

    public static void showMessageDialog(String message){
        // Frame = Polly.getFrame || modal = true
    }

    public static void showErrorDialog(String message){

    }



    private class GenericDialog extends JDialog{

        public GenericDialog(Frame owner, boolean modal) {
            super(owner, modal);
        }
    }
}
