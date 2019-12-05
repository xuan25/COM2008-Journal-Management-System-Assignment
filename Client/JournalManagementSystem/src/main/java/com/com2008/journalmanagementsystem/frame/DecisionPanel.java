/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import com.com2008.journalmanagementsystem.model.Account;
import com.com2008.journalmanagementsystem.model.Article;
import com.com2008.journalmanagementsystem.model.Edition;
import com.com2008.journalmanagementsystem.model.EditorOnBoard;
import com.com2008.journalmanagementsystem.model.Journal;
import com.com2008.journalmanagementsystem.model.Review;
import com.com2008.journalmanagementsystem.model.Submission;
import com.com2008.journalmanagementsystem.model.Submission.Status;
import com.com2008.journalmanagementsystem.model.SubmissionAuthor;
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

/**
 *
 * @author Jorda
 */
public class DecisionPanel extends javax.swing.JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form DecisionPanel
     */
    public DecisionPanel() {
        initComponents();
    }
    
    public DecisionPanel(String email) {
        initComponents();
        //get a list of journals and submissions for each journal
        List<Journal> journals = new ArrayList<>();
        List<List<Submission>> submissions = new ArrayList<>();
        try {
            List<EditorOnBoard> editorsOnBoard = Database.read(
                    "EditorOnBoard", new EditorOnBoard(null,email));
            for (EditorOnBoard editorOnBoard:editorsOnBoard){
                Journal currentJournal = Database.read(
                        "Journal", new Journal(editorOnBoard.getIssn(),null,null)).get(0);
                journals.add(currentJournal);
                List<Submission> currentSubmissions = Database.read(
                        "Submission", new Submission(
                                editorOnBoard.getIssn(),null,null,null,null,null,Status.VERDICTED));
                submissions.add(currentSubmissions);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        //automatically accept or reject submissions based on reviews
        List<Review> reviews = null;
        List<Submission> deleteSubs = new ArrayList<>();
        for (int i=0;i<journals.size(); i++) {
        	for (Submission sub:submissions.get(i)) {
        		try {
					reviews = Database.read("Review", new Review(null,null,sub.getSubmissionID(),null,null,null,null));
				} catch (SQLException e) {
					e.printStackTrace();
				}
        		//automatic = 0 -> manually accept/reject
        		//automatic = -3 -> automatically reject
        		//automatic = 3 -> automatically accept
        		int automatic = 0;
        		for (Review review: reviews) {
        			switch (review.getFinalVerdict()) {
        			case STRONG_ACCEPT:
        				automatic += 1;
        				break;
        			case STRONG_REJECT:
        				automatic -= 1;
        				break;
					default:
						break;
        			}
        		}
        		if (automatic == 3) {
        			try {
        			 Database.update("Submission",sub,new Submission(null,null,null,null,null,null,Status.ACCEPTED));
                     List<Edition> editions = Database.read("Edition", new Edition(null,null,null));
                     int newestVolume = 0;
                     int newestEdition = 0;
                     for (Edition edition:editions){
                         if (edition.getVolume() > newestVolume){
                             newestVolume = edition.getVolume();
                             newestEdition = edition.getEdition();
                         }
                         else if (edition.getVolume() == newestVolume) {
                             if (edition.getEdition() > newestEdition){
                                 newestEdition = edition.getEdition();
                             }
                         }
                     }
                     //if the current edition and volume are full, create new ones
                     int amountArticles = Database.read("Article", new Article(null,null,newestVolume,newestEdition)).size();
                     if(amountArticles >= 8) {
                     	if (newestEdition == 6) {
                     		Database.write("Edition",new Edition(sub.getIssn(),newestVolume+1,1));
                             Database.write("Article",new Article(sub.getIssn(),sub.getSubmissionID(),newestVolume+1,1));
                     	}
                     	else {
                     		Database.write("Edition",new Edition(sub.getIssn(),newestVolume,newestEdition+1));
                             Database.write("Article",new Article(sub.getIssn(),sub.getSubmissionID(),newestVolume,newestEdition+1));
                     	}
                     }
                     else {
                         Database.write("Article",new Article(sub.getIssn(),sub.getSubmissionID(),newestVolume,newestEdition));
                     }
        				
        			}
        			catch (SQLException ex) {
        				ex.printStackTrace();
        			}
            		deleteSubs.add(sub);
        		}
        		else if (automatic == -3) {
        			try {
						Database.update("Submission",sub,new Submission(null,null,null,null,null,null,Status.REJECTED));
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
            		deleteSubs.add(sub);
        		}
        	}
        	for (Submission s:deleteSubs) {
        		submissions.get(i).remove(s);
        	}
        }
    
        //for each submission in a journal add a button and labels
        //to view that submission
        journalPanel.setLayout(new BoxLayout(journalPanel,Y_AXIS));
        ArrayList<JButton> buttonList = new ArrayList<>();
        String editorAffiliation = null;
        //Get the account user's affiliation
        try {
            editorAffiliation = Database.read(
                    "Account", new Account(email,null,null,null,null))
                    .get(0).getUniversity();
        } catch (SQLException ex) {
            Logger.getLogger(DecisionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //contiually add accounts and check affiliations
        for (int i=0; i<journals.size(); i++){
            for (Submission sub: submissions.get(i)){
                //get author universities for conflict of interest
                ArrayList<String> authorAffiliations = new ArrayList<>();
                try {
                    List<SubmissionAuthor> subAuthors = Database.read(
                            "SubmissionAuthor", new SubmissionAuthor(null,sub.getSubmissionID(),null));
                    for (SubmissionAuthor author:subAuthors){
                        Account currentAccount = Database.read(
                                "Account", new Account(author.getEmail(),null,null,null,null)).get(0);
                        authorAffiliations.add(currentAccount.getUniversity().toLowerCase());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DecisionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!(authorAffiliations.contains(editorAffiliation.toLowerCase()))){
                    if (sub.getStatus() == Status.VERDICTED) {
                        JLabel label = new JLabel("Journal: "+journals.get(i).getJournalName()+" "
                        + "Title: "+sub.getTitle());
                        label.setFont(new Font("Tahoma",Font.PLAIN,14));
                        buttonList.add(new JButton("See this submission"));
                        //add button listener with information from the current journal
                        buttonList.get(buttonList.size()-1).addActionListener(
                                new LinkSubmissionActionListener(sub,email));
                        journalPanel.add(label);
                        journalPanel.add(buttonList.get(buttonList.size()-1));
                        journalPanel.add(Box.createRigidArea(new Dimension(20, 20)));
                    } 
                }
            }
        }
        //refresh the panel
        journalPanel.revalidate();
        journalPanel.repaint();
    }
    
    private class LinkSubmissionActionListener implements ActionListener {
    	//custom action listener so that the correct submission is used
    	Submission su;
    	String em;
    	
        public LinkSubmissionActionListener(Submission sub, String email) {
        	this.su = sub;
        	this.em = email;
        	
	}

        public void actionPerformed(ActionEvent e) {
            try {
                //must get the newest updated version of the submission
                //to test whether the submission was just reviewed
                Submission updatedSu = Database.read("Submission", new Submission(su.getIssn(),su.getSubmissionID(),null,null,null,null,null)).get(0);
                if (updatedSu.getStatus() == Status.VERDICTED){
                    JFrame submissionFrame = new JFrame();
                    submissionFrame.add(new ArticlePanel(su,UserRole.EDITOR,em));
                    submissionFrame.setBounds(0, 0, 1000, 500);
                    submissionFrame.setVisible(true);
                }
                else{
                    //if submission is already reviewed, disable button
                    JOptionPane.showMessageDialog(null, "This submission has "
                            + "already been reviewed", "Review Error", JOptionPane.ERROR_MESSAGE);
                    JButton parentButton = (JButton)e.getSource();
                    parentButton.setEnabled(false);
                }
            }
            catch (SQLException r){
                r.printStackTrace();
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

        lblTitle = new javax.swing.JLabel();
        journalPanel = new javax.swing.JPanel();

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setText("Journal List");

        javax.swing.GroupLayout journalPanelLayout = new javax.swing.GroupLayout(journalPanel);
        journalPanel.setLayout(journalPanelLayout);
        journalPanelLayout.setHorizontalGroup(
            journalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
        );
        journalPanelLayout.setVerticalGroup(
            journalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(journalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle))
                .addContainerGap(301, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(journalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel journalPanel;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
