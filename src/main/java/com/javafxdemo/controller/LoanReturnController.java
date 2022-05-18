package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import com.javafxdemo.models.InventoryModel;
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

public class LoanReturnController extends ReusableButtonController implements Initializable {
    @FXML
    private Label returnText;

    public static ArrayList<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public static void setCheckBoxes(ArrayList<CheckBox> checkBoxes) {
        LoanReturnController.checkBoxes = checkBoxes;
    }

    public static ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    public static ArrayList<DialogPane> dialogPanes = new ArrayList<>();

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
            LoanModel.getAllLoansIdUser(Session.getInstance().getCurrentUser().getIdUser());
            LoanModel.getLoansDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         int dialogPaneIncreaseSizeY = 20;
         int checkBoxIncreaseSizeY = 33;
        for (int i = 0; LoanModel.currentUserLoans.size() > i; i++) {
            DialogPane dialogPane = new DialogPane();
            try {
                dialogPane.setContentText("Title = " + getTitleFromBarcode(LoanModel.currentUserLoans.get(i).getIdBarcode()) + " Loan ID = " + LoanModel.currentUserLoans.get(i).getIdLoan() + " Barcode = " + LoanModel.currentUserLoans.get(i).getIdBarcode() + " Expiry date = " + LoanModel.currentUserLoans.get(i).getExpiryDate());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //dialogPane.setPrefColumnCount(DEFAULT_PREF_COLUMN_COUNT);
           // dialogPane.setPrefHeight(53);
           dialogPane.setPrefWidth(560);
            CheckBox checkBox = new CheckBox();
            checkBox.setText("Choose");
            dialogPane.setLayoutY(dialogPaneIncreaseSizeY);
            dialogPane.setLayoutX(3);
            dialogPaneIncreaseSizeY = dialogPaneIncreaseSizeY + 60;
            checkBox.setLayoutY(checkBoxIncreaseSizeY);
            checkBox.setLayoutX(545);
            checkBoxIncreaseSizeY = checkBoxIncreaseSizeY + 60;
            anchorPane1.getChildren().add(dialogPane);
            anchorPane1.getChildren().add(checkBox);
            checkBoxes.add(checkBox);
            dialogPanes.add(dialogPane);
        }
    }

    public void onConfirmButton(ActionEvent a) throws SQLException {
        for (int i = 0; checkBoxes.size() > i; i++) {
            System.out.println(dialogPanes.get(i).getContentText());
            //System.out.println("Title = " + getTitleFromBarcode(LoanModel.currentUserLoans.get(i).getIdBarcode()) + " Loan ID = " + LoanModel.currentUserLoans.get(i).getIdLoan() + " Barcode = " + LoanModel.currentUserLoans.get(i).getIdBarcode() + " Expiry date = " + LoanModel.currentUserLoans.get(i).getExpiryDate());
            if (checkBoxes.get(i).isSelected()) {
                LoanreturnModel.returnLoan(LoanModel.currentUserLoans.get(i).getIdLoan());
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
        backMethod(Session.getInstance().getPreviousScene());
    }
}

