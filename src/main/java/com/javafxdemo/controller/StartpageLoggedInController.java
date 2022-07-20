package com.javafxdemo.controller;

import com.javafxdemo.InheritedMethods;
import com.javafxdemo.ReusableInterface;
import com.javafxdemo.Session;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.models.LoanModel;
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
import java.sql.SQLException;
import java.util.ResourceBundle;
//this controller controls everything with the logged in startpage and its view
public class StartpageLoggedInController extends InheritedMethods implements Initializable, ReusableInterface { //added a logged in startpage

    @FXML
    private Button loanButton;
    @FXML
    private Label itemsOverdueLabel;
    @FXML
    private Label itemsOnLoanLabel;
    @FXML
    private Label welcomeTextLabel;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //setting some pre-determined values when you enter the page
        Session.getInstance().setPreviousScene("Startpage");
        welcomeTextLabel.setText("Welcome " + Session.getInstance().getCurrentUser().getFirstName() + "!");
        try {
            itemsOnLoanLabel.setText("Active loans: " + LoanModel.getLoanCountIdUser(Session.getInstance().getCurrentUser().getIdUser()));
            itemsOverdueLabel.setText("Items overdue: " + LoanModel.getOverdueLoansCount(Session.getInstance().getCurrentUser().getIdUser()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSceneStartpageLoggedIn() throws IOException {
        Scene sceneStartPageLoggedIn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/startpageloggedin-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneStartPageLoggedIn);
        stage.show();
        Session.getInstance().setCurrentScene("StartpageLoggedIn");
    }

    public void onSearchButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().getSearchController().setSceneSearch();
        Session.getInstance().setPreviousScene("StartpageLoggedIn");
    }

    public void onLoanButtonClick(ActionEvent a) throws IOException {
        Session.getInstance().getSearchController().setSceneSearch();
        Session.getInstance().setPreviousScene("StartpageLoggedIn");
    }

    public void onReturnLoanButton(ActionEvent a) throws IOException {
        Session.getInstance().getLoanReturnController().setSceneLoanReturn();
        Session.getInstance().setPreviousScene("StartpageLoggedIn");
    }

    public void onExitButtonClick() {
        exit();
    }

    @Override
    public void backMethod() throws IOException {
    }
}

