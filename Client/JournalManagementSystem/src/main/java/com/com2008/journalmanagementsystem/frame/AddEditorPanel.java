/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import com.com2008.journalmanagementsystem.model.Account;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.com2008.journalmanagementsystem.model.Editor;
import com.com2008.journalmanagementsystem.model.EditorOnBoard;
import com.com2008.journalmanagementsystem.model.Journal;
import com.com2008.journalmanagementsystem.util.database.Database;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jordan Marsh
 */
public class AddEditorPanel extends javax.swing.JPanel {
    
    String email;
    /**
     * Creates new form ProfilePanel
     */
    public AddEditorPanel() {
        initComponents();
    }


    public AddEditorPanel(String email) {
        initComponents();
        this.email = email;
        
    }
    
    public boolean verify(String is,String ne){
        String issn = is;
        String newEmail = ne;

        if(issn.isEmpty() || newEmail.isEmpty()){
            JOptionPane.showMessageDialog(null, "Please complete all the fields.", "Register", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String patternString = "^ISSN [0-9]{4}-[0-9]{3}[0-9X]$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(issn);
        if(!matcher.matches()){
            JOptionPane.showMessageDialog(null, "Invalid ISSN.", "Register", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainScrollPane = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        innerPanel = new javax.swing.JPanel();
        addEditorPanel = new javax.swing.JPanel();
        addEditorLabel = new javax.swing.JLabel();
        addEditorMainPanel = new javax.swing.JPanel();
        addEditorInnerPanel = new javax.swing.JPanel();
        issnPanel = new javax.swing.JPanel();
        issnLabelPanel = new javax.swing.JPanel();
        issnLabel = new javax.swing.JLabel();
        filler40 = new javax.swing.Box.Filler(new java.awt.Dimension(4, 0), new java.awt.Dimension(4, 0), new java.awt.Dimension(4, 32767));
        filler41 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        issnField = new javax.swing.JTextField();
        addExistingEditorPanel = new javax.swing.JPanel();
        addExistingEditorLabelPanel = new javax.swing.JPanel();
        editorEmailLabel = new javax.swing.JLabel();
        filler38 = new javax.swing.Box.Filler(new java.awt.Dimension(4, 0), new java.awt.Dimension(4, 0), new java.awt.Dimension(4, 32767));
        filler39 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        editorEmailField = new javax.swing.JTextField();
        confirmPanel = new javax.swing.JPanel();
        filler52 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        confirmButton = new javax.swing.JButton();
        addNewEditorButton = new javax.swing.JButton();
        filler59 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 40), new java.awt.Dimension(0, 40), new java.awt.Dimension(32767, 40));
        filler60 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler61 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler62 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        jSeparator1 = new javax.swing.JSeparator();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));

        setLayout(new java.awt.BorderLayout());

        mainPanel.setLayout(new java.awt.BorderLayout());

        innerPanel.setLayout(new javax.swing.BoxLayout(innerPanel, javax.swing.BoxLayout.PAGE_AXIS));

        addEditorPanel.setLayout(new java.awt.BorderLayout());

        addEditorLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        addEditorLabel.setText("Add Editors");
        addEditorPanel.add(addEditorLabel, java.awt.BorderLayout.NORTH);

        addEditorMainPanel.setLayout(new java.awt.BorderLayout());

        addEditorInnerPanel.setLayout(new javax.swing.BoxLayout(addEditorInnerPanel, javax.swing.BoxLayout.PAGE_AXIS));

        issnPanel.setMaximumSize(new java.awt.Dimension(2147483647, 31));
        issnPanel.setMinimumSize(new java.awt.Dimension(0, 31));
        issnPanel.setPreferredSize(new java.awt.Dimension(120, 31));
        issnPanel.setLayout(new java.awt.BorderLayout());

        issnLabelPanel.setMinimumSize(new java.awt.Dimension(110, 16));
        issnLabelPanel.setPreferredSize(getTextfieldLabelSize());
        issnLabelPanel.setLayout(new java.awt.BorderLayout());

        issnLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        issnLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        issnLabel.setText("Journal ISSN:");
        issnLabelPanel.add(issnLabel, java.awt.BorderLayout.CENTER);
        issnLabelPanel.add(filler40, java.awt.BorderLayout.EAST);

        issnPanel.add(issnLabelPanel, java.awt.BorderLayout.WEST);
        issnPanel.add(filler41, java.awt.BorderLayout.SOUTH);
        issnPanel.add(issnField, java.awt.BorderLayout.CENTER);

        addEditorInnerPanel.add(issnPanel);

        addExistingEditorPanel.setMaximumSize(new java.awt.Dimension(2147483647, 31));
        addExistingEditorPanel.setMinimumSize(new java.awt.Dimension(0, 31));
        addExistingEditorPanel.setPreferredSize(new java.awt.Dimension(120, 31));
        addExistingEditorPanel.setLayout(new java.awt.BorderLayout());

        addExistingEditorLabelPanel.setMinimumSize(new java.awt.Dimension(110, 16));
        addExistingEditorLabelPanel.setPreferredSize(getTextfieldLabelSize());
        addExistingEditorLabelPanel.setLayout(new java.awt.BorderLayout());

        editorEmailLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        editorEmailLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        editorEmailLabel.setText("Existing Editor Email: ");
        addExistingEditorLabelPanel.add(editorEmailLabel, java.awt.BorderLayout.CENTER);
        addExistingEditorLabelPanel.add(filler38, java.awt.BorderLayout.EAST);

        addExistingEditorPanel.add(addExistingEditorLabelPanel, java.awt.BorderLayout.WEST);
        addExistingEditorPanel.add(filler39, java.awt.BorderLayout.SOUTH);
        addExistingEditorPanel.add(editorEmailField, java.awt.BorderLayout.CENTER);

        addEditorInnerPanel.add(addExistingEditorPanel);

        confirmPanel.setMaximumSize(new java.awt.Dimension(2147483647, 31));
        confirmPanel.setMinimumSize(new java.awt.Dimension(0, 31));
        confirmPanel.setPreferredSize(new java.awt.Dimension(120, 31));
        confirmPanel.setLayout(new java.awt.BorderLayout());
        confirmPanel.add(filler52, java.awt.BorderLayout.SOUTH);

        confirmButton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });
        confirmPanel.add(confirmButton, java.awt.BorderLayout.PAGE_START);

        addEditorInnerPanel.add(confirmPanel);

        addNewEditorButton.setText("Add New Editor (fill in journal ISSN only)");
        addNewEditorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewEditorButtonActionPerformed(evt);
            }
        });
        addEditorInnerPanel.add(addNewEditorButton);

        addEditorMainPanel.add(addEditorInnerPanel, java.awt.BorderLayout.CENTER);
        addEditorMainPanel.add(filler59, java.awt.BorderLayout.NORTH);
        addEditorMainPanel.add(filler60, java.awt.BorderLayout.SOUTH);
        addEditorMainPanel.add(filler61, java.awt.BorderLayout.WEST);
        addEditorMainPanel.add(filler62, java.awt.BorderLayout.EAST);

        addEditorPanel.add(addEditorMainPanel, java.awt.BorderLayout.CENTER);

        innerPanel.add(addEditorPanel);
        innerPanel.add(filler1);
        innerPanel.add(jSeparator1);

        mainPanel.add(innerPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(filler3, java.awt.BorderLayout.WEST);
        mainPanel.add(filler4, java.awt.BorderLayout.EAST);
        mainPanel.add(filler5, java.awt.BorderLayout.NORTH);
        mainPanel.add(filler6, java.awt.BorderLayout.SOUTH);

        mainScrollPane.setViewportView(mainPanel);

        add(mainScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewEditorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewEditorButtonActionPerformed
        String issn = issnField.getText();
        RegisterDialog dialog = new RegisterDialog(null, true, "");
        if (verify(issn, "a")){
            int journalCount = -1;
            try {
                journalCount = Database.read("Journal", new Journal(issn,null,email)).size();
            } catch (SQLException ex) {
                Logger.getLogger(AddEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(journalCount > 0){
                dialog.setVisible(true);
                if (dialog.isRegistered()){
                    Account account = dialog.getAccount();
                    String hashedPassword = dialog.getHashedPassword();
                    try {
                        Database.write("Account", account);
                        Database.write("Editor", new Editor(account.getEmail(),hashedPassword));
                        Database.write("EditorOnBoard", new EditorOnBoard(issn,account.getEmail()));
                    } catch (SQLException ex) {
                        Logger.getLogger(AddEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "You are not the chief editor of this journal", "Add Editor", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_addNewEditorButtonActionPerformed

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String issn = issnField.getText();
        String newEmail = editorEmailField.getText();
        
        if(verify(issn,newEmail)){
            try {
                int journalCount = Database.read("Journal", new Journal(issn,null,email)).size();
                if(journalCount > 0){
                    int editorCount = Database.read("Editor", new Editor(newEmail,null)).size();
                    if(editorCount > 0){
                        int editorOnBoardCount = Database.read("EditorOnBoard", new EditorOnBoard(issn,newEmail)).size();
                        if (editorOnBoardCount < 1){
                            Database.write("EditorOnBoard", new EditorOnBoard(issn,newEmail));
                            JOptionPane.showMessageDialog(null, "Success", "Add Editor", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Editor is already on board", "Add Editor", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Editor doesn't exist", "Add Editor", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "You are not the chief editor of this journal", "Add Editor", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddEditorPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }                                             

    // <editor-fold defaultstate="collapsed" desc="Element size">
    private Dimension textfieldLabelSize = new Dimension(120, 100);
    private Dimension getTextfieldLabelSize(){
        return textfieldLabelSize;
    }
    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addEditorInnerPanel;
    private javax.swing.JLabel addEditorLabel;
    private javax.swing.JPanel addEditorMainPanel;
    private javax.swing.JPanel addEditorPanel;
    private javax.swing.JPanel addExistingEditorLabelPanel;
    private javax.swing.JPanel addExistingEditorPanel;
    private javax.swing.JButton addNewEditorButton;
    private javax.swing.JButton confirmButton;
    private javax.swing.JPanel confirmPanel;
    private javax.swing.JTextField editorEmailField;
    private javax.swing.JLabel editorEmailLabel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler38;
    private javax.swing.Box.Filler filler39;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler40;
    private javax.swing.Box.Filler filler41;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler52;
    private javax.swing.Box.Filler filler59;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler60;
    private javax.swing.Box.Filler filler61;
    private javax.swing.Box.Filler filler62;
    private javax.swing.JPanel innerPanel;
    private javax.swing.JTextField issnField;
    private javax.swing.JLabel issnLabel;
    private javax.swing.JPanel issnLabelPanel;
    private javax.swing.JPanel issnPanel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainScrollPane;
    // End of variables declaration//GEN-END:variables
}