package com.javafxdemo.models;


import com.javafxdemo.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LocationModel {
    private int idLocation;
    private String library;
    private int shelf;
    private int number;
    private String letter;


    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }


    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}

    /*public static void insertLocation(int idLocation, int items_idItems, int location_idLocation, String category) throws SQLException {
        inventory.clear();
        getInventoryDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        String queryToItem = " insert into Inventory (idBarcode, Items_idItems, Location_idLocation, category)" + " values (?, ?, ? , ?)";


        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(queryToItem);
        preparedStmt.setInt(1, idBarcode);
        preparedStmt.setInt(2, items_idItems);
        preparedStmt.setInt(3, location_idLocation);
        preparedStmt.setString(4, category);
        preparedStmt.executeUpdate();
}*/
