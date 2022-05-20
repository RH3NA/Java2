package com.javafxdemo.models;

//this class is based on the Keyword table in the database, however we haven't worked with this one since we had to limit our project to the project requirements.
public class KeywordModel {
    private int idKeyword;
    private String keyword;


    public int getIdKeyword() {
        return idKeyword;
    }

    public void setIdKeyword(int idKeyword) {
        this.idKeyword = idKeyword;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


}
