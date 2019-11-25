package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Account implements IDataRow{
    
    private String email;
    private String title;
    private String forename;
    private String surname;
    private String university;

    public Account(){
        
    }

    public Account(String email, String title, String forename, String surname, String university){
        this.email = email;
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.university = university;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return title + " " + forename + " " + surname + " <" + email + "> from " + university;
    }
}