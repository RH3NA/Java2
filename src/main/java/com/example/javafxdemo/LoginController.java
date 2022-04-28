package com.example.javafxdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML private Label loginErrorLabel;
    @FXML TextField usernameInput;
    @FXML PasswordField passwordInput;

    public void loginButtonOnAction(ActionEvent a) {
        if (passwordInput.getText().isBlank() && usernameInput.getText().isBlank()) {
            loginErrorLabel.setText("Fill in username and password.");
        } else {
            validateLogin();
        }
    }

    public void validateLogin() {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        // If the DBquery email value (username input) = idUser value (password input), then '1' will be returned, meaning a match.
        String verifyLogin = "select count(1) from D0005N.User where email = '" + usernameInput.getText() + "' and idUser = '" + passwordInput.getText() + "'";


        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    loginErrorLabel.setText("Welcome. Successful login.");

                } else {
                    loginErrorLabel.setText("Invalid login. Please try again or register.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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

}