package view.auxiliarpanels;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import utility.PollyConstants;

/**
 * @author Belars Wonder
 */
public class CustomJTable extends JTable {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    };

    public CustomJTable() {
        setBackground(PollyConstants.LIGHT);
        setSelectionBackground(PollyConstants.LIGHT);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setBorder(null);
        // Header
        getTableHeader().setPreferredSize(new Dimension(0, 35));
        getTableHeader().setDefaultRenderer(new Header());
        getTableHeader().setForeground(Color.WHITE);

        // Cells
        setDefaultRenderer(Object.class, new Cell());
        setRowHeight(30);
    }
    
    private class Header extends DefaultTableCellRenderer {
        
        private static final long serialVersionUID = 1L;
        
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            Component c = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            
            c.setBackground(PollyConstants.BROWN);
            c.setFont(PollyConstants.getBoldFont(15));
            c.setForeground(Color.WHITE);
            return c;
        }
        
    }
    
    private class Cell extends DefaultTableCellRenderer {
        
        private static final long serialVersionUID = 1L;
        
        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int row, int column) {
            Component c = super.getTableCellRendererComponent(jtable, o, bln, bln1, row, column);
            
            if (isCellSelected(row, column)) {
                c.setBackground(PollyConstants.HIGHLIGHT);
                c.setForeground(PollyConstants.DARK);
            } else {
                if (row % 2 == 0) {
                    c.setBackground(PollyConstants.DARK);
                } else {
                    c.setBackground(Color.DARK_GRAY);
                }
                c.setForeground(PollyConstants.LIGHT);
            }
            setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

            return c;
        }
        
    }
    
}
