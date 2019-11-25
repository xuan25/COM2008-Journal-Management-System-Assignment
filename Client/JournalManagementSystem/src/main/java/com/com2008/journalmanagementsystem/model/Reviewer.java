package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Reviewer implements IDataRow{
    
    private String email;
    private String hashedPassword;

    public Reviewer(){
        
    }

    public Reviewer(String email, String hashedPassword){
        this.email = email;
        this.hashedPassword = hashedPassword;
    }
}