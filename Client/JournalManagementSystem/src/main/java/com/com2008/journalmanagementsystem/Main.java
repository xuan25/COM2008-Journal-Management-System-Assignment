package com.com2008.journalmanagementsystem;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import com.com2008.journalmanagementsystem.frame.LoginFrame;
import com.com2008.journalmanagementsystem.util.database.Database;

public class Main {
    public static void main(final String args[]) {
        try {
            Database.connect("jdbc:mysql://stusql.dcs.shef.ac.uk/team018", "team018", "9ae70ba0");
        } catch (final SQLException e) {
            JOptionPane.showMessageDialog(null, "Can not connect to the database", "The software will now exit",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (final javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (final ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (final InstantiationException e) {
                    e.printStackTrace();
                } catch (final IllegalAccessException e) {
                    e.printStackTrace();
                } catch (final UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        
        new LoginFrame().setVisible(true);
    }
}