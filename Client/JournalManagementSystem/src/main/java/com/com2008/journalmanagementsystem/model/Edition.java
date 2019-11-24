package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Edition implements IDataRow{
    
    private String issn;
    private int volume;

    public Edition(){
        
    }

    public Edition(String issn, int volume){
        this.issn = issn;
        this.volume = volume;
    }
}