/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.com2008.journalmanagementsystem.model.Account;
import com.com2008.journalmanagementsystem.model.Article;
import com.com2008.journalmanagementsystem.model.Edition;
import com.com2008.journalmanagementsystem.model.Review;
import com.com2008.journalmanagementsystem.model.Submission;
import com.com2008.journalmanagementsystem.model.Submission.Status;
import com.com2008.journalmanagementsystem.model.SubmissionAuthor;
import com.com2008.journalmanagementsystem.model.SubmissionDocument;
import com.com2008.journalmanagementsystem.util.database.Database;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

    private Submission submission;
    private List<ResponsePanel> responsePanels;

    private List<java.awt.event.ActionListener> reloadRequestListeners = new ArrayList<java.awt.event.ActionListener>();

    public void addReloadRequestListener(java.awt.event.ActionListener reloadRequestListener) {
        reloadRequestListeners.add(reloadRequestListener);
    }

    public ArticlePanel(Submission submission, UserRole userRole, String email) {
        initComponents();

        this.submission = submission;

        confirmFinalPanel.setVisible(false);
        uploadFinalPDFBtn.setVisible(false);
        abstractTextArea.setEditable(false);

        // Load panels for each role
        switch (userRole) {
        case READER:
            decisionPanel.setVisible(false);
            reviewPanel.setVisible(false);
            break;
        case AUTHOR:
            decisionPanel.setVisible(false);

            if (submission.getStatus() == Submission.Status.REVIEWED && submission.getMainAuthor().equals(email)) {
                confirmFinalPanel.setVisible(true);
                try {
                    SubmissionDocument submissionDocument = Database.read("SubmissionDocument", new SubmissionDocument(submission.getIssn(), submission.getSubmissionID(), null, null)).get(0);
                    if(submissionDocument.getFinalDraft() == null)
                        uploadFinalPDFBtn.setVisible(true);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (submission.getStatus() == Submission.Status.REVIEWED) {
                if (submission.getMainAuthor().equals(email)) {
                    // Permissions for main author (response)
                    try {
                        List<Review> reviews = Database.read("Review", new Review(null, submission.getIssn(),submission.getSubmissionID(), null, null, null, null));
                        responsePanels = new ArrayList<>();
                        for (int i = 0; i < reviews.size(); i++) {
                            ResponsePanel responsePanel = new ResponsePanel("Reviewer" + (i + 1), reviews.get(i));
                            innerReviewPanel.add(responsePanel);
                            responsePanels.add(responsePanel);
                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    try {
                        List<Review> reviews = Database.read("Review", new Review(null, submission.getIssn(),
                                submission.getSubmissionID(), null, null, null, null));
                        for (int i = 0; i < reviews.size(); i++) {
                            ReviewPanel reviewPanel = new ReviewPanel("Reviewer" + (i + 1), reviews.get(i),
                                    UserRole.AUTHOR);
                            innerReviewPanel.add(reviewPanel);
                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
            else {
                try {
                    List<Review> reviews = Database.read("Review", new Review(null, submission.getIssn(),
                            submission.getSubmissionID(), null, null, null, null));
                    for (int i = 0; i < reviews.size(); i++) {
                        ReviewPanel reviewPanel = new ReviewPanel("Reviewer" + (i + 1), reviews.get(i),
                                UserRole.AUTHOR);
                        innerReviewPanel.add(reviewPanel);
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            break;
        case REVIEWER:
            toolbarPanel.setVisible(false);
            reviewPanel.setVisible(true);

            innerReviewPanel.removeAll();
            // A editable ReviewPanel for Reviewers only
            ReviewPanel reviewPanelReviewer = new ReviewPanel("Reviewer",
                    new Review(email, submission.getIssn(), submission.getSubmissionID(), null, null, null, null),
                    UserRole.REVIEWER);
            innerReviewPanel.add(reviewPanelReviewer);
            break;
        case EDITOR:
            try {
                List<Review> reviews = Database.read("Review", new Review(null, submission.getIssn(),
                        submission.getSubmissionID(), null, null, null, null));
                for (int i = 0; i < reviews.size(); i++) {
                    ReviewPanel reviewPanel = new ReviewPanel("Reviewer" + (i + 1), reviews.get(i),
                            UserRole.EDITOR);
                    innerReviewPanel.add(reviewPanel);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
        default:
            break;
        }

        acceptBtn.addActionListener(new StatusActionListener(submission,true));
        rejectBtn.addActionListener(new StatusActionListener(submission,false));

        // Load basic submission data
        titleLabel.setText(submission.getTitle());
        statusLabel.setText(submission.getStatus().toString());

        abstractTextArea.setText(submission.getContentAbstract());
        // TODO : unused linkLabel
        linkLabel.setText(null);

        try {
            Account mainAuthor = Database.read("Account", new Account(submission.getMainAuthor(), null, null, null, null)).get(0);
            Account corrAuthor = Database.read("Account", new Account(submission.getCorrAuthor(), null, null, null, null)).get(0);
            List<SubmissionAuthor> submissionAuthors = Database.read("SubmissionAuthor", new SubmissionAuthor(submission.getIssn(), submission.getSubmissionID(), null));
            List<String> coAuthorEmails = new ArrayList<String>();
            for (SubmissionAuthor submissionAuthor : submissionAuthors) {
                if (!submissionAuthor.getEmail().equals(submission.getMainAuthor()) && !submissionAuthor.getEmail().equals(submission.getCorrAuthor()))
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
                if (isFirst)
                    isFirst = false;
                else
                    coAuthorBuilder.append("<br/>");
                coAuthorBuilder.append(account.toString());
            }

            coAuthorLabel.setText(
                    "<html>" + coAuthorBuilder.toString().replace("<", "&lt;").replace(">", "&gt;") + "</html>");
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // TODO : remove this
        // Test review panel

        // if(reviewPanel.isVisible()){
        // innerReviewPanel.removeAll();
        // try {
        // List<Review> reviews = Database.read("Review", new Review(null,
        // submission.getIssn(), submission.getSubmissionID(), null, null, null, null));
        // for(int i = 0; i < reviews.size(); i++){
        // ReviewPanel reviewPanel = new ReviewPanel("Reviewer" + (i + 1),
        // reviews.get(i));
        // innerReviewPanel.add(reviewPanel);
        // }
        // } catch (SQLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
    }

    public JButton getSubmitAllResponsesBtn() {
        return confirmFinalBtn;
    }

    private class StatusActionListener implements ActionListener {
        //custom action listener for accept/reject buttons
        //sets the status to accepted or rejected based on button press
    	Submission su;
        boolean st;

        public StatusActionListener(Submission sub, boolean status) {
            //status of true -> accept button
            //status of false -> reject button
            this.su = sub;
            this.st = status;
        }

        public void actionPerformed(ActionEvent e) {
                try {
                    if (st){
                        Database.update("Submission",su,new Submission(null,null,null,null,null,null,Status.ACCEPTED),false);
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
                        		Database.write("Edition",new Edition(su.getIssn(),newestVolume+1,1));
                                Database.write("Article",new Article(su.getIssn(),su.getSubmissionID(),newestVolume+1,1));
                        	}
                        	else {
                        		Database.write("Edition",new Edition(su.getIssn(),newestVolume,newestEdition+1));
                                Database.write("Article",new Article(su.getIssn(),su.getSubmissionID(),newestVolume,newestEdition+1));
                        	}
                        }
                        else {
                            Database.write("Article",new Article(su.getIssn(),su.getSubmissionID(),newestVolume,newestEdition));
                        }
                    }
                    else {
                        Database.update("Submission",su,new Submission(null,null,null,null,null,null,Status.REJECTED),false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ArticlePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                //get the parent JFrame to close when accept/reject
                //button clicked
                JFrame fr = (JFrame)SwingUtilities.getRoot(acceptBtn);
                fr.setVisible(false);
        }
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
        abstractPanel = new javax.swing.JPanel();
        abstractLabel = new javax.swing.JLabel();
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
        openPDFBtn = new javax.swing.JButton();
        uploadFinalPDFBtn = new javax.swing.JButton();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        jSeparator2 = new javax.swing.JSeparator();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        reviewPanel = new javax.swing.JPanel();
        reviewsLabel = new javax.swing.JLabel();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        innerReviewPanel = new javax.swing.JPanel();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 15), new java.awt.Dimension(0, 15), new java.awt.Dimension(32767, 15));
        confirmFinalPanel = new javax.swing.JPanel();
        confirmFinalBtn = new javax.swing.JButton();
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

        abstractPanel.setLayout(new java.awt.BorderLayout());

        abstractLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        abstractLabel.setText("Abstract");
        abstractPanel.add(abstractLabel, java.awt.BorderLayout.NORTH);

        abstractTextArea.setColumns(20);
        abstractTextArea.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        abstractTextArea.setLineWrap(true);
        abstractTextArea.setRows(5);
        abstractTextArea.setText("This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  This is abstruct area  ");
        abstractTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(abstractTextArea);

        abstractPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        innerPanel.add(abstractPanel);
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

        openPDFBtn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        openPDFBtn.setText("Open PDF");
        openPDFBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPDFBtnActionPerformed(evt);
            }
        });
        innerPdfPanel.add(openPDFBtn);

        uploadFinalPDFBtn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        uploadFinalPDFBtn.setText("Upload final draft");
        uploadFinalPDFBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFinalPDFBtnActionPerformed(evt);
            }
        });
        innerPdfPanel.add(uploadFinalPDFBtn);

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

        confirmFinalPanel.setLayout(new java.awt.BorderLayout());

        confirmFinalBtn.setText("Confirm final draft & responses");
        confirmFinalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmFinalBtnActionPerformed(evt);
            }
        });
        confirmFinalPanel.add(confirmFinalBtn, java.awt.BorderLayout.CENTER);

        innerPanel.add(confirmFinalPanel);
        innerPanel.add(jSeparator3);

        mainPanel.add(innerPanel, java.awt.BorderLayout.CENTER);
        mainPanel.add(filler3, java.awt.BorderLayout.WEST);
        mainPanel.add(filler4, java.awt.BorderLayout.EAST);
        mainPanel.add(filler5, java.awt.BorderLayout.NORTH);
        mainPanel.add(filler6, java.awt.BorderLayout.SOUTH);

        mainScrollPane.setViewportView(mainPanel);

        add(mainScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmFinalBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_submitAllResponsesBtnActionPerformed
        try {
            SubmissionDocument submissionDocument = Database.read("SubmissionDocument", new SubmissionDocument(submission.getIssn(), submission.getSubmissionID(), null, null)).get(0);
            if(submissionDocument.getFinalDraft() == null){
                JOptionPane.showMessageDialog(this, "Final draft upload required", "Confirm", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if(!isFullResponsed()){
            int decision = JOptionPane.showOptionDialog(this, "Not all criticisms are responsed. Are you sure to continue?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
            if(decision == JOptionPane.CLOSED_OPTION || decision == JOptionPane.NO_OPTION)
                return;
        }
        try {
            Database.update("Submission", submission, new Submission(null, null, null, null, null, null, Submission.Status.RESPONSED), false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (ActionListener actionListener : reloadRequestListeners) {
            actionListener.actionPerformed(new java.awt.event.ActionEvent(this, 0, null));
        }
    }// GEN-LAST:event_submitAllResponsesBtnActionPerformed

    private boolean isFullResponsed(){
        for (ResponsePanel responsePanel : responsePanels) {
            if(!responsePanel.isFullResponsed())
                return false;
        }
        return true;
    }

    private void openPDFBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_openPDFBtnActionPerformed
        try {
            List<SubmissionDocument> submissionDocuments = Database.read("SubmissionDocument", new SubmissionDocument(submission.getIssn(), submission.getSubmissionID(), null, null));
            if(submissionDocuments.size() == 0){
                JOptionPane.showMessageDialog(this, "Document not found.", "Open document", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SubmissionDocument submissionDocument = submissionDocuments.get(0);

            File temp = File.createTempFile(UUID.randomUUID().toString(), ".pdf");
            temp.deleteOnExit();
            
            if(submissionDocument.getFinalDraft() != null)
                submissionDocument.downloadFinalDraft(temp);
            else
                submissionDocument.downloadFirstDraft(temp);

            java.awt.Desktop.getDesktop().open(temp);
        } catch (IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }// GEN-LAST:event_openPDFBtnActionPerformed

    private void uploadFinalPDFBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_uploadFinalPDFBtnActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filepath = fileChooser.getSelectedFile().getAbsolutePath();
            // TODO : Check format
            try {
                Database.update("SubmissionDocument", new SubmissionDocument(submission.getIssn(), submission.getSubmissionID(), null, null), new SubmissionDocument(null, null, null, new File(filepath)), false);

                JOptionPane.showMessageDialog(this, "Upload success.", "Upload final fraft", JOptionPane.INFORMATION_MESSAGE);
                uploadFinalPDFBtn.setVisible(false);
            }
            catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel abstractLabel;
    private javax.swing.JPanel abstractPanel;
    private javax.swing.JTextArea abstractTextArea;
    private javax.swing.JButton acceptBtn;
    private javax.swing.JPanel authorPanel;
    private javax.swing.JLabel authorsLabel;
    private javax.swing.JLabel coAuthorHeaderLabel;
    private javax.swing.JLabel coAuthorLabel;
    private javax.swing.JButton confirmFinalBtn;
    private javax.swing.JPanel confirmFinalPanel;
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
    private javax.swing.JButton openPDFBtn;
    private javax.swing.JLabel pdfLabel;
    private javax.swing.JPanel pdfPanel;
    private javax.swing.JButton rejectBtn;
    private javax.swing.JPanel reviewPanel;
    private javax.swing.JLabel reviewsLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JScrollPane titleScrollPane;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JButton uploadFinalPDFBtn;
    // End of variables declaration//GEN-END:variables
}
