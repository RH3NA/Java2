package com.javafxdemo.models;


//this class is based on the itemkeyword table in the database, however we haven't worked with this one since we had to limit our project to the project requirements.
public class ItemKeywordModel {
    private int idItemKeyword;
    private int itemIdItem;


    public int getIdItemKeyword() {
        return idItemKeyword;
    }

    public void setIdItemKeyword(int idItemKeyword) {
        this.idItemKeyword = idItemKeyword;
    }


    public int getItemIdItem() {
        return itemIdItem;
    }

    public void setItemIdItem(int itemIdItem) {
        this.itemIdItem = itemIdItem;
    }

}
