package com.javafxdemo.controller;

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
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController extends ReusableButtonController implements Initializable {

    @FXML
    private Label welcomeTextLabel;
    @FXML
    private Button overviewButton;
    @FXML
    Button exitButton;
    @FXML Button crudButton;
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
        welcomeTextLabel.setText("Welcome " + Session.getInstance().getCurrentUser().getFirstName() + "!");
        Session.getInstance().setCurrentScene("Admin");
    }

    public void setSceneAdmin() throws IOException {
        Scene sceneAdmin = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/adminStartpage-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneAdmin);
        stage.show();
    }

    public void onOverviewButton(ActionEvent a) throws IOException {
        Session.getInstance().setPreviousScene("Admin");
        Session.getInstance().getOverviewController().setSceneOverview();
    }

    public void onUpdateButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().setPreviousScene(("Admin"));
        Session.getInstance().getUpdateController().setSceneUpdate();
    }

    public void onAddButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().setPreviousScene(("Admin"));
        Session.getInstance().getAddController().setAddScene();
    }

    public void onExitButtonClick() {
        exit();


    }
    public void onDeleteButtonClick(ActionEvent a) throws IOException {
        Scene sceneDeleteItem = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/delete-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneDeleteItem);
        stage.show();
    }

}


