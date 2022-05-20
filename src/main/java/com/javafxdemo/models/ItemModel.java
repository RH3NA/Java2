package com.javafxdemo.models;

import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

// this class is based on the Item table in the database
public class ItemModel {

    private int idItem;

    private int numberInStock;
    private String title;
    private String isbn;
    private String publisher;
    private int totalStock;

    public static ArrayList<ItemModel> items = new ArrayList<>();

    public int getTotalStock() {
        return totalStock;
    }
    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public ItemModel(int idItem, int numberInStock, String title, String isbn, int totalStock, String publisher) { //constructor
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
                " totalStock = " + this.totalStock +
                " publisher = " + this.publisher;
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

    public static void getItemsDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist
        items.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Item";

        ResultSet rst;
        rst = stm.executeQuery(sql);

        while (rst.next()) {
            ItemModel item = new ItemModel(rst.getInt("idItem"), rst.getInt("numberInStock"), rst.getString("title"), rst.getString("isbn"), rst.getInt("totalStock"), rst.getString("publisher"));
            items.add(item);
        }

    }

    public static void insertItem(int idItem, int numberInStock, String title, String isbn, int totalStock, String publisher) throws SQLException { //inserts an item
        getItemsDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

                Session.getInstance().setCurrentAdd(new ItemModel(idItem,numberInStock, title, isbn, totalStock, publisher));

                System.out.println("Current add information: " + Session.getInstance().getCurrentAdd());
                System.out.println(Session.getInstance().getCurrentAdd());

                String queryToItem = " insert into Item (idItem, numberInStock, title, isbn, totalstock, publisher)" + " values (?, ?, ? , ? , ?, ?)";



                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(queryToItem);
                preparedStmt.setInt(1, Session.getInstance().getCurrentAdd().idItem);
                preparedStmt.setInt(2, Session.getInstance().getCurrentAdd().numberInStock);
                preparedStmt.setString(3,Session.getInstance().getCurrentAdd().title);
                preparedStmt.setString(4,Session.getInstance().getCurrentAdd().isbn);
                preparedStmt.setInt(5,Session.getInstance().getCurrentAdd().totalStock);
                preparedStmt.setString(6,Session.getInstance().getCurrentAdd().publisher);

                preparedStmt.executeUpdate();




            }


    public static Boolean isbnExists(String ISBN) { //checks if the ISBN exists in the database
        for (ItemModel item : items) {
            if (!item.getIsbn().equalsIgnoreCase(ISBN)) {
                return false;
            }
        }
        return true;
    }

    public static void updateItem(int idItem, String attribute, String value) throws SQLException { //updates an attribute value in any table based on the input parameters
        if(isNumeric(value) == Boolean.FALSE) { //checks if it's a string or an int, if it's not an int it adds the quotation marks in order to work as a SQL query
            value = '"' + value + '"';
        }
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();

        //builds the query based on what the input parameters are
        String query = "UPDATE item\n" +
                "SET " + attribute + " = " + value + "\n" +
                "WHERE idItem = " + idItem;
        stm.executeUpdate(query);
        System.out.println(query); //prints the query in the console to check for any SQL syntax error
    }
    private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?"); //regex pattern

    public static boolean isNumeric(String strNum) { //method to check if the String contains numbers or not
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}

