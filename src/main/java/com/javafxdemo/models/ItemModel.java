package com.javafxdemo.models;

public class ItemModel {
    private int idItem;
    private int numberInStock;
    private String title;
    private String isbn;
    private String publisher;

    public ItemModel(int idItem, int numberInStock, String title, String isbn, String publisher) {
        this.idItem = idItem;
        this.numberInStock = numberInStock;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
    }


    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }




    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }



}
