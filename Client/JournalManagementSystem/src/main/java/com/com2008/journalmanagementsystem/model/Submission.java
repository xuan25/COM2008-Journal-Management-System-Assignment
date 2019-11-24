package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Submission implements IDataRow{
    
    private String issn;
    private String submissionID;
    private String title;
    private String mainAuthor;
    private String coAuthor;
    private String contentAbstract;
    private String draftID;
    private String finalID;
    private int status;

    public Submission(){
        
    }

    public Submission(String issn, String submissionID, String title, String mainAuthor, String coAuthor, String contentAbstract, String draftID, String finalID, int status){
        this.issn = issn;
        this.submissionID = submissionID;
        this.title = title;
        this.mainAuthor = mainAuthor;
        this.coAuthor = coAuthor;
        this.contentAbstract = contentAbstract;
        this.draftID = draftID;
        this.finalID = finalID;
        this.status = status;
    }
}