package com.com2008.journalmanagementsystem.util.database;

// A demo class of IDataRow
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
}