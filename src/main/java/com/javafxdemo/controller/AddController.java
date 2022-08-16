package com.javafxdemo.controller;

import com.javafxdemo.InheritedMethods;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.ReusableInterface;
import com.javafxdemo.Session;
import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.ItemHasCreatorModel;
import com.javafxdemo.models.ItemModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//This controller controls the Add function and its view.

public class AddController extends InheritedMethods implements Initializable, ReusableInterface {

    @FXML
    private TextField totalStockInput;
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
    @FXML
    private Label authorLastnameLable;
    @FXML
    TextField authorLastnameInput;
    @FXML
    private Label authorFirstnameLable;
    @FXML
    TextField authorFirstnameInput;
    @FXML
    private Label inserItemInfoLable;

    @FXML
    TextField locationInput;
    @FXML
    Label successMessageLabel;



// This method adds the new item to the DB. We also first stored it in the application as a currentAdd in case we needed to do some other operations
    // on the same values. We later decided to not implement this for the other CRUD operations.
        public void addNewItemToDB () throws SQLException {
            boolean inItems = false;
            boolean inInventory = false;
            boolean inCreators = false;
            ItemModel newItem = new ItemModel(Integer.parseInt(String.valueOf(idItemInput.getText())), Integer.parseInt(String.valueOf(inStockInput.getText())), titleInput.getText(), isbnInput.getText(), Integer.parseInt(String.valueOf(totalInStockInput.getText())), publisherInput.getText());
            System.out.println(newItem);

            ItemModel.insertItem(newItem.getIdItem(), newItem.getNumberInStock(), newItem.getTitle(), newItem.getIsbn(), newItem.getTotalStock(), newItem.getPublisher());
            System.out.println("" + Integer.parseInt(barcodeInput.getText()) + " " + newItem.getIdItem() + " " + Integer.parseInt(barcodeInput.getText()) + " " + newItem.getIdItem() + " " + categoryInput.getText());
            System.out.println("" + newItem.getIdItem() + authorFirstnameInput.getText() + authorLastnameInput.getText());

            InventoryModel.insertInventory(Integer.parseInt(barcodeInput.getText()), newItem.getIdItem(), 210211, categoryInput.getText());
            ItemHasCreatorModel.insertItemHasCreator(newItem.getIdItem(), authorFirstnameInput.getText(), authorLastnameInput.getText());

            ItemModel.getItemsDB(); //refreshing all of the lists with objects from the database
            ItemHasCreatorModel.getItemHasCreatorDB();
            InventoryModel.getInventoryDB();

            for (int i = 0; ItemModel.items.size() > 0; i++) { //trying to find a match between the database object and the new object
                if (newItem.getIdItem() == ItemModel.items.get(i).getIdItem()) {
                    inItems = true;
                    break;
                }
            }
            for (int i = 0; ItemHasCreatorModel.creators.size() > 0; i++) {
                if (newItem.getIdItem() == ItemHasCreatorModel.creators.get(i).getItem_IdItem()) {
                    inCreators = true;
                    break;
                }
            }
            for (int i = 0; InventoryModel.inventory.size() > 0; i++) {
                if (newItem.getIdItem() == InventoryModel.inventory.get(i).getItems_idItems()) {
                    inInventory = true;
                    break;
                }
            }
            if (inItems && inInventory && inCreators) {
                successMessageLabel.setText("Successfully added item!");
            }
            else {
                successMessageLabel.setText("Something went wrong with your insert.");
            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void onupdateItemClick(ActionEvent actionEvent) throws SQLException {
        addNewItemToDB();
    }

    public void setAddScene() throws IOException {
        Scene sceneAdd = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/add-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneAdd);
        stage.show();


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
            Session.getInstance().getAdminController().setSceneAdmin();
        }
    }
}






