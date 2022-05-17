package com.javafxdemo.models;


import com.javafxdemo.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserModel {
    private int idUser;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;
    private int userType;
    private Boolean currentlyLoggedIn;


    public Boolean getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }

    public void setCurrentlyLoggedIn(Boolean currentlyLoggedIn) {
        this.currentlyLoggedIn = currentlyLoggedIn;
    }

    public static ArrayList<UserModel> users = new ArrayList<>();

    public UserModel(int idUser, String lastName, String firstName, String phoneNumber, String email, int userType, Boolean currentlyLoggedIn, Boolean hasTooManyLoans) { //constructor for our usermodel
        this.idUser = idUser;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
        this.currentlyLoggedIn = currentlyLoggedIn;
        this.hasTooManyLoans = hasTooManyLoans;
    }

    @Override
    public String toString() //overriding toString method so we get the values instead of the hashcodes from the arraylist prints
    {
        return "idUser = " + this.idUser +
                " lastName = " + this.lastName +
                " firstName = " + this.firstName +
                " phoneNumber = " + this.phoneNumber +
                " email = " + this.email +
                " userType = " + this.userType +
                " currentlyLoggedIn = " + this.currentlyLoggedIn +
                " hasTooManyLoans = " + this.hasTooManyLoans;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Boolean getHasTooManyLoans() {
        return hasTooManyLoans;
    }

    public void setHasTooManyLoans(Boolean hasTooManyLoans) {
        this.hasTooManyLoans = hasTooManyLoans;
    }

    private Boolean hasTooManyLoans;

    public static void getUsersDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist,
                                                          // the only issue rn is that i didnt set any limits so if you run this method twice,
                                                        // there will be duplicates.. easy to fix probs :)
        users.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From User";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            UserModel user = new UserModel(rst.getInt("idUser"), rst.getString("lastName"), rst.getString("firstName"), rst.getString("phoneNumber"), rst.getString("email"), rst.getInt("UserType_idUserType"), Boolean.FALSE, Boolean.FALSE);
            users.add(user);
        }

        }
    }


