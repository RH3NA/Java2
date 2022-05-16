package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Label idItemLable;
    @FXML
    TextField idItemInput;
    @FXML
    private Label inStockLable;
    @FXML
    TextField inStockInput;
    @FXML
    private Label titleLable;
    @FXML
    TextField titleInput;
    @FXML
    Button confirmCRUDButton;
    @FXML
    private Label isbnLable;
    @FXML
    TextField isbnInput;
    @FXML
    private Label totalInStockLable;
    @FXML
    TextField totalInStockInput;
    @FXML
    private Label publisherLable;
    @FXML
    TextField publisherInput;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private Button crudButton;
    public void onCrudButtonClick(ActionEvent a) throws IOException {
        Scene updateCrud = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/crud-view.fxml")));
        Stage stage = (Stage) crudButton.getScene().getWindow();
        stage.setScene(updateCrud);
        stage.show();
    }




    public static void setSceneAdmin() throws IOException {
        Scene sceneAdmin = new Scene(FXMLLoader.load(Objects.requireNonNull(LibraryApplication.class.getResource("fxml/adminStartpage-view.fxml"))));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneAdmin);
        stage.show();
    }



}


