package com.com2008.journalmanagementsystem.database;

public class Author implements IDataRow{
    private String UniversityAffiliation;
    private String Email;
    private String Surname;
    private String Forename;

    public Author(){
        
    }

    public Author(String university, String email, String surname, String forename){
        UniversityAffiliation = university;
        Email = email;
        Surname = surname;
        Forename = forename;
    }
}