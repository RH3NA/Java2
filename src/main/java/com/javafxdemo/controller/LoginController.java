package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.LoggedInSession;
import com.javafxdemo.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

@SuppressWarnings("SpellCheckingInspection")
public class LoginController {

    @FXML private Label welcomeText;
    @FXML private Label loginErrorLabel;
    @FXML TextField usernameInput;
    @FXML PasswordField passwordInput;

    @FXML Button loginButton;

    private String loggedInIdUser;
    public String getLoggedInIdUser() {
        return loggedInIdUser;
    }
    public void setLoggedInIdUser(String loggedInIdUser) {
        this.loggedInIdUser = loggedInIdUser;
    }
    private String loggedInUserType;
    public String getLoggedInUserType() {
        return loggedInUserType;
    }

    public void setLoggedInUserType(String loggedInUserType) {
        this.loggedInUserType = loggedInUserType;
    }







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
                    setLoggedInIdUser(passwordInput.getText());
                    System.out.println(getLoggedInIdUser());
                    createNewSession();
                    LoggedInSession loggedInSession = new LoggedInSession(getLoggedInIdUser(), getLoggedInUserType());
                        if(loggedInSession.getUserTypeId().equalsIgnoreCase("1111") || loggedInSession.getUserTypeId().equalsIgnoreCase("1112")
                            || loggedInSession.getUserTypeId().equalsIgnoreCase("1113")) {
                                Scene sceneLoggedIn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/loggedIn-view.fxml")));
                                Stage stage = (Stage) loginButton.getScene().getWindow();
                                stage.setScene(sceneLoggedIn);
                                stage.show();

                }
                if(loggedInSession.getUserTypeId().equalsIgnoreCase("1114") || loggedInSession.getUserTypeId().equalsIgnoreCase("1115")) {
                    System.out.println("We have to create an admin page..");
                    }
                }
                else {
                    loginErrorLabel.setText("Invalid login. Please try again or register.");
                    break;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createNewSession() throws SQLException {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        String checkUserType = "select UserType_idUserType from D0005N.User where idUser = '" + getLoggedInIdUser() + "'";


        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(checkUserType);


            while (resultSet.next()) {
                setLoggedInUserType(String.valueOf(resultSet.getInt("UserType_idUserType")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("id user = " + getLoggedInIdUser() + " user type = " + getLoggedInUserType());
    }




    @FXML
    private Button RegisterButton;

    public void onRegisterButtonClick(ActionEvent a) throws IOException {
        Scene sceneRegister = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/registration-view.fxml")));
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
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) loginSearchButton.getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

}