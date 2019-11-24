package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Journal implements IDataRow{
    
    private String issn;
    private String journalName;
    private String cheifEmail;

    public Journal(){
        
    }

    public Journal(String issn, String journalName, String cheifEmail, String surname, String university){
        this.issn = issn;
        this.journalName = journalName;
        this.cheifEmail = cheifEmail;
    }
}