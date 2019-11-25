package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Review implements IDataRow{
    
    private String email;
    private String issn;
    private String submissionID;
    private String summary;
    private Integer verdict;
    private Integer timestamp;

    public Review(){
        
    }

    public Review(String email, String issn, String submissionID, String summary, Verdict verdict, Integer timestamp){
        this.email = email;
        this.issn = issn;
        this.submissionID = submissionID;
        this.summary = summary;
        if(verdict != null)
            this.verdict = verdict.value();
        this.timestamp = timestamp;
    } 

    public enum Verdict {
        STRONG_ACCEPT(3),
        WEAK_ACCEPT(2),
        WEAK_REJECT(1),
        STRONG_REJECT(0),
        UNKNOW(-1);

        private int value = 0;

        private Verdict(int value) {
            this.value = value;
        }

        public static Verdict valueOf(int value) {
            switch (value) {
            case 3:
                return STRONG_ACCEPT;
            case 2:
                return WEAK_ACCEPT;
            case 1:
                return WEAK_REJECT;
            case 0:
                return STRONG_REJECT;
            default:
                return UNKNOW;
            }
        }
     
        public int value() {
            return this.value;
        }    
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Verdict getVerdict() {
        return Verdict.valueOf(verdict);
    }

    public void setVerdict(Verdict verdict) {
        this.verdict = verdict.value();
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}