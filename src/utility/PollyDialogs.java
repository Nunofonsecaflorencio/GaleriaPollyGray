package utility;

import javax.swing.*;
import java.awt.*;

public class PollyDialogs {

    public PollyDialogs() {
        //test
        JOptionPane pane = new JOptionPane("Your Message", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        getComponents(pane);
        pane.setBackground(PollyConstants.LIGHT);
        
//        JDialog p = new JDialog();
//        p = pane.createDialog(this, "Message");
//        p.setVisible(true);
        
    }
    
    public static void getComponents(Container c) {
        Component[] m = c.getComponents();
        
        for (int i = 0; i < m.length; i++) {
            if ("java.swing.JPanel".equals(m[i].getClass().getName()))
                m[i].setBackground(PollyConstants.HIGHLIGHT);
            
            if (c.getClass().isInstance(m[i]))
                getComponents((Container)m[i]);   
        }
    }
}
