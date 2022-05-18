package com.javafxdemo.models;


import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;

public class ItemHasCreatorModel {
    private String firstName;
    private String lastName;

    public ItemHasCreatorModel(int firstName, int lastName) {

    }
    public static ArrayList<ItemHasCreatorModel> creators = new ArrayList<>();

    public ItemHasCreatorModel(String firstName, String lastName) {

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




    public static void getItemHasCreatorDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist,
        // the only issue rn is that i didnt set any limits so if you run this method twice,
        // there will be duplicates.. easy to fix probs :)
        creators.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Item_Has_Creator";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            ItemHasCreatorModel creator = new ItemHasCreatorModel(rst.getInt("firstName"), rst.getInt("lastName"));
            creators.add(creator);

        }
    }



    public static void insertAuthorFirstname(String firstName) throws SQLException {
        getItemHasCreatorDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        String queryFristname = " insert into Item_Has_Creator(firstName)" + " values (?)";

        PreparedStatement preparedStmt = conn.prepareStatement(queryFristname);
        preparedStmt.setString(1, Session.getInstance().getCurrentItemHasCreator().firstName);


        preparedStmt.executeUpdate();


    }

    public static void insertAuthorLastname(String lastName) throws SQLException {
        getItemHasCreatorDB();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();


        String queryLastname = " insert into Item_Has_Creator(lastName)" + " values (?)";

        PreparedStatement preparedStmt = conn.prepareStatement(queryLastname);
        preparedStmt.setString(1, Session.getInstance().getCurrentItemHasCreator().lastName);


        preparedStmt.execute();

    }


}
