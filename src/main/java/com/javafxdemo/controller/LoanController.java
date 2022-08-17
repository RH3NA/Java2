package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.ReusableInterface;
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
//this controller controls everything with the loan function and its views
public class LoanController extends DBConnection implements Initializable, ReusableInterface {
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

    public Label getSelectedLoanItemsLabel() {
        return selectedLoanItemsLabel;
    }
    public void setSelectedLoanItemsLabel(Label selectedLoanItemsLabel) {
        this.selectedLoanItemsLabel = selectedLoanItemsLabel;
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ItemHasCreatorModel.getItemHasCreatorDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        selectedLoanItemsLabel.setText("Selected barcode ID: " + Session.getInstance().getCurrentLoan().getIdBarcode() + "   '" + Session.getInstance().getCurrentSearch().getTitle() + "' by " + Session.getInstance().getCurrentSearch().getAuthorFirstName() + " " + Session.getInstance().getCurrentSearch().getAuthorLastName()); //creating the loan view with the selected barcode from loan
        Session.getInstance().setCurrentScene("Loan");
    }

    public void setSceneLoan() throws IOException {
        Scene sceneLoanReturn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/loan-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneLoanReturn);
        stage.show();
    }

    public void onLoanConfirmButton(ActionEvent a) throws SQLException {
        boolean barcodeIsAvailable = InventoryModel.checkAvailableBarcode(Session.getInstance().getCurrentLoan().getIdBarcode()); //checks if the barcode is available
        LoanModel.getLatestLoanDBidUser(Session.getInstance().getCurrentUser().getIdUser()); //gets the latest loan from the currentuser
        if (barcodeIsAvailable) {
            int newLoanID = createIdLoan(Session.getInstance().getCurrentUser().getIdUser()); //creates new IDloan
            LoanModel.insertLoan(createIdLoan(Session.getInstance().getCurrentUser().getIdUser()), Session.getInstance().getCurrentUser().getIdUser(), Session.getInstance().getCurrentLoan().getIdBarcode(), null, null); //finally inserts the loan into the database
            System.out.println(LoanModel.currentUserLatestLoan.get(0).getIdLoan()); //gets the latest current user loan at index 0
            if(LoanModel.currentUserLatestLoan.isEmpty()) { //some error handling
                receiptLabel.setText("No loan could be created. Please try again.");
            }
            if (LoanModel.currentUserLatestLoan.get(0).getIdLoan() == newLoanID) { //if the new loan ID matches the latest loan created for the user, it's a success
                receiptLabel.setText("Success! Loan ID: " + LoanModel.currentUserLatestLoan.get(0).getIdLoan() + "\nLoandate: " + LoanModel.currentUserLatestLoan.get(0).getLoanDate() +
                        "\nRemember to return your item before: " + LoanModel.currentUserLatestLoan.get(0).getExpiryDate());
            }
        }
        if (!barcodeIsAvailable) {
            receiptLabel.setText("No available items to loan.");
        }
        if (Session.getInstance().getCurrentUser().getHasTooManyLoans() == Boolean.TRUE) {
            receiptLabel.setText("You have too many active loans. Return a current loan before attempting to loan a new item.");
        }
        borrowingRulesLabel.setVisible(false);
        loanConfirmButton.setVisible(false);
    }

    public static int createIdLoan(int idUser) { //simple fix for forgetting to implement autoincrement on our primary key in loan, but could be better since it's still possible to fail with this combination
        return ((idUser) + (Session.getInstance().getCurrentLoan().getIdBarcode()) + (LocalDateTime.now().getMinute()));
    }

    public void onBackButtonClick(ActionEvent a) throws IOException {
        backMethod();
    }

    public void onExitButtonClick() {
        exit();
    }

    @Override
    public void backMethod() throws IOException {
        if (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == Boolean.TRUE) {
            Session.getInstance().getStartpageLoggedInController().setSceneStartpageLoggedIn();
        }
    }
}


