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
    private Integer status;

    public Submission(){
        
    }

    public Submission(String issn, String submissionID, String title, String mainAuthor, String coAuthor, String contentAbstract, String draftID, String finalID, Integer status){
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainAuthor() {
        return mainAuthor;
    }

    public void setMainAuthor(String mainAuthor) {
        this.mainAuthor = mainAuthor;
    }

    public String getCoAuthor() {
        return coAuthor;
    }

    public void setCoAuthor(String coAuthor) {
        this.coAuthor = coAuthor;
    }

    public String getContentAbstract() {
        return contentAbstract;
    }

    public void setContentAbstract(String contentAbstract) {
        this.contentAbstract = contentAbstract;
    }

    public String getDraftID() {
        return draftID;
    }

    public void setDraftID(String draftID) {
        this.draftID = draftID;
    }

    public String getFinalID() {
        return finalID;
    }

    public void setFinalID(String finalID) {
        this.finalID = finalID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title;
    }

    
}