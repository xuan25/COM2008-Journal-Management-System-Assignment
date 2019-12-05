/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import com.com2008.journalmanagementsystem.model.Article;
import com.com2008.journalmanagementsystem.model.Edition;
import com.com2008.journalmanagementsystem.model.EditorOnBoard;
import com.com2008.journalmanagementsystem.model.Journal;
import com.com2008.journalmanagementsystem.model.Submission;
import com.com2008.journalmanagementsystem.model.Submission.Status;
import com.com2008.journalmanagementsystem.util.database.Database;
import java.awt.Dimension;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorda
 */
public class PublishPanel extends javax.swing.JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form PublishPanel
     */
    public PublishPanel() {
        initComponents();
    }
    
    public PublishPanel(String email){
        initComponents();
        List<EditorOnBoard> editorsOnBoard = new ArrayList<>();
        List<Journal> journals = new ArrayList<>();
        List<List<Submission>> submissionList = new ArrayList<>();
        List<Edition> newestEdition = new ArrayList<>();
        List<Submission> submissions = new ArrayList<>();
        List<JButton> buttonList = new ArrayList<>();
        //get every journal the current user is on the board of
        //for each journal find the editions and the newest edition of that journal
        try {
            editorsOnBoard = Database.read("EditorOnBoard", new EditorOnBoard(null,email));
            for (int i=0;i<editorsOnBoard.size();i++){
                Journal currentJournal = Database.read("Journal", new Journal(editorsOnBoard.get(i).getIssn(),null,null)).get(0);
                journals.add(currentJournal);
                List<Edition> currentEditions = Database.read("Edition", new Edition(
                        editorsOnBoard.get(i).getIssn(),null,null));
                newestEdition.add(new Edition(null,0,0));
                for (Edition edition:currentEditions){
                    if (edition.getVolume() > newestEdition.get(i).getVolume()){
                        newestEdition.set(i, edition);
                    }
                    else if (edition.getVolume() == newestEdition.get(i).getVolume()) {
                        if (edition.getEdition() > newestEdition.get(i).getEdition()){
                            newestEdition.set(i, edition);
                        }
                    }
                }
                //from each article in the newest volume and edition in the journal
                //find the connected submission to get the titles
                List<Article> currentArticles = Database.read("Article", new Article(
                        editorsOnBoard.get(i).getIssn(),null,newestEdition.get(i).getVolume(),newestEdition.get(i).getEdition()));
                for (Article article: currentArticles){
                    List<Submission> currentSubmission = Database.read("Submission", new Submission(
                            null,article.getSubmissionID(),null,null,null,null,Status.ACCEPTED));
                    if (currentSubmission.size() > 0) {
                        submissions.add(currentSubmission.get(0));
                    }
                }
                submissionList.add(submissions);
                submissions = new ArrayList<>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublishPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //add labels for the journal and the current article titles in that journal
        //add buttons for publishing as a new volume or a new edition
        journalPanel.setLayout(new BoxLayout(journalPanel,Y_AXIS));
        for (int i=0;i<journals.size();i++){
            JLabel journalLabel = new JLabel("Journal: "+journals.get(i).getJournalName());
            JLabel volumeInfoLabel = new JLabel("The articles are currrently part of Volume "
                    +newestEdition.get(i).getVolume()+" Edition "+newestEdition.get(i).getEdition());
            JLabel articleInfoLabel = new JLabel("The current articles in this edition are: ");
            journalPanel.add(journalLabel);
            journalPanel.add(Box.createRigidArea(new Dimension(20, 20)));
            journalPanel.add(volumeInfoLabel);
            journalPanel.add(Box.createRigidArea(new Dimension(20, 20)));
            journalPanel.add(articleInfoLabel);
            journalPanel.add(Box.createRigidArea(new Dimension(20, 20)));
            for (Submission submission:submissionList.get(i)){
                JLabel articleLabel = new JLabel(submission.getTitle());
                journalPanel.add(articleLabel);
            }
            buttonList.add(new JButton("Publish as Volume"));
            buttonList.get(buttonList.size()-1).addActionListener(new PublishActionListener(
                    journals.get(i),submissionList.get(i),newestEdition.get(i).getVolume(),newestEdition.get(i).getEdition(),true));
            buttonList.add(new JButton("Publish as Edition"));
            buttonList.get(buttonList.size()-1).addActionListener(new PublishActionListener(
                    journals.get(i),submissionList.get(i),newestEdition.get(i).getVolume(),newestEdition.get(i).getEdition(),false));
            journalPanel.add(Box.createRigidArea(new Dimension(20, 20)));
            journalPanel.add(buttonList.get(buttonList.size()-2));
            journalPanel.add(buttonList.get(buttonList.size()-1));
            journalPanel.add(Box.createRigidArea(new Dimension(30, 30)));
        }
        
        journalPanel.revalidate();
        journalPanel.repaint();
        
    }
    
    private class PublishActionListener implements ActionListener {
    	//custom action listener to publish articles
    	
    	Boolean publishType;
        int volNumber;
        int edNumber;
        Journal journal;
        List<Submission> subList;
    	
        public PublishActionListener(Journal journal, List<Submission> subList, int volNumber, int edNumber, Boolean publishType) {
            //publishType true -> publish volume
            //publishType false -> publish edition
            this.journal = journal;
            this.subList = subList;
            this.volNumber = volNumber;
            this.edNumber = edNumber;
            this.publishType = publishType;
        }	

        public void actionPerformed(ActionEvent e) {
        	List<Edition> updatedEditionList = null;
        	boolean hasPressed = false;
			try {
				updatedEditionList = Database.read("Edition", new Edition(journal.getIssn(),null,null));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			for (Edition edition:updatedEditionList) {
				if(edition.getVolume() == volNumber+1 || (edition.getVolume() == volNumber && edition.getEdition() == edNumber+1)) {
					hasPressed = true;
				}
			}
        	if (hasPressed) {
        		JOptionPane.showMessageDialog(null, "Option Already Picked", "Error", JOptionPane.ERROR_MESSAGE);
                JButton parentButton = (JButton)e.getSource();
                parentButton.setEnabled(false);
        	}
        	else {
	        	//if publishing as a volume
	            if (publishType){
	            	//check that there at least 4 editions already
	                if(edNumber <= 3){
	                    JOptionPane.showMessageDialog(null, "There must be at least 4 editions in a volume", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	                //check there are at least 3 items in an edition
	                else if (subList.size() <= 2) {
	                	JOptionPane.showMessageDialog(null, "There must be at least 3 articles in an edition", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	                else {
	                    try {
	                    	//otherwise write to the database
	                        Database.write("Edition", new Edition(journal.getIssn(),volNumber+1,1));
	                    } catch (SQLException ex) {
	                        Logger.getLogger(PublishPanel.class.getName()).log(Level.SEVERE, null, ex);
	                    }
	                }
	            }
	            else {
	            	//if publishing as an edition
	            	//we just need to check there are at least 3 articles
	                if (subList.size() >= 3){
	                    try {
	                        Database.write("Edition", new Edition(journal.getIssn(),volNumber,edNumber+1));
	                    } catch (SQLException ex) {
	                        Logger.getLogger(PublishPanel.class.getName()).log(Level.SEVERE, null, ex);
	                    }
	                }
	                else {
	                    JOptionPane.showMessageDialog(null, "There must be at least 3 articles in an edition", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
        	}
            
            journalPanel.revalidate();
            journalPanel.repaint();
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

        titlePanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        journalPanel = new javax.swing.JPanel();

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setText("Publish Journals");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addContainerGap(285, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout journalPanelLayout = new javax.swing.GroupLayout(journalPanel);
        journalPanel.setLayout(journalPanelLayout);
        journalPanelLayout.setHorizontalGroup(
            journalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );
        journalPanelLayout.setVerticalGroup(
            journalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(journalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(journalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(234, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel journalPanel;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
