package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import com.javafxdemo.models.LoanModel;
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

import static javafx.scene.control.TextArea.DEFAULT_PREF_COLUMN_COUNT;

public class LoanReturnController implements Initializable {
    @FXML
    private Label returnText;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            LoanModel.getAllLoansIdUser(Session.getInstance().getCurrentUser().getIdUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int textAreaIncreaseSizeY = 47;
        int buttonAreaIncreaseSizeY = 64;
        for (int i = 0; LoanModel.currentUserLoans.size() > i; i++) {
            TextArea area = new TextArea();
            area.setText(String.valueOf(LoanModel.currentUserLoans.get(i)));
            area.setPrefColumnCount(DEFAULT_PREF_COLUMN_COUNT);
            area.setPrefHeight(37);
            area.setPrefWidth(353);
            Button button = new Button();
            button.setText("Return");
            area.setLayoutY(textAreaIncreaseSizeY);
            textAreaIncreaseSizeY = textAreaIncreaseSizeY + 47;
            button.setLayoutY(buttonAreaIncreaseSizeY);
            button.setLayoutX(53);
            buttonAreaIncreaseSizeY = buttonAreaIncreaseSizeY + 47;
            anchorPane1.getChildren().add(area);
            anchorPane2.getChildren().add(button);
        }
    }

    public void onLoanReturnButton(ActionEvent a) throws SQLException {
        LoanModel.getAllLoansIdUser(Session.getInstance().getCurrentUser().getIdUser());
        }



    public void setSceneLoanReturn() throws IOException {
        Scene sceneLoanReturn = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/loanReturn-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneLoanReturn);
        stage.show();
    }
}

