package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.ReusableInterface;
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
import java.util.ResourceBundle;

//this controller controls everything on the Admin startpage and its view

public class AdminController extends DBConnection implements Initializable, ReusableInterface {

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
    @FXML
    Button SearchButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeTextLabel.setText("Welcome " + Session.getInstance().getCurrentUser().getFirstName() + "!  Choose what you would like to do."); //overriding and initializing the view to include a welcome text based on the users firstname
        Session.getInstance().setCurrentScene("Admin");
        Session.getInstance().setPreviousScene("Startpage");
    }

    public void setSceneAdmin() throws IOException { //building the scene method to get to the admin startpage
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

    public void onSearchButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().getSearchController().setSceneSearch();
        Session.getInstance().setPreviousScene("Admin");
    }

    public void onReturnLoanButton(ActionEvent a) throws IOException {
        Session.getInstance().getLoanReturnController().setSceneLoanReturn();
        Session.getInstance().setPreviousScene("Admin");
    }

    public void onLoanButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().getSearchController().setSceneSearch();
        Session.getInstance().setPreviousScene("Admin");
    }

    public void onBackButtonClick() throws IOException {
    }
    @Override
    public void backMethod() throws IOException {
    }
}


