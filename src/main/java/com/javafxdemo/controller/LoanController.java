package com.javafxdemo.controller;

import com.javafxdemo.Session;
import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.LoanModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class LoanController {

    public LoanController() {};

    static int idLoanIncrement = 0;

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
        }
       else {
                System.out.println("No objects to loan.");
            }

        }


    public static int createIdLoan() { //needs better algorithm since we dont have autoincrement
        return ((Session.getInstance().getCurrentUser().getIdUser()) + (Session.getInstance().getCurrentLoan().getIdBarcode()));
    }
}


