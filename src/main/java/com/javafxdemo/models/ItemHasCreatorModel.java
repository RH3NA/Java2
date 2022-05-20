package com.javafxdemo.models;


import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;

//this class is the model of everything in the ItemHasCreator table in the database
public class ItemHasCreatorModel {
    public int getItem_IdItem() {
        return item_IdItem;
    }

    public void setItem_IdItem(int item_IdItem) {
        this.item_IdItem = item_IdItem;
    }

    private int item_IdItem;
    private String firstName;
    private String lastName;

    public ItemHasCreatorModel(int firstName, int lastName) {

    }
    public static ArrayList<ItemHasCreatorModel> creators = new ArrayList<>();

    public ItemHasCreatorModel(int Item_idItem, String firstName, String lastName) { //constructor
        this.item_IdItem = Item_idItem;
        this.firstName = firstName;
        this.lastName = lastName;

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }




    public static void getItemHasCreatorDB() throws SQLException { //added a method to get and store the authors from the DB in a static arraylist
        creators.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Item_Has_Creator";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            ItemHasCreatorModel creator = new ItemHasCreatorModel(rst.getInt("Item_idItem"), rst.getString("firstName"), rst.getString("lastName"));
            creators.add(creator);

        }
    }



    public static void insertAuthorFirstname(String firstName) throws SQLException { //inserts the author first name
        getItemHasCreatorDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();


        //Session.getInstance().getCurrentItemHasCreator(new ItemHasCreatorModel(firstName));
        //System.out.println("Current user information: " + Session.getInstance().getCurrentAdd());
        //System.out.println(Session.getInstance().getCurrentAdd());

        String queryFristname = " insert into Item_Has_Creator(firstName)" + " values (" + firstName + ");";

        PreparedStatement preparedStmt = conn.prepareStatement(queryFristname);
        preparedStmt.setString(1, Session.getInstance().getCurrentItemHasCreator().firstName);


        preparedStmt.executeUpdate();


    }

    public static void insertAuthorLastname(String lastName) throws SQLException { //inserts the authors last name
        getItemHasCreatorDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();


        String queryLastname = " insert into Item_Has_Creator(lastName)" + " values (" + lastName + ");";

        PreparedStatement preparedStmt = conn.prepareStatement(queryLastname);
        preparedStmt.setString(1, Session.getInstance().getCurrentItemHasCreator().lastName);


        preparedStmt.executeUpdate();

    }
    public static void insertItemHasCreator(int item_idItem, String firstName, String lastName) throws SQLException { //inserts a new row in the table in the database
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        String queryToItem = " insert into Item_has_Creator (item_idItem, firstName, lastName)" + " values (?, ?, ?)";


        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(queryToItem);
        preparedStmt.setInt(1, item_idItem);
        preparedStmt.setString(2, firstName);
        preparedStmt.setString(3, lastName);
        preparedStmt.executeUpdate();


    }


}
