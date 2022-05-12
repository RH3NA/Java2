package com.javafxdemo.controller;

import com.javafxdemo.Session;
import com.javafxdemo.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartpageLoggedInController { //added a logged in startpage

    @FXML
    private Button searchButton;
    @FXML
    private Button reservationButton;
    @FXML
    private Button roomBookingButton;
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

    public static Label getWelcomeTextLabel() {
        return welcomeTextLabel;
    }

    public static void setWelcomeTextLabel(Label welcomeTextLabel) {
        StartpageLoggedInController.welcomeTextLabel = welcomeTextLabel;
    }

    @FXML
    private static Label welcomeTextLabel;


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






}
