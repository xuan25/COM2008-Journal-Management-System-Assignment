package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Editor implements IDataRow{
    
    private String email;
    private String hashedPassword;

    public Editor(){
//        email = null;
//        hashedPassword = null;
    }

    public Editor(String email, String hashedPassword){
        this.email = email;
        this.hashedPassword = hashedPassword;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}