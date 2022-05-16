package com.javafxdemo.controller;

import com.javafxdemo.Session;
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
    TextField authorInput;
    @FXML
    TextField categoryInput;



    public void onupdateItemClick(ActionEvent actionEvent) throws SQLException {

        if (idItemInput.getText().isBlank() && inStockInput.getText().isBlank()
                && titleInput.getText().isBlank() && isbnInput.getText().isBlank() && totalInStockInput.getText().isBlank()
                && publisherInput.getText().isBlank() && authorInput.getText().isBlank() && categoryInput.getText().isBlank() ) {
           // errorCrudEntrys.setText("All entrys needs values");
        } else
            ItemModel.addItem((idItemInput, inStockInput, titleInput, isbnInput, totalInStockInput, publisherInput, );
        // new ItemModel(idItemInput.getText(), idItemInput.getText(), inStockInput.getText(), titleInput.getText(), isbnInput.getText(), totalInStockInput.getText(), publisherInput.getText());

    }
}

