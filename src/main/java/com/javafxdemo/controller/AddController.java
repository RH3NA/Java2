package com.javafxdemo.controller;

import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.ItemHasCreatorModel;
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

    public void addNewItemToDB() throws SQLException {


        ItemModel.insertItem(getIntFromTextField(idItemInput),getIntFromTextField(inStockInput),titleInput.getText(),isbnInput.getText(),getIntFromTextField(totalInStockInput),publisherInput.getText());
        InventoryModel.insertBarcode(getIntFromTextField(barcodeInput));
        ItemHasCreatorModel.insertAuthorFirstname(authorFirstnameInput.getText());
        ItemHasCreatorModel.insertAuthorLastname(authorLastnameInput.getText());

    }


    public void onupdateItemClick(ActionEvent actionEvent) throws SQLException {

        addNewItemToDB();



    }


}

