package views;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private Service service;
}
