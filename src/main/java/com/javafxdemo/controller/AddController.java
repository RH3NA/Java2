package com.javafxdemo.controller;

import com.javafxdemo.models.ItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AddController {


    @FXML
    private Label idItemLable;
    @FXML
    TextField idItemInput;
    @FXML
    private Label inStockLable;
    @FXML
    TextField inStockInput;
    @FXML
    private Label titleLable;
    @FXML
    TextField titleInput;
    @FXML
    Button confirmCRUDButton;
    @FXML
    private Label isbnLable;
    @FXML
    TextField isbnInput;
    @FXML
    private Label totalInStockLable;
    @FXML
    TextField totalInStockInput;
    @FXML
    private Label publisherLable;
    @FXML
    TextField publisherInput;
    @FXML
    TextField categoryInput;
    @FXML
    private Label barcodeLable;
    @FXML
    TextField barcodeInput;
    private Label authorLastnameLable;
    @FXML
    TextField authorLastnameInput;
    private Label authorFirstnameLable;
    @FXML
    TextField authorFirstnameInput;



    public int getIntFromTextField(TextField textField) {
        String text = textField.getText();
        return Integer.parseInt(text);
    }


    public void onupdateItemClick(ActionEvent actionEvent) throws SQLException {



            //ItemModel.addItem(getIntFromTextField(barcodeInput),getIntFromTextField(barcodeInput),getIntFromTextField(inStockInput), titleInput.getText(), isbnInput.getText(), getIntFromTextField(totalInStockInput), publisherInput.getText(),authorFirstnameInput.getText(),authorLastnameInput.getText(),categoryInput.getText());


        // new ItemModel(idItemInput.getText(), idItemInput.getText(), inStockInput.getText(), titleInput.getText(), isbnInput.getText(), totalInStockInput.getText(), publisherInput.getText());

    }
}

