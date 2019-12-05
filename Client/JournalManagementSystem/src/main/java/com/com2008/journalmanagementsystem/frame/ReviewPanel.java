/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.com2008.journalmanagementsystem.model.*;
import com.com2008.journalmanagementsystem.model.Review.Verdict;
import com.com2008.journalmanagementsystem.util.database.Database;

/**
 *
 * @author Xuan, harix
 */
public class ReviewPanel extends javax.swing.JPanel {

    /**
     * Creates new form ReviewPanel
     */
    private Review review;
    DefaultListModel<TypoError> typoErrorListModel = new DefaultListModel<TypoError>();
    DefaultListModel<Criticism> criticismsListModel = new DefaultListModel<Criticism>();
    public ReviewPanel() {
        initComponents();
    }

    // public ReviewPanel(String name, Review review) {
    //     initComponents();

    //     reviewerLabel.setText(name);
    //     acceptableLabel.setText(review.getVerdict().toString());
    //     summaryTextArea.setText(review.getSummary());

    //     try {
    //         typoErrorsList.removeAll();
    //         criticismsList.removeAll();

    //         List<TypoError> typoErrors = Database.read("TypoError", new TypoError(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null));
    //         for(TypoError typoError : typoErrors){
    //             typoErrorListModel.addElement(typoError);
    //         }
    //         typoErrorsList.setModel(typoErrorListModel);

    //         List<Criticism> criticisms = Database.read("Criticism", new Criticism(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null));
    //         for(Criticism criticism : criticisms){
    //             criticismsListModel.addElement(criticism);
    //         }
    //         criticismsList.setModel(criticismsListModel);
            
    //         submitPannel.setVisible(false);
            
    //     } catch (SQLException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
        

    // }

    public ReviewPanel(String name, Review review, UserRole userRole) {
        initComponents();

        switch(userRole){
            case AUTHOR:
                submitPannel.setVisible(false);

                reviewerLabel.setText(name);
                acceptableLabel.setText(review.getVerdict().toString());
                summaryTextArea.setText(review.getSummary());
        
                try {
                    typoErrorsList.removeAll();
                    criticismsList.removeAll();
        
                    List<TypoError> typoErrors = Database.read("TypoError", new TypoError(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null));
                    for(TypoError typoError : typoErrors){
                        typoErrorListModel.addElement(typoError);
                    }
                    typoErrorsList.setModel(typoErrorListModel);
        
                    List<Criticism> criticisms = Database.read("Criticism", new Criticism(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null));
                    for(Criticism criticism : criticisms){
                        criticismsListModel.addElement(criticism);
                    }
                    criticismsList.setModel(criticismsListModel);
                    
                }
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case REVIEWER:
                reviewerLabel.setVisible(false);
                try {
                    List<Review> reviews = Database.read("Review",new Review(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null, null, null));
                    if (reviews.size() > 0){
                        this.review = reviews.get(0);
                        acceptableLabel.setVisible(true);
                        authorResponcePanel.setVisible(true);
                        typoErrorsAddPanel.setVisible(false);
                        criticismsAddPanel.setVisible(false);
                        
                        summaryTextArea.setEditable(false);
                        summaryTextArea.setText(this.review.getSummary());

                        acceptableLabel.setText(this.review.getVerdict().toString());

                        List<TypoError> typoErrors = Database.read("TypoError", new TypoError(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null));
                        for(TypoError typoError : typoErrors){
                            typoErrorListModel.addElement(typoError);
                        }

                        DefaultListModel<Response> responsesListModel = new DefaultListModel<Response>();
                        List<Response> responses = Database.read("Response", new Response(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null));

                        HashMap<Integer, Response> responseMap = new HashMap<Integer, Response>();
                        for (Response response : responses) {
                            responseMap.put(response.getNum(), response);
                        }

                        List<Criticism> criticisms = Database.read("Criticism", new Criticism(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null));
                        for(Criticism criticism : criticisms){
                            criticismsListModel.addElement(criticism);
                            if(responseMap.containsKey(criticism.getNum())){
                                responsesListModel.addElement(responseMap.get(criticism.getNum()));
                            }
                            else{
                                responsesListModel.addElement(new Response(null, null, null, null, "<No RESPONSE!>"));
                            }
                        }
                        authorResponceList.setModel(responsesListModel);
                        Submission submission = Database.read("Submission", new Submission(review.getIssn(), review.getSubmissionID(), null, null, null, null, null)).get(0);
                        if (submission.getStatus() == Submission.Status.REVIEWED) {
                            submitPannel.setVisible(false);
                        }
                    }
                    else{
                        this.review = review;
                        acceptableLabel.setVisible(false);
                        authorResponcePanel.setVisible(false);
                        summaryTextArea.setText("");
                
                        typoErrorsList.removeAll();
                        criticismsList.removeAll(); 
                    } 
                    typoErrorsList.setModel(typoErrorListModel);
                    criticismsList.setModel(criticismsListModel);

                
                } 
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
    }

    public JList<Criticism> getCriticismList(){
        return criticismsList;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        mainPanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        infoPanel = new javax.swing.JPanel();
        reviewerLabel = new javax.swing.JLabel();
        acceptableLabel = new javax.swing.JLabel();
        summaryLabel = new javax.swing.JLabel();
        summaryScrollPane = new javax.swing.JScrollPane();
        summaryTextArea = new javax.swing.JTextArea();
        typoErrorsLabel = new javax.swing.JLabel();
        typoErrorsScrollPane = new javax.swing.JScrollPane();
        typoErrorsList = new javax.swing.JList<>();
        criticismsLabel = new javax.swing.JLabel();
        criticismsScrollPane = new javax.swing.JScrollPane();
        criticismsList = new javax.swing.JList<>();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jSeparator1 = new javax.swing.JSeparator();
        submitPannel = new javax.swing.JPanel();
        SubmissionLabel = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        typoCritismsAddPanel = new javax.swing.JPanel();
        typoErrorsAddPanel = new javax.swing.JPanel();
        addTypoText = new javax.swing.JLabel();
        typoErrorAddScrolpanel = new javax.swing.JScrollPane();
        typoErrorsTextArea = new javax.swing.JTextArea();
        typoErrorAddButton = new javax.swing.JButton();
        criticismsAddPanel = new javax.swing.JPanel();
        criticismsAddText = new javax.swing.JLabel();
        criticismsAddScrolPanel = new javax.swing.JScrollPane();
        criticsmsTextArea = new javax.swing.JTextArea();
        criticismsAddButton = new javax.swing.JButton();
        authorResponcePanel = new javax.swing.JPanel();
        authorResponceLable = new javax.swing.JLabel();
        authorResponceTextPanel = new javax.swing.JScrollPane();
        authorResponceList = new javax.swing.JList<>();
        verdictPanel = new javax.swing.JPanel();
        verdictText = new javax.swing.JLabel();
        verdictList = new javax.swing.JScrollPane();
        verdictSelectList = new javax.swing.JList<>();
        submitButton = new javax.swing.JButton();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        setLayout(new java.awt.BorderLayout());
        add(filler3, java.awt.BorderLayout.WEST);
        add(filler4, java.awt.BorderLayout.EAST);

        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.PAGE_AXIS));
        mainPanel.add(filler1);

        infoPanel.setLayout(new java.awt.BorderLayout());

        reviewerLabel.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        reviewerLabel.setText("Reviewer ID");
        infoPanel.add(reviewerLabel, java.awt.BorderLayout.CENTER);

        acceptableLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        acceptableLabel.setForeground(new java.awt.Color(0, 153, 0));
        acceptableLabel.setText("Acceptable");
        infoPanel.add(acceptableLabel, java.awt.BorderLayout.EAST);

        mainPanel.add(infoPanel);

        summaryLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        summaryLabel.setText("Summary");
        mainPanel.add(summaryLabel);

        summaryTextArea.setColumns(20);
        summaryTextArea.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setRows(5);
        summaryTextArea.setText("Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  ");
        summaryTextArea.setWrapStyleWord(true);
        summaryScrollPane.setViewportView(summaryTextArea);

        mainPanel.add(summaryScrollPane);

        typoErrorsLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        typoErrorsLabel.setText("Typo errors");
        mainPanel.add(typoErrorsLabel);

        typoErrorsList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        typoErrorsScrollPane.setViewportView(typoErrorsList);

        mainPanel.add(typoErrorsScrollPane);

        criticismsLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        criticismsLabel.setText("Criticisms");
        mainPanel.add(criticismsLabel);

        criticismsList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        criticismsScrollPane.setViewportView(criticismsList);

        mainPanel.add(criticismsScrollPane);
        mainPanel.add(filler2);
        mainPanel.add(jSeparator1);

        submitPannel.setLayout(new java.awt.BorderLayout());

        SubmissionLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SubmissionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SubmissionLabel.setText("Submit");
        submitPannel.add(SubmissionLabel, java.awt.BorderLayout.PAGE_START);

        jSplitPane2.setDividerLocation(500);

        typoCritismsAddPanel.setLayout(new java.awt.BorderLayout());

        typoErrorsAddPanel.setLayout(new java.awt.BorderLayout());

        addTypoText.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        addTypoText.setText("Add Typo Error");
        typoErrorsAddPanel.add(addTypoText, java.awt.BorderLayout.NORTH);

        typoErrorsTextArea.setColumns(20);
        typoErrorsTextArea.setRows(5);
        typoErrorAddScrolpanel.setViewportView(typoErrorsTextArea);

        typoErrorsAddPanel.add(typoErrorAddScrolpanel, java.awt.BorderLayout.CENTER);

        typoErrorAddButton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        typoErrorAddButton.setText("Add");
        typoErrorAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typoErrorAddButtonActionPerformed(evt);
            }
        });
        typoErrorsAddPanel.add(typoErrorAddButton, java.awt.BorderLayout.LINE_END);

        typoCritismsAddPanel.add(typoErrorsAddPanel, java.awt.BorderLayout.PAGE_START);

        criticismsAddPanel.setMinimumSize(new java.awt.Dimension(120, 45));
        criticismsAddPanel.setLayout(new java.awt.BorderLayout());

        criticismsAddText.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        criticismsAddText.setText("Add Criticisms");
        criticismsAddPanel.add(criticismsAddText, java.awt.BorderLayout.PAGE_START);

        criticsmsTextArea.setColumns(20);
        criticsmsTextArea.setRows(5);
        criticismsAddScrolPanel.setViewportView(criticsmsTextArea);

        criticismsAddPanel.add(criticismsAddScrolPanel, java.awt.BorderLayout.CENTER);

        criticismsAddButton.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        criticismsAddButton.setText("Add");
        criticismsAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criticismsAddButtonActionPerformed(evt);
            }
        });
        criticismsAddPanel.add(criticismsAddButton, java.awt.BorderLayout.LINE_END);

        typoCritismsAddPanel.add(criticismsAddPanel, java.awt.BorderLayout.CENTER);

        authorResponcePanel.setLayout(new java.awt.BorderLayout());

        authorResponceLable.setText("Responses:");
        authorResponcePanel.add(authorResponceLable, java.awt.BorderLayout.PAGE_START);

        authorResponceTextPanel.setViewportView(authorResponceList);

        authorResponcePanel.add(authorResponceTextPanel, java.awt.BorderLayout.PAGE_END);

        typoCritismsAddPanel.add(authorResponcePanel, java.awt.BorderLayout.PAGE_END);

        jSplitPane2.setLeftComponent(typoCritismsAddPanel);

        verdictPanel.setLayout(new java.awt.BorderLayout());

        verdictText.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        verdictText.setText("Verdict");
        verdictPanel.add(verdictText, java.awt.BorderLayout.PAGE_START);

        verdictSelectList.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        verdictSelectList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "STRONG_REJECT", "WEAK_REJECT", "WEAK_ACCEPT", "STRONG_ACCEPT" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        verdictSelectList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                verdictSelectListValueChanged(evt);
            }
        });
        verdictList.setViewportView(verdictSelectList);

        verdictPanel.add(verdictList, java.awt.BorderLayout.CENTER);

        jSplitPane2.setRightComponent(verdictPanel);

        submitPannel.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        submitButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });
        submitPannel.add(submitButton, java.awt.BorderLayout.PAGE_END);

        mainPanel.add(submitPannel);

        add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed

        try {
            if (Database.read("Response",new Response(review.getEmail(), review.getIssn(), review.getSubmissionID(), null, null)).size() != 0) {

                int verdictIndex = verdictSelectList.getSelectedIndex();
                Verdict verdict = Verdict.valueOf(verdictIndex);
                Database.update("Review", review, new Review(null, null, null, null, null, verdict, null), false);

            }
            else {
                int verdictIndex = verdictSelectList.getSelectedIndex();
                Verdict verdict = Verdict.valueOf(verdictIndex);
                review.setVerdict(verdict);
                review.setSummary(summaryTextArea.getText());
                review.setTimestampNow();
                // review.setTimestamp(timestamp);
                try {
                    Database.write("Review", review);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                for (int i = 0; i < typoErrorListModel.getSize(); i++) {
                    try {
                        Database.write("TypoError", typoErrorListModel.get(i));
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                for (int i = 0; i < criticismsListModel.getSize(); i++) {
                    try {
                        Database.write("Criticism", criticismsListModel.get(i));
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            List<Review> reviewList= Database.read("Review", new Review(null, review.getIssn(), review.getSubmissionID(), null, null, null, null));
            if (reviewList.size() > 2) {
                Database.update("Submission", Database.read("Submission", new Submission(review.getIssn(), review.getSubmissionID(), null, null, null, null, null)).get(0), new Submission(null, null, null, null, null, null, Submission.Status.REVIEWED), false);
                boolean finalverdict = reviewList.get(0).getFinalVerdict() != null && reviewList.get(1).getFinalVerdict() != null && reviewList.get(2).getFinalVerdict() != null;
                if (finalverdict) {
                    Database.update("Submission", Database.read("Submission", new Submission(review.getIssn(), review.getSubmissionID(), null, null, null, null, null)).get(0), new Submission(null, null, null, null, null, null, Submission.Status.VERDICTED), false);
                }
            }

            submitButton.setEnabled(false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }//GEN-LAST:event_submitButtonActionPerformed

    private void typoErrorAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typoErrorAddButtonActionPerformed

        TypoError typoError = new TypoError(review.getEmail(), review.getIssn(), review.getSubmissionID(), typoErrorListModel.getSize(), typoErrorsTextArea.getText());
        typoErrorListModel.addElement(typoError);
        typoErrorsTextArea.setText("");

    }//GEN-LAST:event_typoErrorAddButtonActionPerformed

    private void criticismsAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criticismsAddButtonActionPerformed

        Criticism criticism = new Criticism(review.getEmail(), review.getIssn(), review.getSubmissionID(), criticismsListModel.getSize(), criticsmsTextArea.getText());
        criticismsListModel.addElement(criticism);
        criticsmsTextArea.setText("");

    }//GEN-LAST:event_criticismsAddButtonActionPerformed

    private void verdictSelectListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_verdictSelectListValueChanged
        // TODO delet the func
    }//GEN-LAST:event_verdictSelectListValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SubmissionLabel;
    private javax.swing.JLabel acceptableLabel;
    private javax.swing.JLabel addTypoText;
    private javax.swing.JLabel authorResponceLable;
    private javax.swing.JList<Response> authorResponceList;
    private javax.swing.JPanel authorResponcePanel;
    private javax.swing.JScrollPane authorResponceTextPanel;
    private javax.swing.JButton criticismsAddButton;
    private javax.swing.JPanel criticismsAddPanel;
    private javax.swing.JScrollPane criticismsAddScrolPanel;
    private javax.swing.JLabel criticismsAddText;
    private javax.swing.JLabel criticismsLabel;
    private javax.swing.JList<Criticism> criticismsList;
    private javax.swing.JScrollPane criticismsScrollPane;
    private javax.swing.JTextArea criticsmsTextArea;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel reviewerLabel;
    private javax.swing.JButton submitButton;
    private javax.swing.JPanel submitPannel;
    private javax.swing.JLabel summaryLabel;
    private javax.swing.JScrollPane summaryScrollPane;
    private javax.swing.JTextArea summaryTextArea;
    private javax.swing.JPanel typoCritismsAddPanel;
    private javax.swing.JButton typoErrorAddButton;
    private javax.swing.JScrollPane typoErrorAddScrolpanel;
    private javax.swing.JPanel typoErrorsAddPanel;
    private javax.swing.JLabel typoErrorsLabel;
    private javax.swing.JList<TypoError> typoErrorsList;
    private javax.swing.JScrollPane typoErrorsScrollPane;
    private javax.swing.JTextArea typoErrorsTextArea;
    private javax.swing.JScrollPane verdictList;
    private javax.swing.JPanel verdictPanel;
    private javax.swing.JList<String> verdictSelectList;
    private javax.swing.JLabel verdictText;
    // End of variables declaration//GEN-END:variables
}
