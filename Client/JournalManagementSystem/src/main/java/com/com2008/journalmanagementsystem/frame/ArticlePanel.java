/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.com2008.journalmanagementsystem.model.Account;
import com.com2008.journalmanagementsystem.model.Author;
import com.com2008.journalmanagementsystem.model.Review;
import com.com2008.journalmanagementsystem.model.Submission;
import com.com2008.journalmanagementsystem.model.SubmissionAuthor;
import com.com2008.journalmanagementsystem.util.database.Database;

/**
 *
 * @author Xuan
 */
public class ArticlePanel extends javax.swing.JPanel {

    /**
     * Creates new form ArticlePanel
     */
    public ArticlePanel() {
        initComponents();
    }

    public ArticlePanel(Submission submission, UserRole userRole, String email) {
        initComponents();

        switch(userRole){
            case READER:
                decisionPanel.setVisible(false);
                reviewPanel.setVisible(false);
                break;
            case AUTHOR:
                decisionPanel.setVisible(false);
                if(submission.getMainAuthor().equals(email)){
                    // TODO : Permissions for main author
                }
                break;
            case REVIEWER:
                toolbarPanel.setVisible(false);
                reviewPanel.setVisible(true);
            default:
                break;
        }

        titleLabel.setText(submission.getTitle());
        statusLabel.setText(submission.getStatus().toString());

        abstractTextArea.setText(submission.getContentAbstract());
        if (submission.getFinalID() == null)
            linkLabel.setText(submission.getDraftID());
        else
            linkLabel.setText(submission.getFinalID());

        try {
            Account mainAuthor = Database.read("Account", new Account(submission.getMainAuthor(), null, null, null, null)).get(0);
            Account corrAuthor = Database.read("Account", new Account(submission.getCorrAuthor(), null, null, null, null)).get(0);
            List<SubmissionAuthor> submissionAuthors = Database.read("SubmissionAuthor", new SubmissionAuthor(submission.getIssn(), submission.getSubmissionID(), null));
            List<String> coAuthorEmails = new ArrayList<String>();
            for (SubmissionAuthor submissionAuthor : submissionAuthors) {
                if(!submissionAuthor.getEmail().equals(submission.getMainAuthor()) && !submissionAuthor.getEmail().equals(submission.getCorrAuthor()))
                    coAuthorEmails.add(submissionAuthor.getEmail());
            }
            List<Account> coAuthors = new ArrayList<Account>();
            for (String coAuthoremail : coAuthorEmails) {
                coAuthors.add(Database.read("Account", new Account(coAuthoremail, null, null, null, null)).get(0));
            }

            mainAuthorLabel.setText(mainAuthor.toString());
            corrAuthorLabel.setText(corrAuthor.toString());

            StringBuilder coAuthorBuilder = new StringBuilder();
            boolean isFirst = true;
            for (Account account : coAuthors) {
                if(isFirst)
                    isFirst = false;
                else
                    coAuthorBuilder.append("<br/>");
                coAuthorBuilder.append(account.toString());
            }

            coAuthorLabel.setText("<html>" + coAuthorBuilder.toString().replace("<", "&lt;").replace(">", "&gt;") + "</html>");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        

        if(reviewPanel.isVisible()){
            innerReviewPanel.removeAll();
            try {
                List<Review> reviews = Database.read("Review", new Review(null, submission.getIssn(), submission.getSubmissionID(), null, null, null));
                for(int i = 0; i < reviews.size(); i++){
                    ReviewPanel reviewPanel = new ReviewPanel("Reviewer" + (i + 1), reviews.get(i));
                    innerReviewPanel.add(reviewPanel);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(reviewPanel.isVisible() && userRole==UserRole.REVIEWER){
            innerReviewPanel.removeAll();
            try {
                List<Review> reviews = Database.read("Review", new Review(null, submission.getIssn(), submission.getSubmissionID(), null, null, null));
                for(int i = 0; i < reviews.size(); i++){
                    ReviewPanel reviewPanel = new ReviewPanel("Reviewer" + (i + 1), reviews.get(i),UserRole.REVIEWER);
                    innerReviewPanel.add(reviewPanel);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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

        headerPanel = new javax.swing.JPanel();
        titleScrollPane = new javax.swing.JScrollPane();
        titleLabel = new javax.swing.JLabel();
        toolbarPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        decisionPanel = new javax.swing.JPanel();
        acceptBtn = new javax.swing.JButton();
        rejectBtn = new javax.swing.JButton();
        mainScrollPane = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        innerPanel = new javax.swing.JPanel();
        abstructPanel = new javax.swing.JPanel();
        abstructLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        abstractTextArea = new javax.swing.JTextArea();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        jSeparator1 = new javax.swing.JSeparator();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        authorPanel = new javax.swing.JPanel();
        innerAuthorPanel = new javax.swing.JPanel();
        authorsLabel = new javax.swing.JLabel();
        mainAuthorHeaderLabel = new javax.swing.JLabel();
        mainAuthorLabel = new javax.swing.JLabel();
        corrAuthorHeaderLabel = new javax.swing.JLabel();
        corrAuthorLabel = new javax.swing.JLabel();
        coAuthorHeaderLabel = new javax.swing.JLabel();
        coAuthorLabel = new javax.swing.JLabel();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        jSeparator4 = new javax.swing.JSeparator();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        pdfPanel = new javax.swing.JPanel();
        innerPdfPanel = new javax.swing.JPanel();
        pdfLabel = new javax.swing.JLabel();
        linkLabel = new javax.swing.JLabel();
        copyLinkBtn = new javax.swing.JButton();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        jSeparator2 = new javax.swing.JSeparator();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        reviewPanel = new javax.swing.JPanel();
        reviewsLabel = new javax.swing.JLabel();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        innerReviewPanel = new javax.swing.JPanel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        jSeparator3 = new javax.swing.JSeparator();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));

        setLayout(new java.awt.BorderLayout());

        headerPanel.setLayout(new java.awt.BorderLayout());

        titleScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        titleLabel.setText("Title of the article  Title of the article  Title of the article  Title of the article  Title of the article  Title of the article");
        titleLabel.setToolTipText("");
        titleScrollPane.setViewportView(titleLabel);

        headerPanel.add(titleScrollPane, java.awt.BorderLayout.CENTER);

        toolbarPanel.setLayout(new java.awt.GridLayout(2, 1, 0, 8));

        statusLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        statusLabel.setText("Status");
        toolbarPanel.add(statusLabel);

        decisionPanel.setLayout(new javax.swing.BoxLayout(decisionPanel, javax.swing.BoxLayout.LINE_AXIS));

        acceptBtn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        acceptBtn.setText("Accept");
        decisionPanel.add(acceptBtn);

        rejectBtn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        rejectBtn.setText("Reject");
        decisionPanel.add(rejectBtn);

        toolbarPanel.add(decisionPanel);

        headerPanel.add(toolbarPanel, java.awt.BorderLayout.EAST);

        add(headerPanel, java.awt.BorderLayout.NORTH);

        mainPanel.setLayout(new java.awt.BorderLayout());

        innerPanel.setLayout(new javax.swing.BoxLayout(innerPanel, javax.swing.BoxLayout.PAGE_AXIS));

        abstructPanel.setLayout(new java.awt.BorderLayout());

        abstructLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        abstructLabel.setText("Abstruct");
        abstructPanel.add(abstructLabel, java.awt.BorderLayout.NORTH);

        abstractTextArea.setColumns(20);
        abstractTextArea.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        abstractTextArea.setLineWrap(true);
        abstractTextArea.setRows(5);
        abstractTextArea.setText("This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  ");
        abstractTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(abstractTextArea);

        abstructPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        innerPanel.add(abstructPanel);
        innerPanel.add(filler1);
        innerPanel.add(jSeparator1);
        innerPanel.add(filler2);

        authorPanel.setLayout(new java.awt.BorderLayout());

        innerAuthorPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        innerAuthorPanel.setLayout(new javax.swing.BoxLayout(innerAuthorPanel, javax.swing.BoxLayout.PAGE_AXIS));

        authorsLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        authorsLabel.setText("Authors");
        innerAuthorPanel.add(authorsLabel);

        mainAuthorHeaderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        mainAuthorHeaderLabel.setText("Main author:");
        innerAuthorPanel.add(mainAuthorHeaderLabel);

        mainAuthorLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        mainAuthorLabel.setText("mainAuthor <email@email.com>");
        innerAuthorPanel.add(mainAuthorLabel);

        corrAuthorHeaderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        corrAuthorHeaderLabel.setText("Corresponding author:");
        innerAuthorPanel.add(corrAuthorHeaderLabel);

        corrAuthorLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        corrAuthorLabel.setText("mainAuthor <email@email.com>");
        innerAuthorPanel.add(corrAuthorLabel);

        coAuthorHeaderLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        coAuthorHeaderLabel.setText("Co-author:");
        innerAuthorPanel.add(coAuthorHeaderLabel);

        coAuthorLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        coAuthorLabel.setText("mainAuthor <email@email.com>");
        innerAuthorPanel.add(coAuthorLabel);

        authorPanel.add(innerAuthorPanel, java.awt.BorderLayout.CENTER);

        innerPanel.add(authorPanel);
        innerPanel.add(filler11);
        innerPanel.add(jSeparator4);
        innerPanel.add(filler7);

        pdfPanel.setLayout(new java.awt.BorderLayout());

        innerPdfPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        innerPdfPanel.setLayout(new javax.swing.BoxLayout(innerPdfPanel, javax.swing.BoxLayout.PAGE_AXIS));

        pdfLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        pdfLabel.setText("PDF link");
        innerPdfPanel.add(pdfLabel);

        linkLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        linkLabel.setText("https://link.pdf.here");
        innerPdfPanel.add(linkLabel);

        copyLinkBtn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        copyLinkBtn.setText("Copy Link");
        innerPdfPanel.add(copyLinkBtn);

        pdfPanel.add(innerPdfPanel, java.awt.BorderLayout.CENTER);

        innerPanel.add(pdfPanel);
        innerPanel.add(filler8);
        innerPanel.add(jSeparator2);
        innerPanel.add(filler9);

        reviewPanel.setLayout(new java.awt.BorderLayout());

        reviewsLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        reviewsLabel.setText("Reviews");
        reviewPanel.add(reviewsLabel, java.awt.BorderLayout.NORTH);
        reviewPanel.add(filler12, java.awt.BorderLayout.PAGE_END);

        innerReviewPanel.setLayout(new javax.swing.BoxLayout(innerReviewPanel, javax.swing.BoxLayout.PAGE_AXIS));
        reviewPanel.add(innerReviewPanel, java.awt.BorderLayout.CENTER);

        innerPanel.add(reviewPanel);
        innerPanel.add(filler10);
        innerPanel.add(jSeparator3);

        mainPanel.add(innerPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(filler3, java.awt.BorderLayout.WEST);
        mainPanel.add(filler4, java.awt.BorderLayout.EAST);
        mainPanel.add(filler5, java.awt.BorderLayout.NORTH);
        mainPanel.add(filler6, java.awt.BorderLayout.SOUTH);

        mainScrollPane.setViewportView(mainPanel);

        add(mainScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea abstractTextArea;
    private javax.swing.JLabel abstructLabel;
    private javax.swing.JPanel abstructPanel;
    private javax.swing.JButton acceptBtn;
    private javax.swing.JPanel authorPanel;
    private javax.swing.JLabel authorsLabel;
    private javax.swing.JLabel coAuthorHeaderLabel;
    private javax.swing.JLabel coAuthorLabel;
    private javax.swing.JButton copyLinkBtn;
    private javax.swing.JLabel corrAuthorHeaderLabel;
    private javax.swing.JLabel corrAuthorLabel;
    private javax.swing.JPanel decisionPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel innerAuthorPanel;
    private javax.swing.JPanel innerPanel;
    private javax.swing.JPanel innerPdfPanel;
    private javax.swing.JPanel innerReviewPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel linkLabel;
    private javax.swing.JLabel mainAuthorHeaderLabel;
    private javax.swing.JLabel mainAuthorLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JLabel pdfLabel;
    private javax.swing.JPanel pdfPanel;
    private javax.swing.JButton rejectBtn;
    private javax.swing.JPanel reviewPanel;
    private javax.swing.JLabel reviewsLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JScrollPane titleScrollPane;
    private javax.swing.JPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables
}
