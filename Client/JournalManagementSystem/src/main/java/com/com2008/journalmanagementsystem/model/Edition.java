package com.com2008.journalmanagementsystem.model;

import com.com2008.journalmanagementsystem.util.database.IDataRow;

public class Edition implements IDataRow{
    
    private String issn;
    private Integer volume;
    private Integer edition;

    public Edition(){
        
    }

    public Edition(String issn, Integer volume, Integer edition){
        this.issn = issn;
        this.volume = volume;
        this.edition = edition;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
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

    @Override
    public String toString() {
        return "Vol." + volume + ", Ed." + edition;
    }
}