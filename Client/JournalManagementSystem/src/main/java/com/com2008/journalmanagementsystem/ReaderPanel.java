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
public class ReaderPanel extends javax.swing.JPanel {

    /**
     * Creates new form ReaderPanel
     */
    public ReaderPanel() {
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

        jSplitPane1 = new javax.swing.JSplitPane();
        JournalScrollPane = new javax.swing.JScrollPane();
        JournalTree = new javax.swing.JTree();
        articlePanel1 = new com.com2008.journalmanagementsystem.ArticlePanel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Journals");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Journal1");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Vol.1");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition1");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition2");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Vol.2");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition1");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Vol.3");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition1");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Journal2");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Vol.1");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition1");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition2");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition3");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Vol.2");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition1");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Journal3");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Vol.1");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Edition1");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        JournalTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        JournalScrollPane.setViewportView(JournalTree);

        jSplitPane1.setLeftComponent(JournalScrollPane);
        jSplitPane1.setRightComponent(articlePanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JournalScrollPane;
    private javax.swing.JTree JournalTree;
    private com.com2008.journalmanagementsystem.ArticlePanel articlePanel1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
