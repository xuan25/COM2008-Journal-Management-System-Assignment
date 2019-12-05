/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import com.com2008.journalmanagementsystem.model.EditorOnBoard;
import com.com2008.journalmanagementsystem.model.Journal;
import com.com2008.journalmanagementsystem.util.database.Database;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jorda
 */
public class PassChiefPanel extends javax.swing.JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form PassChiefPanel
     * 
     * Panel that allows chief editors to pass the chief editor role
     */
    public PassChiefPanel(String email) {
        initComponents();
        //get journals and the amount of editors on the
        //board of that journal
        List<Journal> journals = null;
        List<List<EditorOnBoard>> editorBoards = new ArrayList<>();
        try {
            journals = Database.read("Journal", new Journal(null,null,email));
            for (Journal journal : journals){
                List<EditorOnBoard> journalEditors = Database.read("EditorOnBoard", new EditorOnBoard(journal.getIssn(),null));
                editorBoards.add(journalEditors);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        //for each editor get the journal info and 
        //create a button for each editor which can become
        //the new chief
        editorListPanel.setLayout(new BoxLayout(editorListPanel,Y_AXIS));
        ArrayList<JButton> buttonList = new ArrayList<>();
        for (int i=0; i<journals.size();i++){
            
            JLabel journalLabel = new JLabel("Journal: "+journals.get(i).getJournalName());
            journalLabel.setFont(new Font("Tahoma",Font.PLAIN,14));
            editorListPanel.add(journalLabel);
            editorListPanel.add(Box.createRigidArea(new Dimension(20, 20)));
            
        	if (editorBoards.get(i).size() > 1) {
                    //if you aren't the only editor
	            for (int j=0; j<editorBoards.get(i).size();j++){
	            	if (!(email.toLowerCase().equals(editorBoards.get(i).get(j).getEmail().toLowerCase()))) {
                            //skip if the current editor on board is user
	                    buttonList.add(new JButton("Change Chief to "+editorBoards.get(i).get(j).getEmail()));
	                    //add button listener with information from the current journal
	                    buttonList.get(buttonList.size()-1).addActionListener(
	                            new PassChiefPanel.ChangeChiefActionListener(
	                                    journals.get(i),email,editorBoards.get(i).get(j).getEmail()));
	                    editorListPanel.add(buttonList.get(buttonList.size()-1));
	                    editorListPanel.add(Box.createRigidArea(new Dimension(10, 10)));
	            	}
	            }
	            editorListPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        	}
        	else {
                    JLabel noEditorsLabel = new JLabel("There are no other editors on this journal");
                    editorListPanel.add(noEditorsLabel);
                    editorListPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        	}
        }
        //refresh the panel
        editorListPanel.revalidate();
        editorListPanel.repaint();
    }
    
    private class ChangeChiefActionListener implements ActionListener {
        
    	String userEmail;
        String newEmail;
        Journal thisJournal;
    	
        public ChangeChiefActionListener(Journal jo, String userEm, String newEm) {
            this.userEmail = userEm;
            this.newEmail = newEm;
            this.thisJournal = jo;
	}

	public void actionPerformed(ActionEvent e) {
            //get the most up to date version of the journal\
            Journal updatedJournal = null;
            try {
                updatedJournal = Database.read("Journal", new Journal(thisJournal.getIssn(),null,null)).get(0);
            } catch (SQLException ex) {
                Logger.getLogger(PassChiefPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            //if you are still the chief of this journal
            //(user may have tranferred chief and tried to press the button again)
            if (updatedJournal.getCheifEmail().toLowerCase().equals(userEmail.toLowerCase())){ 
                try {
                    Database.update("Journal",thisJournal,new Journal(null,null,newEmail));
                    int journalAmount = Database.read("Journal", new Journal(null,null,userEmail)).size();
                    System.out.println(journalAmount);
                    if (journalAmount == 0){
                        //if you are no longer chief of any journal
                        //log out user since you no longer have chief privilidges
                        JOptionPane.showMessageDialog(null, "You are no longer chief "
                                + "editor of any journals\n You will now be logged out", "Logout", JOptionPane.INFORMATION_MESSAGE);
                        JFrame fr = (JFrame)SwingUtilities.getRoot(editorListPanel);
                        fr.dispose();
                        LoginFrame newLogFrame = new LoginFrame();
                        newLogFrame.setVisible(true);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PassChiefPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "You are no longer the chief of "
                    + "this journal", "Error", JOptionPane.ERROR_MESSAGE);
                JButton parentButton = (JButton)e.getSource();
                parentButton.setEnabled(false);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editorListPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();

        javax.swing.GroupLayout editorListPanelLayout = new javax.swing.GroupLayout(editorListPanel);
        editorListPanel.setLayout(editorListPanelLayout);
        editorListPanelLayout.setHorizontalGroup(
            editorListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        editorListPanelLayout.setVerticalGroup(
            editorListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setText("Change Chief Editor");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editorListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editorListPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel editorListPanel;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
