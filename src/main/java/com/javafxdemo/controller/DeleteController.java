package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.annotation.Inherited;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DeleteController extends ReusableButtonController implements Initializable {
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

        System.out.println(rowsDeleted + " records deleted.\n");
        if (rowsDeleted >0) {
            deleteItemConfirmationLabel.setText(rowsDeleted + " record successfully deleted.");
        } else{
            deleteItemConfirmationLabel.setText("No record found. No record deleted.");
        }


        /* String sqlDelete = "DELETE FROM inventory where idBarcode=?";

        PreparedStatement statement;
        statement = conn.prepareStatement(sqlDelete);
        statement.setString(1, barcodeInput.getText());
        System.out.println("The SQL statement is: " + sqlDelete + "\n");  // Echo for debugging

        int countDeleted = statement.executeUpdate(sqlDelete);
        System.out.println(countDeleted + " records deleted.\n"); */

    }

    public void onBackButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().getAdminController().backMethod("Admin");
    }

    public void onExitButtonClick() {
        exit();
    }
}
