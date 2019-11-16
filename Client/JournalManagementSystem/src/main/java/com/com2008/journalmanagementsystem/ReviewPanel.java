/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem;

/**
 *
 * @author Xuan
 */
public class ReviewPanel extends javax.swing.JPanel {

    /**
     * Creates new form ReviewPanel
     */
    public ReviewPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jPanel1 = new javax.swing.JPanel();
        reviewerLabel = new javax.swing.JLabel();
        acceptableLabel = new javax.swing.JLabel();
        summaryLabel = new javax.swing.JLabel();
        summaryScrollPane = new javax.swing.JScrollPane();
        summaryTextArea = new javax.swing.JTextArea();
        TypoErrorsLabel = new javax.swing.JLabel();
        TypoErrorsScrollPane = new javax.swing.JScrollPane();
        TypoErrorsList = new javax.swing.JList<>();
        CristicismsLabel = new javax.swing.JLabel();
        CristicismsScrollPane = new javax.swing.JScrollPane();
        CristicismsList = new javax.swing.JList<>();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jSeparator1 = new javax.swing.JSeparator();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));
        add(filler1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        reviewerLabel.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        reviewerLabel.setText("Reviewer ID");
        jPanel1.add(reviewerLabel, java.awt.BorderLayout.CENTER);

        acceptableLabel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        acceptableLabel.setForeground(new java.awt.Color(0, 153, 0));
        acceptableLabel.setText("Acceptable");
        jPanel1.add(acceptableLabel, java.awt.BorderLayout.EAST);

        add(jPanel1);

        summaryLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        summaryLabel.setText("Summary");
        add(summaryLabel);

        summaryTextArea.setColumns(20);
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setRows(5);
        summaryTextArea.setText("Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  Review summary  ");
        summaryTextArea.setWrapStyleWord(true);
        summaryScrollPane.setViewportView(summaryTextArea);

        add(summaryScrollPane);

        TypoErrorsLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        TypoErrorsLabel.setText("Typo errors");
        add(TypoErrorsLabel);

        TypoErrorsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Typo error 1", "Typo error 2", "Typo error 3", "Typo error 4", "Typo error 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        TypoErrorsScrollPane.setViewportView(TypoErrorsList);

        add(TypoErrorsScrollPane);

        CristicismsLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        CristicismsLabel.setText("Cristicisms");
        add(CristicismsLabel);

        CristicismsList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Cristicisms 1", "Cristicisms 2", "Cristicisms 3", "Cristicisms 4", "Cristicisms 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        CristicismsScrollPane.setViewportView(CristicismsList);

        add(CristicismsScrollPane);
        add(filler2);
        add(jSeparator1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CristicismsLabel;
    private javax.swing.JList<String> CristicismsList;
    private javax.swing.JScrollPane CristicismsScrollPane;
    private javax.swing.JLabel TypoErrorsLabel;
    private javax.swing.JList<String> TypoErrorsList;
    private javax.swing.JScrollPane TypoErrorsScrollPane;
    private javax.swing.JLabel acceptableLabel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel reviewerLabel;
    private javax.swing.JLabel summaryLabel;
    private javax.swing.JScrollPane summaryScrollPane;
    private javax.swing.JTextArea summaryTextArea;
    // End of variables declaration//GEN-END:variables
}
