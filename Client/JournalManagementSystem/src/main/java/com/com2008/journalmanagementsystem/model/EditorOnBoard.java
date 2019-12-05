package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class EditorOnBoard implements IDataRow{
    
    private String issn;
    private String email;

    public EditorOnBoard(){
        
    }

    public EditorOnBoard(String issn, String email){
        this.issn = issn;
        this.email = email;
    }
    
    public String getIssn(){
        return issn;
    }
    public String getEmail(){
        return email;
    }
}