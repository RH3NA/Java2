package com.javafxdemo.models;

import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;

public class ItemModel {
    private final String category;
    private int idItem;
    private final int idBarcode;
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

    public ItemModel(int idItem, int idBarcode, int numberInStock, String title, String isbn, String publisher, int totalStock, String firstName,String lastName, String category) {
        this.idItem = idItem;
        this.idBarcode = idBarcode;
        this.numberInStock = numberInStock;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.totalStock = totalStock;
        this.category = category;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() //overriding toString method so we get the values instead of the hashcodes from the arraylist prints
    {
        return "idItem = " + this.idItem +
                "idBarcode = " + this.idBarcode +
                " numberInStock = " + this.numberInStock +
                " title = " + this.title +
                " isbn = " + this.isbn +
                " publisher = " + this.publisher +
                " totalStock = " + this.totalStock;
    }


    public int getIdItem() {
        return idItem;
    }
    public int getIdBarcode() {
        return idBarcode;
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
        String sqlin = "Select idBarcode From Inventory";
        String sqlauthorlastname = "Select lastName From Item_Has_Creator";
        String sqlauthorfirstname = "SELECT firstName from Item_Has_Creator";
        ResultSet rst;
        ResultSet rstin;
        ResultSet rstitemhascreatorfirst;
        ResultSet rstitemhascreatorlast;
        rst = stm.executeQuery(sql);
        rstin = stm.executeQuery(sqlin);
        rstitemhascreatorfirst = stm.executeQuery(sqlauthorfirstname);
        rstitemhascreatorlast = stm.executeQuery(sqlauthorlastname);

        while (rst.next()) {
            ItemModel item = new ItemModel(rst.getInt("idItem"), rstin.getInt("idBarcode"),rst.getInt("numberInStock"), rst.getString("title"), rst.getString("isbn"), rst.getString("publisher"), rst.getInt("totalStock"),rstitemhascreatorfirst.getString("firstName"), rstitemhascreatorlast.getString("lastName"),rstin.getString("category"));
            items.add(item);
        }

    }

    public static void addItem(int idItem, int idBarcode, int numberInStock, String title, String isbn, int totalStock, String publisher, String firstName,String lastName,String category) throws SQLException {
        getItemsDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        for (int i = 0; items.size() > i; i++) {
            if (idItem == items.get(i).getIdItem()) {
                Session.getInstance().setCurrentAdd(new ItemModel(idItem, idBarcode,numberInStock, title, isbn, publisher, totalStock,firstName, lastName, category));
                System.out.println("Current user information: " + Session.getInstance().getCurrentAdd());
                System.out.println(Session.getInstance().getCurrentAdd());

                String queryToItem = " insert into Item (idItem, numberInStock, title, isbn, publisher, totalstock)" + " values (?, ?, ? , ? , ?, ?)";
                String queryToInventory = " insert into Inventory(idBarcode)";
                String queryFristname = " insert into Item_Has_Creator(firstName)";
                String queryLastname = " insert into Item_Has_Creator(lastName)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(queryToItem);
                preparedStmt.setInt(1, Session.getInstance().getCurrentAdd().idItem);
                preparedStmt.setInt(2, Session.getInstance().getCurrentAdd().numberInStock);
                preparedStmt.setInt(3, Integer.parseInt(Session.getInstance().getCurrentAdd().title));
                preparedStmt.setTimestamp(4, Timestamp.valueOf(Session.getInstance().getCurrentAdd().isbn));
                preparedStmt.setTimestamp(5, Timestamp.valueOf(Session.getInstance().getCurrentAdd().publisher));
                preparedStmt.setTimestamp(6, Timestamp.valueOf(String.valueOf(Session.getInstance().getCurrentAdd().totalStock)));

                PreparedStatement preparedStmtInventory = conn.prepareStatement(queryToInventory);
                preparedStmtInventory.setInt(7,Integer.parseInt(String.valueOf(Session.getInstance().getCurrentAdd().idBarcode)));


                PreparedStatement preparedStmtAuthorFirstName = conn.prepareStatement(queryFristname);
                preparedStmtAuthorFirstName.setString(8, Session.getInstance().getCurrentAdd().firstName);



                PreparedStatement preparedStmtAuthorLastName = conn.prepareStatement(queryLastname);
                preparedStmtAuthorLastName.setString(9, Session.getInstance().getCurrentAdd().lastName);


                preparedStmt.execute();
                preparedStmtInventory.execute();
                preparedStmtAuthorFirstName.execute();
                preparedStmtAuthorLastName.execute();




            }
        }
    }



}
