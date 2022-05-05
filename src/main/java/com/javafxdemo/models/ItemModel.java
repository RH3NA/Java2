package com.javafxdemo.models;

public class ItemModel {
    private int idItem;
    private short numberInStock;
    private String title;
    private String isbn;
    private short totalStock;
    private String publisher;


    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }




    public short getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(short numberInStock) {
        this.numberInStock = numberInStock;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public short getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(short totalStock) {
        this.totalStock = totalStock;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }



}
