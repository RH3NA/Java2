package com.example.javafxdemo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchController {

    @FXML public TextField searchTextInputField;
    @FXML private TextArea searchResultsArea;

    public void onSearchForResultButton(ActionEvent a) throws SQLException {  // throwing out exceptions so the system doesn't crash & also ensure connection and statement closes

        DBConnection connectNow = new DBConnection();       //This initiates a new connection instance of 'conn' using the DBConnection Class
        Connection conn = connectNow.getConnection(); // THis opens a 'conn' connection using the DBConnection

        String sqlInsert = "insert into Keyword (keyword) values ('" + searchTextInputField.getText() + "')";

        try {
            Statement statement = conn.createStatement();  // Allocate a 'Statement' object in the connection instance 'conn'

                                                                // INSERT (create) a partial record
                                                                // add a new  string (PARTIAL record, ie not all fields - autofields should self-fill -  into database (ie not all fields) via the searchTextInputField and searchForResultButton (Create/insert)
                                                                // sets textInput parameters (via constructor) to match database (sql) input syntax
            System.out.println("The SQL statement is: " + sqlInsert + "\n");  //This is an echo test for debugging
            int countInserted = statement.executeUpdate(sqlInsert);  // This calculates how many rows have been affected.
            System.out.println(countInserted + " records inserted.\n"); // This OUTPUTS " x records inserted"

                                                                /* Other code also used by some:
                                                                String sql= "insert into user values('"+searchTextInputField.getText()+"')"; // INSTEAD OF INSERTING INTO TEXTAREA LIKE THIS ‹  searchResultsArea.setText(searchTextInputField.getText());  ›
                                                                Statement statement = conn.createStatement();
                                                                statement.executeUpdate(sql);  */

                                                                // Now this will SELECT(find) and show the new data from database into the search-view's searchResultsArea TextArea (Read/select))
            String sql = "select * from user; ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                searchTextInputField.setText(resultSet.getString(1));
            }

        } catch (SQLException ex) {
             ex.printStackTrace();
        }
    }
}
