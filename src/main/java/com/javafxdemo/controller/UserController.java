package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserController {
    public static ArrayList<User> getUsers() throws SQLException {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From User";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        ArrayList<User> userList = new ArrayList<>();
        while (rst.next()) {
            User user = new User(rst.getInt("idUser"), rst.getString("lastName"), rst.getString("firstName"), rst.getString("phoneNumber"), rst.getString("email"), rst.getInt("UserType_idUserType"));
            userList.add(user);
        }
        for (User i : userList) {
            System.out.println(i);
        }
        return userList;
    }
}
