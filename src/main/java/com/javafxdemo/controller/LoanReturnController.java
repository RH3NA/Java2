package com.javafxdemo.controller;

import com.javafxdemo.InheritedMethods;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.ReusableInterface;
import com.javafxdemo.Session;
import com.javafxdemo.models.LoanModel;
import com.javafxdemo.models.LoanreturnModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.javafxdemo.models.InventoryModel.getTitleFromBarcode;

//this controller controls the loan return function and its view
public class LoanReturnController extends InheritedMethods implements Initializable, ReusableInterface {
    @FXML
    private Label returnText;

    public static ArrayList<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public static void setCheckBoxes(ArrayList<CheckBox> checkBoxes) {
        LoanReturnController.checkBoxes = checkBoxes;
    }

    public static ArrayList<CheckBox> checkBoxes = new ArrayList<>(); //arraylist to store the dynamically created checkboxes
    public static ArrayList<DialogPane> dialogPanes = new ArrayList<>(); //arraylist to store the dynamically created dialogPanes

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    @FXML
    private TextArea textArea;
    @FXML
    private ScrollBar scrollBar;
    @FXML
    private Label welcomeText;
    @FXML
    private Button loanReturnButton;
    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private AnchorPane anchorPane2;
    @FXML
    private Button confirmButton;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.getInstance().setCurrentScene("Loanreturn");
        try {
            LoanModel.getAllLoansIdUser(Session.getInstance().getCurrentUser().getIdUser()); //refreshing all loan for the current user
            LoanModel.getLoansDB(); //refreshing all loans for all users
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int dialogPaneIncreaseSizeY = 20; //setting the pre-determined values for the blueprint for the dialog panes and checkboxes
        int checkBoxIncreaseSizeY = 53;
        for (int i = 0; LoanModel.currentUserLoans.size() > i; i++) { //looping through the amount of loans the user has and builds the page based on that number
            DialogPane dialogPane = new DialogPane();
            try {
                dialogPane.setContentText("Title = " + getTitleFromBarcode(LoanModel.currentUserLoans.get(i).getIdBarcode()) + " Loan ID = " + LoanModel.currentUserLoans.get(i).getIdLoan() + " Barcode = " + LoanModel.currentUserLoans.get(i).getIdBarcode() + " Expiry date = " + LoanModel.currentUserLoans.get(i).getExpiryDate());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            dialogPane.setPrefWidth(750); //more pre-determined build data for each new dialogpane and checkbox
            CheckBox checkBox = new CheckBox();
            checkBox.setText("Choose");
            dialogPane.setLayoutY(dialogPaneIncreaseSizeY);
            dialogPane.setLayoutX(3);
            dialogPaneIncreaseSizeY = dialogPaneIncreaseSizeY + 60;
            checkBox.setLayoutY(checkBoxIncreaseSizeY);
            checkBox.setLayoutX(6);
            checkBoxIncreaseSizeY = checkBoxIncreaseSizeY + 60;
            anchorPane1.getChildren().add(dialogPane);
            anchorPane2.getChildren().add(checkBox);
            checkBoxes.add(checkBox);
            dialogPanes.add(dialogPane);
        }
    }

    public void onConfirmButton(ActionEvent a) throws SQLException {
        for (int i = 0; checkBoxes.size() > i; i++) { //looping through the checkbox array to check which checkbox is selected
            System.out.println(dialogPanes.get(i).getContentText()); //since the dialogpane and checkbox have the same amount of loans, eg. size, they will also have the same content connected to them, so we don't have to look for a match more than the arraylist.size number.
            if (checkBoxes.get(i).isSelected()) {
                LoanreturnModel.returnLoan(LoanModel.currentUserLoans.get(i).getIdLoan()); //here we can easily connect it to the same index of the loan array, since the lists are all connected
                System.out.println("Returned loan = " + LoanModel.currentUserLoans.get(i).toString());
                successLabel.setText("Success!");

                break;
            }
        }
    }

    @FXML
    private Label successLabel;

    public void setSceneLoanReturn() throws IOException {
        Scene sceneLoanReturn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/loanReturn-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneLoanReturn);
        stage.show();
    }

    public void onBackButtonClick() throws IOException {
        backMethod();
    }

    @Override
    public void backMethod() throws IOException {
        if (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == Boolean.TRUE) {
            Session.getInstance().getStartpageLoggedInController().setSceneStartpageLoggedIn();
        }
    }
}