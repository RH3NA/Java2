package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.LoanModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoanController implements Initializable {

    public LoanController() {
    }

    ;

    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button reservationButton;
    @FXML
    private Button loanButton;
    @FXML
    private Button roomBookingButton;
    @FXML
    private Button paymentButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button historyButton;


    public Label getSelectedLoanItemsLabel() {
        return selectedLoanItemsLabel;
    }

    public void setSelectedLoanItemsLabel(Label selectedLoanItemsLabel) {
        this.selectedLoanItemsLabel = selectedLoanItemsLabel;
    }

    @FXML
    private Label selectedLoanItemsLabel;
    @FXML
    private Label borrowingRulesLabel;
    @FXML
    private Label inactiveAccountLabel;
    @FXML
    private Button loanConfirmButton;
    @FXML
    private Label borrowingRulesBrokenLabel;

    public void onLoanConfirmButton(ActionEvent a) throws SQLException {
        boolean barcodeIsAvailable = InventoryModel.checkAvailableBarcode(Session.getInstance().getCurrentLoan().getIdBarcode());
        if (barcodeIsAvailable) {
            LoanModel.insertLoan(createIdLoan(), Session.getInstance().getCurrentUser().getIdUser(), Session.getInstance().getCurrentLoan().getIdBarcode(), null, null);
        } else {
            System.out.println("No objects to loan.");
        }

    }


    public static int createIdLoan() { //needs better algorithm since we dont have autoincrement
        return ((Session.getInstance().getCurrentUser().getIdUser()) + (Session.getInstance().getCurrentLoan().getIdBarcode()));
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedLoanItemsLabel.setText(Session.getInstance().getCurrentLoan().toString());

    }

    public void setSceneLoan() throws IOException {
        Scene sceneLoanReturn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/loan-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneLoanReturn);
        stage.show();
    }
}



