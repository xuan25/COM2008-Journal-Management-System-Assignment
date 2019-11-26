/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.DefaultListModel;

import com.com2008.journalmanagementsystem.model.Account;
import com.com2008.journalmanagementsystem.model.Review;
import com.com2008.journalmanagementsystem.model.Submission;
import com.com2008.journalmanagementsystem.model.SubmissionAuthor;
import com.com2008.journalmanagementsystem.model.Submission.Status;
import com.com2008.journalmanagementsystem.util.database.Database;

/**
 *
 * @author harix
 */
public class ReviewerPanel extends javax.swing.JPanel {

    /**
     * Creates new form ReviewerPanel
     */
    private String email;
    // DefaultListModel selectListModel = new DefaultListModel<Submission>();
    public ReviewerPanel(String email) {
        initComponents();

        this.email = email;

        refreshList();
    }

    private void refreshList(){
        try {
            DefaultListModel selectListModel = new DefaultListModel<Submission>();
            List<Submission> sub = Database.read("Submission",new Submission(null, null, null, null, null, null, null, null, Status.SUBMITTED));
            for (Submission submission : sub) {
                
                List<SubmissionAuthor> submissionAuthors = Database.read("SubmissionAuthor", new SubmissionAuthor(submission.getIssn(), submission.getSubmissionID(), null));

                List<Account> accounts = new ArrayList<Account>();

                for (SubmissionAuthor submissionAuthor : submissionAuthors)
                    accounts.add(Database.read("Account", new Account(submissionAuthor.getEmail(), null, null, null, null)).get(0));

                HashSet<String> universitySet = new HashSet<String>();
                for (Account account : accounts)
                    universitySet.add(account.getUniversity());

                String reviewerUniversity = Database.read("Account", new Account(email, null, null, null, null)).get(0).getUniversity();

                if(!universitySet.contains(reviewerUniversity)){
                    if(Database.read("Review", new Review(email, submission.getIssn(), submission.getSubmissionID(), null, null, null)).size() == 0)
                        selectListModel.addElement(submission);
                }

            }
            selectList.setModel(selectListModel);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        list = new javax.swing.JPanel();
        selectPanel = new javax.swing.JPanel();
        selectLable = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        selectList = new javax.swing.JList<>();
        refreshButton = new javax.swing.JButton();
        reviewPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);

        list.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        list.setLayout(new java.awt.BorderLayout());

        selectPanel.setLayout(new java.awt.BorderLayout());

        selectLable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectLable.setText("Select here:");
        selectLable.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        selectPanel.add(selectLable, java.awt.BorderLayout.PAGE_START);

        selectList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                selectListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(selectList);

        selectPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        refreshButton.setText("Refresh list");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        selectPanel.add(refreshButton, java.awt.BorderLayout.PAGE_END);

        list.add(selectPanel, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(list);

        reviewPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(reviewPanel);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void selectListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_selectListValueChanged
        selectList.setEnabled(false);
        Submission submission = selectList.getSelectedValue();
        if(submission == null){
            return;
        }
        ArticlePanel articlePanel = new ArticlePanel(submission, UserRole.REVIEWER, email);
        reviewPanel.removeAll();
        reviewPanel.add(articlePanel);
        reviewPanel.revalidate();
    }//GEN-LAST:event_selectListValueChanged

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
        // Submission submissionNow = selectList.getSelectedValue();
        // selectListModel.removeElement(submissionNow);
        reviewPanel.removeAll();
        reviewPanel.repaint();
        reviewPanel.revalidate();
        refreshList();
        selectList.setEnabled(true);



    }//GEN-LAST:event_refreshButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel list;
    private javax.swing.JButton refreshButton;
    private javax.swing.JPanel reviewPanel;
    private javax.swing.JLabel selectLable;
    private javax.swing.JList<Submission> selectList;
    private javax.swing.JPanel selectPanel;
    // End of variables declaration//GEN-END:variables
}
