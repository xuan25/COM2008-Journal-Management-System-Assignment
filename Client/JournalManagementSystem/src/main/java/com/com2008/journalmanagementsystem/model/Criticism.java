package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Criticism implements IDataRow{
    
    private String email;
    private String issn;
    private String submissionID;
    private int num;
    private String content;

    public Criticism(){
        
    }

    public Criticism(String email, String issn, String submissionID, int num, String content){
        this.email = email;
        this.issn = issn;
        this.submissionID = submissionID;
        this.num = num;
        this.content = content;
    }
}