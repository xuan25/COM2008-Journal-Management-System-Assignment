package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Article implements IDataRow{
    
    private String issn;
    private String submissionID;
    private Integer volume;
    private Integer edition;

    public Article(){
        
    }

    public Article(String issn, String submissionID, Integer volume, Integer edition){
        this.issn = issn;
        this.submissionID = submissionID;
        this.volume = volume;
        this.edition = edition;
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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }
}