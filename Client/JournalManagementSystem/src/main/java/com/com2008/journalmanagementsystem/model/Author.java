package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Author implements IDataRow{
    
    private String email;
    private String hashedPassword;

    public Author(){
        
    }

    public Author(String email, String hashedPassword){
        this.email = email;
        this.hashedPassword = hashedPassword;
    }
}