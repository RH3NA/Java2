package com.javafxdemo.models;

import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;

//this class is the Inventory Model and stores the database objects
public class InventoryModel extends DBConnection {
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
    public static ArrayList<InventoryModel> inventory = new ArrayList<>(); //list that stores all the objects from the database in the application

    public InventoryModel(int idBarcode, int items_idItems, int location_idLocation, String category, Boolean available) { //constructor
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

    public int setIdBarcode(int idBarcode) {
        this.idBarcode = idBarcode;
        return idBarcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static void getInventoryDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist
        inventory.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Inventory";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) { //creates a new Inventory Model object for each row in the database
            InventoryModel inventoryModel = new InventoryModel(rst.getInt("idBarcode"), rst.getInt("Items_idItems"), rst.getInt("Location_idLocation"), rst.getString("category"), checkAvailableBarcode(rst.getInt("idBarcode")));
            inventory.add(inventoryModel);
        }
    }

    public static int availableBarcode(int idItem) throws SQLException { //checks if there's an available barcode for the item you're searching for
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stmt;
        ResultSet rs;
        int availableBarcode = 0; //initialising the value to be 0 for easy error handling
        stmt = conn.createStatement();
        rs = stmt.executeQuery("Select idBarcode\n" + //a nested query to check for barcodes with a limit on 1 so we only get one value to return
                "From inventory\n" +
                "Where idBarcode NOT IN (Select Inventory_idBarcode From Loan Where idLoan NOT IN (Select Loan_idLoan From Loanreturn)) AND Items_idItems = '" + idItem +
                "'OR idBarcode NOT IN (Select Inventory_idBarcode From Loan) AND Items_idItems = '" + idItem +
                "'Limit 1");
        while (rs.next()) { //error handling
            availableBarcode = rs.getInt("idBarcode");
        }
        if (availableBarcode == 0) {
            System.out.println("No items available to loan");
        }
        System.out.println(availableBarcode);
        return availableBarcode; //returns the first available barcode
    }

    public static Boolean checkAvailableBarcode(int idBarcode) throws SQLException { //another method to check if the barcode is available, which first checks if it's ever been present in a loan, and then checks if it's been in loan and loanreturn and ready to be loaned again.
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
            return true; //breaks here if the barcode's never been present in a loan
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
        }
        return false;
    }

    public int getItems_idItems() {
        return items_idItems;
    }

    public void setItems_idItems(int items_idItems) {
        this.items_idItems = items_idItems;
    }

    public static String getTitleFromBarcode(int idBarcode) throws SQLException { //gets the title from the barcode value to find a match
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

    public static void insertCategory(String category) throws SQLException { //inserts a category

        getInventoryDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        String queryCategogry = " insert into Inventory(category)" + " values (" + category + ");";

        PreparedStatement preparedStmt = conn.prepareStatement(queryCategogry);
        preparedStmt.setString(1, Session.getInstance().getcurrentInventory().category);

        preparedStmt.executeUpdate();
    }

    public static void insertBarcode(int idBarcode) throws SQLException { //inserts a barcode

        getInventoryDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        String queryToInventory = "insert into Inventory(idBarcode)" + " values (" + idBarcode + ");";

        // create the mysql insert preparedstatement

        PreparedStatement preparedStmtInventory = conn.prepareStatement(queryToInventory);
        preparedStmtInventory.setInt(1, Session.getInstance().getcurrentInventory().idBarcode);

        preparedStmtInventory.executeUpdate();
    }

    public static void insertInventory(int idBarcode, int items_idItems, int location_idLocation, String category) throws SQLException { //insert method for a new item
        inventory.clear(); //refreshes the stored list first to check for any updates
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
    }
}
