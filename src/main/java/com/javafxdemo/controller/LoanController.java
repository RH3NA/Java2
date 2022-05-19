package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.ItemHasCreatorModel;
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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LoanController extends ReusableButtonController implements Initializable {

    public LoanController() {
    }


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
    @FXML
    private Label receiptLabel;

    public void onLoanConfirmButton(ActionEvent a) throws SQLException {
        boolean barcodeIsAvailable = InventoryModel.checkAvailableBarcode(Session.getInstance().getCurrentLoan().getIdBarcode());
        if (barcodeIsAvailable) {
            int newLoanID = createIdLoan(Session.getInstance().getCurrentUser().getIdUser());
            LoanModel.insertLoan(createIdLoan(Session.getInstance().getCurrentUser().getIdUser()), Session.getInstance().getCurrentUser().getIdUser(), Session.getInstance().getCurrentLoan().getIdBarcode(), null, null);
            LoanModel.getLatestLoanDBidUser(Session.getInstance().getCurrentUser().getIdUser());
            System.out.println(LoanModel.currentUserLatestLoan.get(0).getIdLoan());
            if(LoanModel.currentUserLatestLoan.isEmpty()) {
                System.out.println("No loans");
            }
            if (LoanModel.currentUserLatestLoan.get(0).getIdLoan() == newLoanID) {
                receiptLabel.setText("Success! Loandate: " + LoanModel.currentUserLatestLoan.get(0).getLoanDate() +
                        "\nRemember to return your item before: " + LoanModel.currentUserLatestLoan.get(0).getExpiryDate());
            }

        }
        if (!barcodeIsAvailable) {
            receiptLabel.setText("No available items to loan.");
        }
        if (Session.getInstance().getCurrentUser().getHasTooManyLoans() == Boolean.TRUE) {
            receiptLabel.setText("You have too many active loans. Return a current loan before attempting to loan a new item.");
        }
    }


    public static int createIdLoan(int idUser) { //needs better algorithm since we dont have autoincrement
        return ((idUser) + (Session.getInstance().getCurrentLoan().getIdBarcode()) + (LocalDateTime.now().getMinute()));
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ItemHasCreatorModel.getItemHasCreatorDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        selectedLoanItemsLabel.setText("Selected barcode ID: " + Session.getInstance().getCurrentLoan().getIdBarcode() + "");

        Session.getInstance().setCurrentScene("Loan");

    }

    public void setSceneLoan() throws IOException {
        Scene sceneLoanReturn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/loan-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneLoanReturn);
        stage.show();
    }

    public void onBackButtonClick(ActionEvent a) throws IOException {
        backMethod(Session.getInstance().getPreviousScene());
    }

    public void onExitButtonClick() {
        exit();
    }
}


