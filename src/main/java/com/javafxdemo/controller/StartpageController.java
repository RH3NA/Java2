package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Context;
import com.javafxdemo.models.UserModel;
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

import static com.javafxdemo.models.UserModel.*;

@SuppressWarnings("SpellCheckingInspection")
public class StartpageController {

    @FXML private Label welcomeText;
    @FXML private Label loginErrorLabel;
    @FXML TextField usernameInput;
    @FXML PasswordField passwordInput;
    @FXML Button loginButton;
    private int password;

    public void loginButtonOnAction(ActionEvent a) throws SQLException {
        if (passwordInput.getText().isBlank() && usernameInput.getText().isBlank()) {
            loginErrorLabel.setText("Fill in username and password.");
        } else {
            validateLogin();
        }
    }

    public void validateLogin() throws SQLException {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        getUsersDB();

        // If the DBquery email value (username input) = idUser value (password input), then '1' will be returned, meaning a match.
        String verifyLogin = "select count(1) from D0005N.User where email = '" + usernameInput.getText() + "' and idUser = '" + passwordInput.getText() + "'";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);


            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    loginErrorLabel.setText("Welcome. Successful login.");
                    password = Integer.parseInt(passwordInput.getText());
                    setUpCurrUser();
                        if(Context.getInstance().getCurrentUser().getUserType() == 1111 || Context.getInstance().getCurrentUser().getUserType() == 1112
                            || Context.getInstance().getCurrentUser().getUserType() == 1113) {
                                Scene sceneLoggedIn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/startpageloggedin-view.fxml")));
                                Stage stage = (Stage) loginButton.getScene().getWindow();
                                stage.setScene(sceneLoggedIn);
                                stage.show();


                }
                if(Context.getInstance().getCurrentUser().getUserType() == 1114 || Context.getInstance().getCurrentUser().getUserType() == 1115) {
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

    public void setUpCurrUser() { //method to compare the current user to the users in the stored arraylist, only problem rn is that this only works for 1 user
                                //should probably make a global method that stores every user as well
                                //and also rename this one to something not so similar to the other getCurrentUser method
        for (com.javafxdemo.models.UserModel user : users) {
            if (password == user.getIdUser()) {
                Context.getInstance().setCurrentUser(new UserModel(user.getIdUser(), user.getLastName(), user.getFirstName(), user.getPhoneNumber(), user.getEmail(), user.getUserType(), Boolean.TRUE));
                System.out.println("Current user information:" + Context.getInstance().getCurrentUser());
                System.out.println(Context.getInstance().getCurrentUser().getFirstName());
                break;
            }
        }
    }

    /*public void setUserType() throws SQLException {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        String checkUserType = "select UserType_idUserType from D0005N.User where idUser = '" + passwordInput.getText() + "'";


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
    }*/




    @FXML
    private Button registerButton;

    public void onRegisterButtonClick(ActionEvent a) throws IOException {
        Scene sceneRegister = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/registration-view.fxml")));
        Stage stage = (Stage) registerButton.getScene().getWindow();
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
    private Button startPageSearchButton;

    public void onStartPageSearchButtonClick(ActionEvent a) throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) startPageSearchButton.getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

}