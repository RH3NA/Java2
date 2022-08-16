package com.javafxdemo.controller;

import com.javafxdemo.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

//this controller controlls the Delete function and its view

public class DeleteController extends InheritedMethods implements Initializable, ReusableInterface {
    @FXML
    TextField barcodeInput;

    @FXML
    Label deleteItemConfirmationLabel;

    @FXML Label generalTextLabel;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generalTextLabel.setText(Session.getInstance().getCurrentUser().getFirstName() + ", enter the barcode of the item you wish to delete.");
        Session.getInstance().setCurrentScene("Delete");
    }

    public void setSceneDeleteItem() throws IOException {
        Scene sceneDeleteItem = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/delete-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneDeleteItem);
        stage.show();
    }

    public void onDeleteItemButtonClick(ActionEvent a) throws Exception {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();


        CallableStatement statement = conn.prepareCall("{CALL DeleteItem(?)}");  //makes a statement call ready to execute the procedure in database
        statement.setString(1, barcodeInput.getText());

        int rowsDeleted = statement.executeUpdate();

        System.out.println(rowsDeleted + " records deleted.\n"); //checking if the item was successfully deleted and setting the text based on it
        if (rowsDeleted >0) {
            deleteItemConfirmationLabel.setText(rowsDeleted + " record successfully deleted.");
        } else{
            deleteItemConfirmationLabel.setText("No record found. No record deleted.");
        }
    }

    public void onBackButtonClick(ActionEvent a) throws IOException {
        backMethod();
    }

    public void onExitButtonClick() {
        exit();
    }

    @Override
    public void backMethod() throws IOException {
        if (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == Boolean.TRUE) {
            Session.getInstance().getAdminController().setSceneAdmin();
        }
    }
}
