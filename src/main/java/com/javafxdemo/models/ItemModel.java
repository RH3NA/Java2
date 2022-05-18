package com.javafxdemo.models;

import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;

public class ItemModel {

    private int idItem;

    private int numberInStock;
    private String title;
    private String isbn;
    private String firstName;
    private String lastName;
    private String publisher;
    private int totalStock;

    public static ArrayList<ItemModel> items = new ArrayList<>();

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public ItemModel(int idItem, int idBarcode, String title, String isbn, String publisher, int totalStock) {
        this.idItem = idItem;
        this.numberInStock = numberInStock;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.totalStock = totalStock;


    }

    @Override
    public String toString() //overriding toString method so we get the values instead of the hashcodes from the arraylist prints
    {
        return "idItem = " + this.idItem +

                " numberInStock = " + this.numberInStock +
                " title = " + this.title +
                " isbn = " + this.isbn +
                " publisher = " + this.publisher +
                " totalStock = " + this.totalStock;
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

    public static void getItemsDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist,
        // the only issue rn is that i didnt set any limits so if you run this method twice,
        // there will be duplicates.. easy to fix probs :)
        items.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Item";

        ResultSet rst;


        rst = stm.executeQuery(sql);

        while (rst.next()) {
            ItemModel item = new ItemModel(rst.getInt("idItem"),rst.getInt("numberInStock"), rst.getString("title"), rst.getString("isbn"), rst.getString("publisher"), rst.getInt("totalStock"));
            items.add(item);
        }

    }

    public static void addItem(int idItem, int numberInStock, String title, String isbn, int totalStock, String publisher) throws SQLException {
        getItemsDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        for (int i = 0; items.size() > i; i++) {
            if (idItem == items.get(i).getIdItem()) {
                Session.getInstance().setCurrentAdd(new ItemModel(idItem,numberInStock, title, isbn, publisher, totalStock));
                System.out.println("Current user information: " + Session.getInstance().getCurrentAdd());
                System.out.println(Session.getInstance().getCurrentAdd());

                String queryToItem = " insert into Item (idItem, numberInStock, title, isbn, publisher, totalstock)" + " values (?, ?, ? , ? , ?, ?)";



                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(queryToItem);
                preparedStmt.setInt(1, Session.getInstance().getCurrentAdd().idItem);
                preparedStmt.setInt(2, Session.getInstance().getCurrentAdd().numberInStock);
                preparedStmt.setInt(3, Integer.parseInt(Session.getInstance().getCurrentAdd().title));
                preparedStmt.setTimestamp(4, Timestamp.valueOf(Session.getInstance().getCurrentAdd().isbn));
                preparedStmt.setTimestamp(5, Timestamp.valueOf(Session.getInstance().getCurrentAdd().publisher));
                preparedStmt.setTimestamp(6, Timestamp.valueOf(String.valueOf(Session.getInstance().getCurrentAdd().totalStock)));


                preparedStmt.execute();






            }
        }
    }


    public static void insterBarcode(int idBarcode) throws SQLException {

        getItemsDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();



                String queryToInventory = "insert into Inventory(idBarcode)" + " values (?)";

                // create the mysql insert preparedstatement

                PreparedStatement preparedStmtInventory = conn.prepareStatement(queryToInventory);
                preparedStmtInventory.setInt(1,Integer.parseInt(String.valueOf(Session.getInstance().getCurrentAdd().idBarcode)));


                preparedStmtInventory.execute();



            }







}
