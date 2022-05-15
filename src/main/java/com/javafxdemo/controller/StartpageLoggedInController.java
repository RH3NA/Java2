package com.javafxdemo.controller;

import com.javafxdemo.Session;
import com.javafxdemo.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartpageLoggedInController implements Initializable { //added a logged in startpage

    @FXML
    private Button searchButton;
    @FXML
    private Button reservationButton;
    @FXML
    private Button loanReturnButton;
    @FXML
    private Button paymentButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private Button loanButton;


    @FXML
    private Label welcomeTextLabel;


    public void onSearchButtonClick(ActionEvent a) throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) searchButton.getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

    public void onLoanButtonClick(ActionEvent a) throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) loanButton.getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

    public void onStartPageLoggedInLogOutButton(ActionEvent a) {
    }

    public void onReturnLoanButton(ActionEvent a) throws IOException {
        Session.getInstance().getLoanReturnController().setSceneLoanReturn();
    }


    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeTextLabel.setText("Welcome " + Session.getInstance().getCurrentUser().getFirstName() + "!");

    }

    public void setSceneStartpageLoggedIn() throws IOException {
        Scene sceneStartPageLoggedIn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/startpageloggedin-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneStartPageLoggedIn);
        stage.show();
    }
}

