package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Article implements IDataRow{
    
    private String issn;
    private String submissionID;
    private int volume;
    private int edition;

    public Article(){
        
    }

    public Article(String issn, String submissionID, int volume, int edition){
        this.issn = issn;
        this.submissionID = submissionID;
        this.volume = volume;
        this.edition = edition;
    }
}