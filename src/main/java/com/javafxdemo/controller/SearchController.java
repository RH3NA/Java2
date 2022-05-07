package com.javafxdemo.controller;

import com.javafxdemo.Context;
import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class  SearchController {

    @FXML
    public TextField searchTextInputField;
    public Button searchForResultButton;
    @FXML
    private Label searchResultsArea;


    public void onSearchForResultButton(ActionEvent a) throws Exception {  // throwing out exceptions so the system doesn't crash & also ensure connection and statement closes
        DBConnection connectNow = new DBConnection();       //This initiates a new connection instance of 'conn' using the DBConnection Class
        Connection conn = connectNow.getConnection(); // THis opens a 'conn' connection using the DBConnection

        CallableStatement statement = conn.prepareCall("{CALL SearchByIsbn(?)}");  //makes a statement call ready to execute the procedure in database

        statement.setString(1, searchTextInputField.getText()); // gets input text from JavaFX 'search bar'
        boolean hadResults = statement.execute(); // executes sql query

        while (hadResults) {
            ResultSet resultSet = statement.getResultSet(); // will run the query until all results collected

            while (resultSet.next()) {  //outputs the query result back to the search results area (label) on search page
                searchResultsArea.setText("ISBN\t\t\t\t\tTitle\t\t\t\t\tPublisher\t\t\t Item ID\t\tNo. in Stock\n" +
                        resultSet.getString("isbn") + "   " +
                        resultSet.getString("title") + "\t\t" +
                        resultSet.getString("publisher") + "\t\t" +
                        resultSet.getString("idItem") + "\t\t\t\t" +
                        resultSet.getString("numberInStock"));
            }
            hadResults = statement.getMoreResults(); // will loop until no more results available
        }

        statement.close(); // closes query

    }
    @FXML
    private Button searchResultsLoanButton;
    public void onSearchResultsLoanButton(ActionEvent a) throws IOException {
        UserModel currentUser = Context.getInstance().getCurrentUser();
        if (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE) {
            Scene sceneLoan = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/loan-view.fxml")));
            Stage stage = (Stage) searchResultsLoanButton.getScene().getWindow();
            stage.setScene(sceneLoan);
            stage.show();
        }
            else {
                System.out.println("Something went wrong. Try logging in.");
            }


    }
}



        /*DBConnection connectNow = new DBConnection();       //This initiates a new connection instance of 'conn' using the DBConnection Class
        Connection conn = connectNow.getConnection(); // THis opens a 'conn' connection using the DBConnection

        String sqlInsert = "insert into Keyword (keyword) values ('" + searchTextInputField.getText() + "')";
       //String sqlProcedureSearchISBN = "{CALL SearchByIsbn(?)}";
        try {
            Statement statement = conn.createStatement();  // Allocate a 'Statement' object in the connection instance 'conn'

                                                                // INSERT (create) a partial record
                                                                // add a new  string (PARTIAL record, ie not all fields - autofields should self-fill -  into database (ie not all fields) via the searchTextInputField and searchForResultButton (Create/insert)
                                                                // sets textInput parameters (via constructor) to match database (sql) input syntax
            System.out.println("The SQL statement is: " + sqlInsert + "\n");  //This is an echo test for debugging
            int countInserted = statement.executeUpdate(sqlInsert);  // This calculates how many rows have been affected.
            System.out.println(countInserted + " records inserted.\n"); // This OUTPUTS " x records inserted"

                                                                *//* Other code also used by some:
                                                                String sql= "insert into user values('"+searchTextInputField.getText()+"')"; // INSTEAD OF INSERTING INTO TEXTAREA LIKE THIS ‹  searchResultsArea.setText(searchTextInputField.getText());  ›
                                                                Statement statement = conn.createStatement();
                                                                statement.executeUpdate(sql);  *//*

                                                                // Now this will SELECT(find) and show the new data from database into the search-fxml's searchResultsArea TextArea (Read/select))
            String sql = "select * from user; ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                searchTextInputField.setText(resultSet.getString(1));
            }

        } catch (SQLException ex) {
             ex.printStackTrace();
        }*/
