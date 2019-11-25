package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class SubmissionAuthor implements IDataRow{
    
    private String issn;
    private String submissionID;
    private String email;

    public SubmissionAuthor(){
        
    }

    public SubmissionAuthor(String issn, String submissionID, String email){
        this.issn = issn;
        this.submissionID = submissionID;
        this.email = email;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(String submissionID) {
        this.submissionID = submissionID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}