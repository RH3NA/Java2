package com.javafxdemo;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public Connection conn;             // 1. declaring DBConnection class's Connection class variable 'conn' to hold the new connection

    public Connection getConnection() {

            String dbName = "D0005N";    // 2. declaring the database name, username and login variables and arguments to be passed to the connection
            String userName = "root";
            String password = "MySQ1S3rv3rS$S";

            try {                        // 3. checking, catching & reporting any crashes/exceptions instead of crashing whole programme then continues with remaining code
                Class.forName("com.mysql.cj.jdbc.Driver");   // 4. Nominating/checking for JDBC Driver
                conn = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, userName, password); // 5. Makes a new database connection object 'conn'
            } catch (Exception e) {
                e.printStackTrace();
            }

            return conn;                  // 6. output is a ready database connection (variable) 'conn'
    }

}
