package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Submission implements IDataRow{
    
    private String issn;
    private String submissionID;
    private String title;
    private String mainAuthor;
    private String corrAuthor;
    private String contentAbstract;
    private Integer status;

    public Submission(){
        
    }

    public Submission(String issn, String submissionID, String title, String mainAuthor, String corrAuthor, String contentAbstract, Status status){
        this.issn = issn;
        this.submissionID = submissionID;
        this.title = title;
        this.mainAuthor = mainAuthor;
        this.corrAuthor = corrAuthor;
        this.contentAbstract = contentAbstract;
        if(status != null)
            this.status = status.value();
    }

    public enum Status {
        SUBMITTED(0),
        REVIEWED(1),
        RESPONSED(2),
        VERDICTED(3),
        ACCEPTED(4),
        REJECTED(5),
        UNKNOW(-1);

        private int value = 0;

        private Status(int value) {
            this.value = value;
        }

        public static Status valueOf(int value) {
            switch (value) {
            case 0:
                return SUBMITTED;
            case 1:
                return REVIEWED;
            case 2:
                return RESPONSED;
            case 3:
                return VERDICTED;
            case 4:
                return ACCEPTED;
            case 5:
                return REJECTED;
            default:
                return UNKNOW;
            }
        }
     
        public int value() {
            return this.value;
        }    
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

    public String getCorrAuthor() {
        return corrAuthor;
    }

    public void setCorrAuthor(String coAuthor) {
        this.corrAuthor = coAuthor;
    }

    public String getContentAbstract() {
        return contentAbstract;
    }

    public void setContentAbstract(String contentAbstract) {
        this.contentAbstract = contentAbstract;
    }

    public Status getStatus() {
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status.value();
    }

    @Override
    public String toString() {
        return title;
    }

}