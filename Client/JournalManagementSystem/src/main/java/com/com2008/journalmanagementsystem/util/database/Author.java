package com.com2008.journalmanagementsystem.util.database;

// A demo class of IDataRow
public class Author implements IDataRow{
    private String authorEmail;
    private String forename;
    private String surname;
    private String university;

    public Author(){
        
    }

    public Author(String authorEmail, String forename, String surname, String university){
        this.authorEmail = authorEmail;
        this.forename = forename;
        this.surname = surname;
        this.university = university;
    }
}