/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem;

import java.awt.event.*;
/**
 *
 * @author Xuan
 */
public class SidePanelButton extends javax.swing.JPanel {

    /**
     * Creates new form sidePanelButton
     */
    public SidePanelButton() {
        initComponents();
    }
    
    public SidePanelButton(String title, MouseAdapter mouseAdapter){
        initComponents();
        btnLabel.setText(title);
        this.addMouseListener(mouseAdapter);
    }
    
    private boolean isActived = false;
    
    public void active(){
        isActived = true;
        Theme.highlightBackground(this);
    }
    
    public void reset(){
        isActived = false;
        Theme.resetBackground(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLabel = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));

        setBackground(Theme.getFgColor());
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(2147483647, 50));
        setPreferredSize(new java.awt.Dimension(400, 50));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        btnLabel.setBackground(new java.awt.Color(204, 204, 204));
        btnLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLabel.setForeground(new java.awt.Color(204, 204, 204));
        btnLabel.setText("Selection");
        add(btnLabel, java.awt.BorderLayout.CENTER);
        add(filler5, java.awt.BorderLayout.WEST);
        add(filler6, java.awt.BorderLayout.EAST);
        add(filler7, java.awt.BorderLayout.NORTH);
        add(filler8, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        Theme.highlightBackground(this);
    }//GEN-LAST:event_formMouseEntered

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        if(!isActived){
            Theme.resetBackground(this);
        }
    }//GEN-LAST:event_formMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnLabel;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    // End of variables declaration//GEN-END:variables
}
