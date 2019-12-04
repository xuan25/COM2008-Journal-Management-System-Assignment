/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.com2008.journalmanagementsystem.model.Account;
import com.com2008.journalmanagementsystem.model.Author;
import com.com2008.journalmanagementsystem.model.Journal;
import com.com2008.journalmanagementsystem.model.Submission;
import com.com2008.journalmanagementsystem.model.SubmissionAuthor;
import com.com2008.journalmanagementsystem.util.database.Database;

/**
 *
 * @author boxuanshan
 */
public class SubmissionPanel extends javax.swing.JPanel {

    /**
     * Creates new form SubmissionPanel
     */
    public SubmissionPanel() {
        initComponents();
    }

    public SubmissionPanel(String email) {
        initComponents();

        this.email = email;

        coAuthorListModel = new DefaultListModel<Account>();
        coAuthorList.setModel(coAuthorListModel);

        DefaultComboBoxModel<Journal> journalComboBoxModel = new DefaultComboBoxModel<Journal>();
        try {
            List<Journal> journals = Database.read("Journal", new Journal());
            for (Journal journal : journals) {
                journalComboBoxModel.addElement(journal);
            }
            journalComboBox.setModel(journalComboBoxModel);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String email;
    private File pdfFile;
    private Account corrAuthor;
    private DefaultListModel<Account> coAuthorListModel;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        journalPanel = new javax.swing.JPanel();
        journalLabel = new javax.swing.JLabel();
        journalComboBox = new javax.swing.JComboBox<>();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        jSeparator1 = new javax.swing.JSeparator();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        filePanel = new javax.swing.JPanel();
        fileLabel = new javax.swing.JLabel();
        chooseFileBtn = new javax.swing.JButton();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        jSeparator2 = new javax.swing.JSeparator();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        corrAuthorPanel = new javax.swing.JPanel();
        corrAuthorEmailLabel = new javax.swing.JLabel();
        corrAuthorTextField = new javax.swing.JTextField();
        setCorrAuthorBtn = new javax.swing.JButton();
        selectedCorrAuthorPanel = new javax.swing.JPanel();
        corrAuthorLabel = new javax.swing.JLabel();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        jSeparator3 = new javax.swing.JSeparator();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        coAuthorPanel = new javax.swing.JPanel();
        coAuthorEmailLabel = new javax.swing.JLabel();
        coAuthorTextField = new javax.swing.JTextField();
        addCoAuthorBtn = new javax.swing.JButton();
        removeCoAuthorBtn = new javax.swing.JButton();
        coAuthorScrollPane = new javax.swing.JScrollPane();
        coAuthorList = new javax.swing.JList<>();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        jSeparator4 = new javax.swing.JSeparator();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        abstractLabel = new javax.swing.JLabel();
        abstractScrollPane = new javax.swing.JScrollPane();
        abstractTextArea = new javax.swing.JTextArea();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        jSeparator5 = new javax.swing.JSeparator();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10),
                new java.awt.Dimension(32767, 10));
        submitPanel = new javax.swing.JPanel();
        submitBtn = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 32767));

        setLayout(new java.awt.BorderLayout());

        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.PAGE_AXIS));

        journalPanel.setLayout(new javax.swing.BoxLayout(journalPanel, javax.swing.BoxLayout.LINE_AXIS));

        journalLabel.setText("Journal to submit:");
        journalPanel.add(journalLabel);

        journalComboBox.setMaximumSize(new java.awt.Dimension(32767, 27));
        journalPanel.add(journalComboBox);

        mainPanel.add(journalPanel);
        mainPanel.add(filler2);
        mainPanel.add(jSeparator1);
        mainPanel.add(filler3);

        titlePanel.setLayout(new javax.swing.BoxLayout(titlePanel, javax.swing.BoxLayout.LINE_AXIS));

        titleLabel.setText("Title: ");
        titlePanel.add(titleLabel);

        titleTextField.setMaximumSize(new java.awt.Dimension(2147483647, 26));
        titlePanel.add(titleTextField);

        mainPanel.add(titlePanel);

        filePanel.setMaximumSize(new java.awt.Dimension(2147483647, 45));
        filePanel.setLayout(new java.awt.BorderLayout());

        fileLabel.setText("<No file selected>");
        filePanel.add(fileLabel, java.awt.BorderLayout.CENTER);

        chooseFileBtn.setText("Choose a file...");
        chooseFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFileBtnActionPerformed(evt);
            }
        });
        filePanel.add(chooseFileBtn, java.awt.BorderLayout.EAST);

        mainPanel.add(filePanel);
        mainPanel.add(filler4);
        mainPanel.add(jSeparator2);
        mainPanel.add(filler5);

        corrAuthorPanel.setLayout(new javax.swing.BoxLayout(corrAuthorPanel, javax.swing.BoxLayout.LINE_AXIS));

        corrAuthorEmailLabel.setText("Email:");
        corrAuthorPanel.add(corrAuthorEmailLabel);

        corrAuthorTextField.setMaximumSize(new java.awt.Dimension(2147483647, 26));
        corrAuthorPanel.add(corrAuthorTextField);

        setCorrAuthorBtn.setText("Set Corr-author");
        setCorrAuthorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setCorrAuthorBtnActionPerformed(evt);
            }
        });
        corrAuthorPanel.add(setCorrAuthorBtn);

        mainPanel.add(corrAuthorPanel);

        selectedCorrAuthorPanel.setMaximumSize(new java.awt.Dimension(2147483647, 16));
        selectedCorrAuthorPanel.setLayout(new java.awt.BorderLayout());

        corrAuthorLabel.setText("<No corr-author setted>");
        selectedCorrAuthorPanel.add(corrAuthorLabel, java.awt.BorderLayout.CENTER);

        mainPanel.add(selectedCorrAuthorPanel);
        mainPanel.add(filler6);
        mainPanel.add(jSeparator3);
        mainPanel.add(filler7);

        coAuthorPanel.setLayout(new javax.swing.BoxLayout(coAuthorPanel, javax.swing.BoxLayout.LINE_AXIS));

        coAuthorEmailLabel.setText("Email:");
        coAuthorPanel.add(coAuthorEmailLabel);

        coAuthorTextField.setMaximumSize(new java.awt.Dimension(2147483647, 26));
        coAuthorPanel.add(coAuthorTextField);

        addCoAuthorBtn.setText("Add Co-author");
        addCoAuthorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCoAuthorBtnActionPerformed(evt);
            }
        });
        coAuthorPanel.add(addCoAuthorBtn);

        removeCoAuthorBtn.setText("Remove");
        removeCoAuthorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCoAuthorBtnActionPerformed(evt);
            }
        });
        coAuthorPanel.add(removeCoAuthorBtn);

        mainPanel.add(coAuthorPanel);

        coAuthorScrollPane.setViewportView(coAuthorList);

        mainPanel.add(coAuthorScrollPane);
        mainPanel.add(filler8);
        mainPanel.add(jSeparator4);
        mainPanel.add(filler9);

        abstractLabel.setText("Abstract:");
        mainPanel.add(abstractLabel);

        abstractTextArea.setColumns(20);
        abstractTextArea.setRows(5);
        abstractScrollPane.setViewportView(abstractTextArea);

        mainPanel.add(abstractScrollPane);
        mainPanel.add(filler10);
        mainPanel.add(jSeparator5);
        mainPanel.add(filler11);

        submitPanel.setMaximumSize(new java.awt.Dimension(2147483647, 29));
        submitPanel.setLayout(new java.awt.BorderLayout());

        submitBtn.setText("Submit");
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });
        submitPanel.add(submitBtn, java.awt.BorderLayout.CENTER);

        mainPanel.add(submitPanel);
        mainPanel.add(filler1);

        jScrollPane1.setViewportView(mainPanel);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void chooseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_chooseFileBtnActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            pdfFile = fileChooser.getSelectedFile();
            // TODO : Check format
            fileLabel.setText(pdfFile.getName());
        }

    }// GEN-LAST:event_chooseFileBtnActionPerformed

    private Account tryGetAccount(String email) {
        try {
            List<Author> authors = Database.read("Author", new Author(email, null));

            if (authors.size() > 0) {
                Account account = Database.read("Account", new Account(email, null, null, null, null)).get(0);
                return account;
            } else {
                RegisterDialog dialog = new RegisterDialog(null, true, email);
                dialog.setVisible(true);
                if (dialog.isRegistered()) {
                    Account account = dialog.getAccount();
                    String hashedPassword = dialog.getHashedPassword();
                    try {
                        if (Database.write("Account", account) == 1) {
                            if (Database.write("Author", new Author(account.getEmail(), hashedPassword)) == 1) {
                                JOptionPane.showMessageDialog(null, "registration success", "Register", JOptionPane.INFORMATION_MESSAGE);
                                return account;
                            } else {
                                // TODO : Regiter failed
                                return null;
                            }
                        } else {
                            // TODO : Regiter failed
                            return null;
                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    private void setCorrAuthorBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_setCorrAuthorBtnActionPerformed
        String email = corrAuthorTextField.getText();
        Account account = tryGetAccount(email);
        if (account != null) {
            corrAuthor = account;
            corrAuthorLabel.setText(account.toString());
        }
    }

    private void addCoAuthorBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addCoAuthorBtnActionPerformed
        String email = coAuthorTextField.getText();
        Account account = tryGetAccount(email);
        if (account != null) {
            coAuthorListModel.addElement(account);
        }
    }// GEN-LAST:event_addCoAuthorBtnActionPerformed

    private void removeCoAuthorBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removeCoAuthorBtnActionPerformed
        Account account = coAuthorList.getSelectedValue();
        if (account != null) {
            coAuthorListModel.removeElement(account);
        }
    }// GEN-LAST:event_removeCoAuthorBtnActionPerformed

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_submitBtnActionPerformed
        if(journalComboBox.getSelectedItem() == null || titleTextField.getText().isEmpty() || this.corrAuthor == null || abstractTextArea.getText().isEmpty() || pdfFile == null){
            JOptionPane.showMessageDialog(this, "Please complete the form.", "Submission", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!pdfFile.exists()){
            JOptionPane.showMessageDialog(this, "File does not exists.", "Submission", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            // Submission
            String issn = ((Journal) journalComboBox.getSelectedItem()).getIssn();
            String submissionID = UUID.randomUUID().toString();
            String title = titleTextField.getText();
            String mainAuthor = email;
            String corrAuthor = this.corrAuthor.getEmail();
            String contentAbstract = abstractTextArea.getText();
            String draftID = Database.uploadDocument("Document", pdfFile.getAbsolutePath());
            String finalID = null;
            Submission.Status status = Submission.Status.SUBMITTED;
            Submission submission = new Submission(issn, submissionID, title, mainAuthor, corrAuthor, contentAbstract, draftID, finalID, status);

            // Authors set
            HashSet<String> authorsSet = new HashSet<String>();
            authorsSet.add(mainAuthor);
            authorsSet.add(corrAuthor);
            for(int i = 0; i < coAuthorListModel.getSize(); i++){
                authorsSet.add(coAuthorListModel.getElementAt(i).getEmail());
            }

            // Database
            Database.write("Submission", submission);
            for(String author : authorsSet){
                Database.write("SubmissionAuthor", new SubmissionAuthor(issn, submissionID, author));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Submission success.", "Submission", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_submitBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel abstractLabel;
    private javax.swing.JScrollPane abstractScrollPane;
    private javax.swing.JTextArea abstractTextArea;
    private javax.swing.JButton addCoAuthorBtn;
    private javax.swing.JButton chooseFileBtn;
    private javax.swing.JLabel coAuthorEmailLabel;
    private javax.swing.JList<Account> coAuthorList;
    private javax.swing.JPanel coAuthorPanel;
    private javax.swing.JScrollPane coAuthorScrollPane;
    private javax.swing.JTextField coAuthorTextField;
    private javax.swing.JLabel corrAuthorEmailLabel;
    private javax.swing.JLabel corrAuthorLabel;
    private javax.swing.JPanel corrAuthorPanel;
    private javax.swing.JTextField corrAuthorTextField;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JPanel filePanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JComboBox<Journal> journalComboBox;
    private javax.swing.JLabel journalLabel;
    private javax.swing.JPanel journalPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton removeCoAuthorBtn;
    private javax.swing.JPanel selectedCorrAuthorPanel;
    private javax.swing.JButton setCorrAuthorBtn;
    private javax.swing.JButton submitBtn;
    private javax.swing.JPanel submitPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JTextField titleTextField;
    // End of variables declaration//GEN-END:variables
}
