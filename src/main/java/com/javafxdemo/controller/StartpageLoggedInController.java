package com.javafxdemo.controller;

import com.javafxdemo.Context;
import com.javafxdemo.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartpageLoggedInController { //added a logged in startpage

    @FXML
    private Button startPageLoggedInSearchButton;
    @FXML
    private Button startPageLoggedInActiveLoansButton;
    @FXML
    private Button startPageLoggedInLogOutButton;
    @FXML
    private Label startPageLoggedInWelcomeLabel;


    public void onStartPageLoggedInSearchButtonClick(ActionEvent a) throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) startPageLoggedInSearchButton.getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

    public void onStartPageLoggedInActiveLoansButton(ActionEvent a) {
        startPageLoggedInWelcomeLabel.setText("Welcome " + Context.getInstance().getCurrentUser().getFirstName() + "!");
    }

    public void onStartPageLoggedInLogOutButton(ActionEvent a) {
    }


}
