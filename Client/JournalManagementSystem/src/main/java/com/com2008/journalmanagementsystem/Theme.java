/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.com2008.journalmanagementsystem;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author Xuan
 */
public class Theme {
    private static Color bgColor = new Color(54, 33, 89);
    public static Color getBgColor(){
        return bgColor;
    }
    
    private static Color fgColor = new Color(64, 43, 100);
    public static Color getFgColor(){
        return fgColor;
    }
    
    private static Color hlColor = new Color(85, 65, 118);
    public static Color getHlColor(){
        return hlColor;
    }
    
    public static void highlightBackground(JPanel panel){
        panel.setBackground(hlColor);
    }
    
    public static void resetBackground(JPanel panel){
        panel.setBackground(fgColor);
    }
}
