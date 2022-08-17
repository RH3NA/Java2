package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import com.javafxdemo.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import static com.javafxdemo.models.UserModel.users;
//this class controls everything related to the startpage and its view
@SuppressWarnings("SpellCheckingInspection")
public class StartpageController extends DBConnection implements Initializable {

    @FXML public Button HelloButton;
    @FXML public Button startPageSearchButton;
    @FXML private Label loginErrorLabel;
    @FXML TextField usernameInput;
    @FXML PasswordField passwordInput;
    @FXML Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label welcomeTextLabel;

    private int password;

    public void loginButtonOnAction(MouseEvent a) throws SQLException {
        if (passwordInput.getText().isBlank() && usernameInput.getText().isBlank()) {
            loginErrorLabel.setText("Fill in username and password.");
        } else {
            validateLogin();
        }
    }

    public void validateLogin() throws SQLException {
        Connection conn = super.getConnection();
        Session.getInstance().getCurrentUser().getUsersDB();

        // If the DBquery email value (username input) = idUser value (password input), then '1' will be returned, meaning a match.
        String verifyLogin = "select count(1) from D0005N.User where email = '" + usernameInput.getText() + "' and idUser = '" + passwordInput.getText() + "'";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);

            while (resultSet.next()) {
                if (resultSet.getInt(1) == 1) { //checks for a match in the log in details in the database
                    loginErrorLabel.setText("Welcome. Successful login.");
                    password = Integer.parseInt(passwordInput.getText());
                    setUpCurrUser();
                    if (Session.getInstance().getCurrentUser().getUserType() == 1111 || Session.getInstance().getCurrentUser().getUserType() == 1112 //redirects to the proper startpage based on if they're normal customers or admins/staff
                            || Session.getInstance().getCurrentUser().getUserType() == 1113) {
                        Session.getInstance().getStartpageLoggedInController().setSceneStartpageLoggedIn();
                    }
                    if (Session.getInstance().getCurrentUser().getUserType() == 1114 || Session.getInstance().getCurrentUser().getUserType() == 1115) {
                        Session.getInstance().getAdminController().setSceneAdmin();
                    }
                } else {
                    loginErrorLabel.setText("Invalid login. Please try again or register.");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpCurrUser() { //method to compare the current user to the users in the stored arraylist from the database to create the current user
        for (com.javafxdemo.models.UserModel user : users) {
            if (password == user.getIdUser()) {
                Session.getInstance().setCurrentUser(new UserModel(user.getIdUser(), user.getLastName(), user.getFirstName(), user.getPhoneNumber(), user.getEmail(), user.getUserType(), Boolean.TRUE, Boolean.FALSE));
                System.out.println("Current user information: " + Session.getInstance().getCurrentUser());
                System.out.println(Session.getInstance().getCurrentUser().getFirstName());
                break;
            }
        }
    }

    public void onRegisterButtonClick(ActionEvent a) throws IOException {
        Scene sceneRegister = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/registration-view.fxml")));
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(sceneRegister);
        stage.show();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeTextLabel.setText("Welcome to Ruminski Library! Search, login or register below...");
    }

    public void onStartPageSearchButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().getSearchController().setSceneSearch();
        Session.getInstance().setPreviousScene("Startpage");
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.getInstance().setCurrentScene("Startpage");
    }
}