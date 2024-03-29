//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package ticket;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.awt.print.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 *
 * @author  Adrian
 */
public class JTicketContainer extends javax.swing.JPanel {

    protected int H_GAP = 8;
    protected int V_GAP = 8;
    
    /** Creates new form JTicketContainer */
    public JTicketContainer() {
        initComponents();
        setLayout(null);
    }

    public Dimension getPreferredSize() { 
        
        Insets ins = getInsets();
        int iMaxx = 0;
        int iMaxy = ins.top + V_GAP;
        int n = getComponentCount();
        for(int i = 0; i < n; i++) {
            Component comp = getComponent(i);
            Dimension dc = comp.getPreferredSize();
            if (dc.width > iMaxx) {
                iMaxx = dc.width;
            }
            iMaxy += V_GAP + dc.height;
        }

        return new Dimension(iMaxx + 2 * H_GAP + ins.left + ins.right, iMaxy + ins.bottom);
    }

    public Dimension getMaximumSize() {
      return getPreferredSize();
    }

    public Dimension getMinimumSize() {
      return getPreferredSize();
    }

    public void doLayout() {
        Insets ins = getInsets();
        int x = ins.left + H_GAP;
        int y = ins.top + V_GAP;

        int n = getComponentCount();
        for(int i = 0; i < n; i++) {
            Component comp = getComponent(i);
            Dimension dc = comp.getPreferredSize();
            
            comp.setBounds(x, y, dc.width, dc.height);
            y += V_GAP + dc.height;
        }
    }
    
    public void addTicket(JTicket ticket) {
                
        add(ticket);
        
        doLayout();
        revalidate();
        scrollRectToVisible(new Rectangle(0, getPreferredSize().height - 1, 1, 1));
    }
    
    public void removeAllTickets() {
        
        removeAll();
        
        doLayout();
        revalidate();
        scrollRectToVisible(new Rectangle(0, 0, 1, 1));   
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
