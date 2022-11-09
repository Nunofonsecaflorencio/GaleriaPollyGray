package utility;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleButton extends JButton {

    public SimpleButton(String text, Icon icon, Color bg, Color hv) {
        super(text, icon);
        setUI(new BasicButtonUI());

        if (bg != null)
            setBackground(bg);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hv != null)
                    setBackground(hv);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (bg != null)
                    setBackground(bg);
            }
        });

    }

    public SimpleButton(String text, Icon icon) {
        this(text, icon, null, null);
    }

    public SimpleButton(String text) {
        this(text, null);
    }

    public SimpleButton(Icon icon) {
        this(null, icon);
    }

    public SimpleButton() {
        this(null, null);
    }
}
