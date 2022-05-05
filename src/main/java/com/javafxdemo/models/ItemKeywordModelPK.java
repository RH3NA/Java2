package com.javafxdemo.models;

import java.io.Serializable;

public class ItemKeywordModelPK implements Serializable {
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
