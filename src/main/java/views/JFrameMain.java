package views;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import models.Service;
import views.panels.*;

/**
 *
 * @author USER
 */
public class JFrameMain extends javax.swing.JFrame {

    /**
     * Creates new form JFrameMain
     */
    public JFrameMain() {
        this.setIconImage(new ImageIcon(getClass().getResource("/icono.png")).getImage());
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        initComponents();
        this.setVisible(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setBackground(new Color(244, 225, 208));
        initLogin();
    }

    public void initLogin() {
        JPanelLogin jPanelLogin = new JPanelLogin(this);
        jPanelLogin.setLocation(0, 0);
        jPanelLogin.setSize(this.getSize());
        this.setContentPane(jPanelLogin);
        this.revalidate();
        this.repaint();
    }

    public void initMain() {
        JPanelMain jPanelMain = new JPanelMain(this);
        jPanelMain.setLocation(0, 0);
        jPanelMain.setSize(this.getSize());
        this.setContentPane(jPanelMain);
        jPanelMain.initPanels();
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1020, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.isAltDown() && evt.getKeyCode() == KeyEvent.VK_F4) {
            evt.consume();
        }
    }//GEN-LAST:event_formKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private Service service;
}
