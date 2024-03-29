/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem.frame;

import java.awt.*;
import javax.swing.*;

import com.com2008.journalmanagementsystem.model.Account;
import com.com2008.journalmanagementsystem.model.Journal;
import com.com2008.journalmanagementsystem.util.database.Database;

import java.awt.event.*;
import java.sql.SQLException;

/**
 *
 * @author Xuan
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        selectionsPanel.add(new SidePanelButton("Journals", new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                activeSidePanelBtn((SidePanelButton) evt.getSource());
                updateMainPanel(new ReaderPanel());
            }
        }));

        selectionsPanel.add(new SidePanelButton("Staff management", new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                activeSidePanelBtn((SidePanelButton) evt.getSource());
                updateMainPanel(new StaffManagementPanel());
            }
        }));

        selectionsPanel.add(new SidePanelButton("Publish", new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                activeSidePanelBtn((SidePanelButton) evt.getSource());
                updateMainPanel(new ProgressManagementPanel());
            }
        }));

        selectionsPanel.add(new SidePanelButton("Profile", new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                activeSidePanelBtn((SidePanelButton) evt.getSource());
                updateMainPanel(new ProfilePanel());
            }
        }));
        
        selectionsPanel.add(new SidePanelButton("Reviewer", new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                activeSidePanelBtn((SidePanelButton) evt.getSource());
                updateMainPanel(new ReviewerPanel(email));
            }
        }));
        
    }

    private LoginFrame parent;
    private String email;

    public MainFrame(LoginFrame parent, UserRole userRole, String email) {
        initComponents();

        this.parent = parent;
        this.email = email;

        if (email != null) {
            try {
                java.util.List<Account> accounts = Database.read("Account", new Account(email, null, null, null, null));
                if(accounts.size() > 0)
                    usernameLabel.setText(accounts.get(0).getForename());
                // TODO : If user not found
            } catch (SQLException e) {
                // TODO : If user not found
                e.printStackTrace();
            }
        }

        switch(userRole){
            case READER:
                usernameLabel.setText("Reader");
                selectionsPanel.add(new SidePanelButton("Journals", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton)evt.getSource());
                        updateMainPanel(new ReaderPanel());
                    }
                }));
                break;
            case AUTHOR:
                selectionsPanel.add(new SidePanelButton("Submissions", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton)evt.getSource());
                        updateMainPanel(new AuthorPanel(email));
                    }
                }));
                selectionsPanel.add(new SidePanelButton("New submission", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton)evt.getSource());
                        updateMainPanel(new SubmissionPanel(email));
                    }
                }));
                selectionsPanel.add(new SidePanelButton("Change password", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton)evt.getSource());
                        updateMainPanel(new ProfilePanel(userRole, email));
                    }
                }));
                break;
            case REVIEWER:
                selectionsPanel.add(new SidePanelButton("Reviewer", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton) evt.getSource());
                        updateMainPanel(new ReviewerPanel(email));
                    }
                }));
                selectionsPanel.add(new SidePanelButton("Change password", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton)evt.getSource());
                        updateMainPanel(new ProfilePanel(userRole, email));
                    }
                }));
                break;
            case EDITOR:
            	selectionsPanel.add(new SidePanelButton("Change password", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton)evt.getSource());
                        updateMainPanel(new ProfilePanel(userRole, email));
                    }
                }));
            	selectionsPanel.add(new SidePanelButton("Retire", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton)evt.getSource());
                        updateMainPanel(new RetirePanel(email));
                    }
                }));
                selectionsPanel.add(new SidePanelButton("Accept/Reject Articles", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton) evt.getSource());
                        updateMainPanel(new DecisionPanel(email));
                    }
                }));
                selectionsPanel.add(new SidePanelButton("Create New Journal", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton) evt.getSource());
                        updateMainPanel(new CreateJournalPanel(email));
                    }
                }));
                selectionsPanel.add(new SidePanelButton("Add Editor to Journal", new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        activeSidePanelBtn((SidePanelButton) evt.getSource());
                        updateMainPanel(new AddEditorPanel(email));
                    }
                }));
            	try {
                    java.util.List<Journal> journals = Database.read("Journal", new Journal(null, null, email));
                	if (journals.size() > 0) {
                        selectionsPanel.add(new SidePanelButton("Pass Chief Editor", new MouseAdapter() {
                            public void mousePressed(MouseEvent evt) {
                                activeSidePanelBtn((SidePanelButton) evt.getSource());
                                updateMainPanel(new PassChiefPanel(email));
                            }
                        }));
                        selectionsPanel.add(new SidePanelButton("Publish Journal", new MouseAdapter() {
                            public void mousePressed(MouseEvent evt) {
                                activeSidePanelBtn((SidePanelButton) evt.getSource());
                                updateMainPanel(new PublishPanel(email));
                            }
                        }));
                     
                	}
                    // TODO : If user not found
                } catch (SQLException e) {
                    // TODO : If user not found
                    e.printStackTrace();
                }
            	break;
            default:
                break;
        }
    }
    
    private void activeSidePanelBtn(SidePanelButton btn){
        for(Component comp : selectionsPanel.getComponents()){
            ((SidePanelButton)comp).reset();
        }
        btn.active();
    }
    
    private void updateMainPanel(Component comp){
        mainPanel.removeAll();
        mainPanel.add(comp, BorderLayout.CENTER);
        mainPanel.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        welcomePanel = new javax.swing.JPanel();
        welcomeInnerPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        selectionsPanel = new javax.swing.JPanel();
        logoutBtn = new javax.swing.JPanel();
        selectionLabel4 = new javax.swing.JLabel();
        filler21 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler22 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        filler23 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler24 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Journal Management System");
        setLocation(getStartupLocation());
        setPreferredSize(getFrameSize());
        setSize(getFrameSize());

        bg.setLayout(new javax.swing.BoxLayout(bg, javax.swing.BoxLayout.LINE_AXIS));

        sidePanel.setBackground(Theme.getBgColor());
        sidePanel.setMaximumSize(new java.awt.Dimension(250, 32767));
        sidePanel.setMinimumSize(new java.awt.Dimension(250, 65));
        sidePanel.setPreferredSize(new java.awt.Dimension(250, 646));
        sidePanel.setLayout(new java.awt.BorderLayout());

        welcomePanel.setBackground(Theme.getFgColor());
        welcomePanel.setMaximumSize(new java.awt.Dimension(32767, 100));
        welcomePanel.setPreferredSize(new java.awt.Dimension(250, 100));
        welcomePanel.setLayout(new java.awt.BorderLayout());

        welcomeInnerPanel.setBackground(Theme.getFgColor());
        welcomeInnerPanel.setLayout(new java.awt.GridLayout(2, 1));

        welcomeLabel.setBackground(new java.awt.Color(204, 204, 204));
        welcomeLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        welcomeLabel.setForeground(new java.awt.Color(204, 204, 204));
        welcomeLabel.setText("Welcome");
        welcomeInnerPanel.add(welcomeLabel);

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(204, 204, 204));
        usernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        usernameLabel.setText("Username");
        welcomeInnerPanel.add(usernameLabel);

        welcomePanel.add(welcomeInnerPanel, java.awt.BorderLayout.CENTER);
        welcomePanel.add(filler1, java.awt.BorderLayout.WEST);
        welcomePanel.add(filler2, java.awt.BorderLayout.EAST);
        welcomePanel.add(filler3, java.awt.BorderLayout.NORTH);
        welcomePanel.add(filler4, java.awt.BorderLayout.SOUTH);

        sidePanel.add(welcomePanel, java.awt.BorderLayout.NORTH);

        selectionsPanel.setBackground(Theme.getBgColor());
        selectionsPanel.setLayout(new javax.swing.BoxLayout(selectionsPanel, javax.swing.BoxLayout.PAGE_AXIS));
        sidePanel.add(selectionsPanel, java.awt.BorderLayout.CENTER);

        logoutBtn.setBackground(Theme.getFgColor());
        logoutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        logoutBtn.setMaximumSize(new java.awt.Dimension(32767, 50));
        logoutBtn.setPreferredSize(new java.awt.Dimension(250, 50));
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                logoutBtnMousePressed(evt);
            }
        });
        logoutBtn.setLayout(new java.awt.BorderLayout());

        selectionLabel4.setBackground(new java.awt.Color(204, 204, 204));
        selectionLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectionLabel4.setForeground(new java.awt.Color(204, 204, 204));
        selectionLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        selectionLabel4.setText("Logout");
        logoutBtn.add(selectionLabel4, java.awt.BorderLayout.CENTER);
        logoutBtn.add(filler21, java.awt.BorderLayout.WEST);
        logoutBtn.add(filler22, java.awt.BorderLayout.EAST);
        logoutBtn.add(filler23, java.awt.BorderLayout.NORTH);
        logoutBtn.add(filler24, java.awt.BorderLayout.SOUTH);

        sidePanel.add(logoutBtn, java.awt.BorderLayout.PAGE_END);

        bg.add(sidePanel);

        mainPanel.setLayout(new java.awt.BorderLayout());
        bg.add(mainPanel);

        getContentPane().add(bg, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Frame size and location">
    private int frmWidth = 1200;
    private int frmHeight = 650;
    
    private java.awt.Dimension getFrameSize(){
        return new java.awt.Dimension(frmWidth, frmHeight);
    }
    
    private java.awt.Point getStartupLocation(){
        java.awt.Toolkit kit = java.awt.Toolkit.getDefaultToolkit();
        int screenwidth=kit.getScreenSize().width;
        int screenheight=kit.getScreenSize().height;
        int frmwidth = frmWidth;
        int frmheight = frmHeight;
        java.awt.Point p = new java.awt.Point(screenwidth/2-frmwidth/2, screenheight/2-frmheight/2);
        return p;
    }
    // </editor-fold>
    
    private void btnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMouseEntered
        Theme.highlightBackground((JPanel)evt.getSource());
    }//GEN-LAST:event_btnMouseEntered

    private void btnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMouseExited
        Theme.resetBackground((JPanel)evt.getSource());
    }//GEN-LAST:event_btnMouseExited

    private void logoutBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMousePressed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logoutBtnMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler21;
    private javax.swing.Box.Filler filler22;
    private javax.swing.Box.Filler filler23;
    private javax.swing.Box.Filler filler24;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JPanel logoutBtn;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel selectionLabel4;
    private javax.swing.JPanel selectionsPanel;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JPanel welcomeInnerPanel;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JPanel welcomePanel;
    // End of variables declaration//GEN-END:variables
}
