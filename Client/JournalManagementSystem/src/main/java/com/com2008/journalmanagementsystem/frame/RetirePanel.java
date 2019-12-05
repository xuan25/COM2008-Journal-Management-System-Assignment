/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import com.com2008.journalmanagementsystem.model.Account;
import com.com2008.journalmanagementsystem.model.Editor;
import com.com2008.journalmanagementsystem.model.EditorOnBoard;
import com.com2008.journalmanagementsystem.model.Journal;
import com.com2008.journalmanagementsystem.util.database.Database;

import static javax.swing.BoxLayout.Y_AXIS;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jorda
 * 
 * Creates a Panel from which Editors can retire
 */

public class RetirePanel extends javax.swing.JPanel {
	
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form RetirePanel
     */
    public RetirePanel() {
        initComponents();
    }
    
    public RetirePanel(String email) {
        initComponents();
        //find the journals which this editor is working on
        //and which other editors are in those journals
        //store these in lists 
        List<Journal> journals = new ArrayList<Journal>();
        List<List<EditorOnBoard>> editorsOnBoardOf = new ArrayList<List<EditorOnBoard>>();
        try {
            List<EditorOnBoard> editorsOnBoard = Database.read("EditorOnBoard", new EditorOnBoard(null,email));
            for (EditorOnBoard editorOnBoard:editorsOnBoard){
                Journal currentJournal = Database.read("Journal", new Journal(editorOnBoard.getIssn(),null,null)).get(0);
                journals.add(currentJournal);
                List<EditorOnBoard> currentEditorsOnBoard = Database.read("EditorOnBoard", new EditorOnBoard(editorOnBoard.getIssn(),null));
                editorsOnBoardOf.add(currentEditorsOnBoard);
            }
            
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        //turn editorPAnel into a new grid layout
        String editorString = "";
        editorPanel.setLayout(new BoxLayout(editorPanel,Y_AXIS));
        
        //for each journal in journals
        //add a label for: the journal name, the current editors, the chief editor
        //add a button which has a custom action listener
        ArrayList<JButton> buttonList = new ArrayList<>();
        for (int i=0; i<journals.size(); i++){
        	editorString = "";
            for (EditorOnBoard e:editorsOnBoardOf.get(i)) {
            	editorString = editorString.concat(e.getEmail()+" ");
            }
            JLabel label = new JLabel("Journal Name: "+journals.get(i).getJournalName());
            JLabel labelEditor = new JLabel("Editors on Project: "+editorString);
            JLabel labelChief = new JLabel("Chief: "+journals.get(i).getCheifEmail());
            buttonList.add(new JButton("Retire from "+journals.get(i).getJournalName()));
            //add button listener with information from the current journal
            buttonList.get(buttonList.size()-1).addActionListener(new RetireActionListener(editorsOnBoardOf.get(i),email,journals.get(i).getIssn(),journals.get(i).getCheifEmail()));
            editorPanel.add(label);
            editorPanel.add(labelEditor);
            editorPanel.add(labelChief);
            editorPanel.add(buttonList.get(buttonList.size()-1));
            editorPanel.add(Box.createRigidArea(new Dimension(20, 20)));
            
        }
        editorPanel.revalidate();
        editorPanel.repaint();
    }
    
    /*
     * Custom action listener which takes values so that the buttons
     * generated perform actions on the correct journal
     */
    private class RetireActionListener implements ActionListener {
    	
    	List<EditorOnBoard> eList;
    	String email;
    	String issn;
    	String cEmail;
    	
        public RetireActionListener(List<EditorOnBoard> list, String em, String is, String ce) {
			// TODO Auto-generated constructor stub
        	this.eList = list;
        	this.email = em;
        	this.issn = is;
        	this.cEmail = ce;
		}

		public void actionPerformed(ActionEvent e) {
			//if you are the chief editor or the only editor, generate an error
            if (email.toLowerCase().equals(cEmail.toLowerCase())) {
            	JOptionPane.showMessageDialog(null, "You are the chief editor \nPlease pass chief editor before retiring", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
            	 if (eList.size() > 1) {
                 	try {
     					Database.delete("EditorOnBoard", new EditorOnBoard(issn, email));
                 		JOptionPane.showMessageDialog(null, "You are no longer on the board of this journal", 
                 				"Information", JOptionPane.INFORMATION_MESSAGE);
                 		JButton currentButton = (JButton) e.getSource();
                 		currentButton.setEnabled(false);
                     	List<EditorOnBoard > editorsOnBoard = Database.read("EditorOnBoard", new EditorOnBoard(null,email));
                     	//if your are no longer an editor on any board, logout and delete account
                     	if (editorsOnBoard.size() == 0) {
                     		JOptionPane.showMessageDialog(null, "You are no longer the editor of any journal"
                     				+ "\nYou will now be logged out and account removed", "Information", JOptionPane.INFORMATION_MESSAGE);
                            JFrame fr = (JFrame)SwingUtilities.getRoot(editorPanel);
                            fr.dispose();
                            Database.delete("Editor", new Editor(email,null));
                            Database.delete("Account", new Account(email, null, null, null, null));
                            LoginFrame newLogFrame = new LoginFrame();
                            newLogFrame.setVisible(true);
                     	}
     				} catch (SQLException e1) {
     					e1.printStackTrace();
     				}
                 }
                 else {
                 	JOptionPane.showMessageDialog(null, "Cannot remove the only Editor", "Error", JOptionPane.ERROR_MESSAGE);
                 }
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

        editorPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();

        javax.swing.GroupLayout editorPanelLayout = new javax.swing.GroupLayout(editorPanel);
        editorPanel.setLayout(editorPanelLayout);
        editorPanelLayout.setHorizontalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 145, Short.MAX_VALUE)
        );
        editorPanelLayout.setVerticalGroup(
            editorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setText("Retire From Journals");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addContainerGap(249, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titlePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel editorPanel;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
