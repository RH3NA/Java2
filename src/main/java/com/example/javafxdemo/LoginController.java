package com.example.javafxdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label loginErrorLabel;
    @FXML
    TextField usernameInput;
    @FXML
    PasswordField passwordInput;

    public void loginButtonOnAction(ActionEvent a) {
        if (!passwordInput.getText().isBlank() && !usernameInput.getText().isBlank()) {
            loginErrorLabel.setText("Something went wrong. Try again, or click 'register'.");
        } else {
            loginErrorLabel.setText("Fill in username and password.");
        }
    }

    @FXML
    private Button RegisterButton;

    public void onRegisterButtonClick(ActionEvent a) throws IOException {
        Scene sceneRegister = new Scene(FXMLLoader.load(getClass().getResource("registration-view.fxml")));
        Stage stage = (Stage) RegisterButton.getScene().getWindow();
        stage.setScene(sceneRegister);
        stage.show();
    }

    @FXML
    private Label welcomeTextLabel;

    @FXML
    protected void onHelloButtonClick() {
        welcomeTextLabel.setText("Welcome to Ruminski Library! Search, login or register below...");
    }

    @FXML
    private Button loginSearchButton;

    public void onLoginSearchButtonClick(ActionEvent a) throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(getClass().getResource("search-view.fxml")));
        Stage stage = (Stage) loginSearchButton.getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

    @FXML
    private TextField searchTextInputField;
    @FXML
    private TextArea searchForResultArea;

    public void onSearchInputButtonClick() {
        searchForResultArea.setText(searchTextInputField.getText());
    }

}