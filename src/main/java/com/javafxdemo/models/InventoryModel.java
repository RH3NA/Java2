package com.javafxdemo.models;


import com.javafxdemo.Context;
import com.javafxdemo.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InventoryModel {
    public int getLocation_idLocation() {
        return location_idLocation;
    }

    public void setLocation_idLocation(int location_idLocation) {
        this.location_idLocation = location_idLocation;
    }

    private int location_idLocation;
    private int items_idItems;
    private int idBarcode;
    private String category;
    public static ArrayList<InventoryModel> inventory = new ArrayList<>();

    public InventoryModel(int idBarcode, int items_idItems, int location_idLocation, String category) {
        this.idBarcode = idBarcode;
        this.items_idItems = items_idItems;
        this.location_idLocation = location_idLocation;
        this.category = category;

    }


    public int getIdBarcode() {
        return idBarcode;
    }

    public void setIdBarcode(int idBarcode) {
        this.idBarcode = idBarcode;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static void getInventoryDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist,
        // the only issue rn is that i didnt set any limits so if you run this method twice,
        // there will be duplicates.. easy to fix probs :)
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Inventory";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            InventoryModel inventoryModel = new InventoryModel(rst.getInt("idBarcode"), rst.getInt("Items_idItems"), rst.getInt("Location_idLocation"), rst.getString("category"));
            inventory.add(inventoryModel);
        }
    }

    public static int availableBarcode(int idItem) throws SQLException {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stmt;
        ResultSet rs;
        int availableBarcode = 0;
        stmt = conn.createStatement();

        rs = stmt.executeQuery("Select idBarcode\n" +
                "From inventory\n" +
                "Where idBarcode NOT IN (Select Inventory_idBarcode From Loan Where idLoan NOT IN (Select Loan_idLoan From Loanreturn)) AND Items_idItems = '" + idItem +
                "'OR idBarcode NOT IN (Select Inventory_idBarcode From Loan) AND Items_idItems = '"+ idItem +
                "'Limit 1");
        while (rs.next())
        {
            availableBarcode = rs.getInt("idBarcode");
        }
        if (availableBarcode == 0) {
            System.out.println("No items available to loan");
        };
        return availableBarcode;
    }

    public int getItems_idItems() {
        return items_idItems;
    }

    public void setItems_idItems(int items_idItems) {
        this.items_idItems = items_idItems;
    }
}
