package com.javafxdemo.models;


import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;
import com.javafxdemo.controller.AddController;
import com.javafxdemo.controller.AdminController;

import java.sql.*;
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
    private final Boolean available;
    public static ArrayList<InventoryModel> inventory = new ArrayList<>();

    public InventoryModel(int idBarcode, int items_idItems, int location_idLocation, String category, Boolean available) {
        this.idBarcode = idBarcode;
        this.items_idItems = items_idItems;
        this.location_idLocation = location_idLocation;
        this.category = category;
        this.available = available;
    }

    @Override
    public String toString() //overriding toString method so we get the values instead of the hashcodes from the arraylist prints
    {
        return "idBarcode = " + this.idBarcode +
                " items_idItems = " + this.items_idItems +
                " location_idLocation = " + this.location_idLocation +
                " category = " + this.category +
                " available = " + this.available;
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
        inventory.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Inventory";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            InventoryModel inventoryModel = new InventoryModel(rst.getInt("idBarcode"), rst.getInt("Items_idItems"), rst.getInt("Location_idLocation"), rst.getString("category"), checkAvailableBarcode(rst.getInt("idBarcode")));
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
                "'OR idBarcode NOT IN (Select Inventory_idBarcode From Loan) AND Items_idItems = '" + idItem +
                "'Limit 1");
        while (rs.next()) {
            availableBarcode = rs.getInt("idBarcode");
        }
        if (availableBarcode == 0) {
            System.out.println("No items available to loan");
        }
        System.out.println(availableBarcode);
        return availableBarcode;
    }

    public static Boolean checkAvailableBarcode (int idBarcode) throws SQLException {
        int checkBarcode = 0;
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stmt;
        ResultSet rs;
        stmt = conn.createStatement();
        rs = stmt.executeQuery("Select idBarcode\n" +
                "From Inventory\n" +
                "Where idBarcode = '" + idBarcode +
                "'AND idBarcode NOT IN (Select Inventory_idBarcode From Loan);");
        while (rs.next()) {
            checkBarcode = rs.getInt("idBarcode");
            }
        if (checkBarcode > 0) {
            return true;
        }
        if (checkBarcode == 0) {
            rs = stmt.executeQuery("Select Inventory_idBarcode\n" +
                    "From Loan\n" +
                    "left outer join Loanreturn ON Loan.idLoan = Loanreturn.Loan_idLoan\n" +
                    "Where returnDate is not null\n" +
                    "And Inventory_idBarcode = '" + idBarcode +
                    "'Order by expiryDate desc\n" +
                    "Limit 1;");

            while (rs.next()) {
                checkBarcode = rs.getInt("Inventory_idBarcode");
            }
            return checkBarcode > 0;
            //if (checkBarcode > 0) {
            //    return true;
            //}
        }
        return false;
    }


    public int getItems_idItems() {
        return items_idItems;
    }

    public void setItems_idItems(int items_idItems) {
        this.items_idItems = items_idItems;
    }

    public static String getTitleFromBarcode(int idBarcode) throws SQLException {
        ItemModel.getItemsDB();
        getInventoryDB();
        for (InventoryModel inventoryModel : inventory) {
            if (idBarcode == inventoryModel.idBarcode) {
                int idItem = inventoryModel.items_idItems;
                for (int y = 0; ItemModel.items.size() > y; y++) {
                    if (idItem == ItemModel.items.get(y).getIdItem()) {
                        return ItemModel.items.get(y).getTitle();
                    }
                }
            }
        }
        return "No title";
    }


    public static void insertCategory(String category) throws SQLException {

        getInventoryDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();


        String queryCategogry = " insert into Inventory(category)"+ " values (" + category + ");";

        PreparedStatement preparedStmt = conn.prepareStatement(queryCategogry);
        preparedStmt.setString(1,Session.getInstance().getcurrentInventory().category);


        preparedStmt.executeUpdate();



    }

    public static void insertBarcode(int idBarcode) throws SQLException {

        getInventoryDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        //Session?

        String queryToInventory = "insert into Inventory(idBarcode)" + " values (" + idBarcode + ");";

        // create the mysql insert preparedstatement

        PreparedStatement preparedStmtInventory = conn.prepareStatement(queryToInventory);
        preparedStmtInventory.setInt(1,Session.getInstance().getcurrentInventory().idBarcode);


        preparedStmtInventory.executeUpdate();



    }





}
