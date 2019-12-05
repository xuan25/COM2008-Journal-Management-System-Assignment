package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Journal implements IDataRow{
    
    private String issn;
    private String journalName;
    private String cheifEmail;

    public Journal(){
        
    }

    public Journal(String issn, String journalName, String cheifEmail){
        this.issn = issn;
        this.journalName = journalName;
        this.cheifEmail = cheifEmail;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getCheifEmail() {
        return cheifEmail;
    }

    public void setCheifEmail(String cheifEmail) {
        this.cheifEmail = cheifEmail;
    }

    @Override
    public String toString() {
        return journalName;
    }
}