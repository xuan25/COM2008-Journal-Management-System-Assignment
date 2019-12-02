package com.com2008.journalmanagementsystem;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import com.com2008.journalmanagementsystem.frame.LoginFrame;
import com.com2008.journalmanagementsystem.util.database.Database;

public class Main {
    public static void main(String args[]) {

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setLoading(true);
        loginFrame.setVisible(true);

        try {
            Database.connect("jdbc:mysql://stusql.dcs.shef.ac.uk/team018", "team018", "9ae70ba0");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Can not connect to the database", "The software will now exit", JOptionPane.ERROR_MESSAGE);
            loginFrame.setVisible(false);
            loginFrame.dispose();
            System.exit(0);
            return;
        }

        loginFrame.setLoading(false);
    }
}