package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Review implements IDataRow{
    
    private String email;
    private String issn;
    private String submissionID;
    private String summary;
    private int verdict;
    private int timestamp;

    public Review(){
        
    }

    public Review(String email, String issn, String submissionID, String summary, int verdict, int timestamp){
        this.email = email;
        this.issn = issn;
        this.submissionID = submissionID;
        this.summary = summary;
        this.verdict = verdict;
        this.timestamp = timestamp;
    }
}