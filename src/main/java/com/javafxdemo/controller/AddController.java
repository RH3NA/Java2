package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddController extends ReusableButtonController implements Initializable {

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






    /*public int getIntFromTextField(TextField textField) {
        String text = textField.getText();
        return Integer.parseInt(text);*/

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

            ItemModel.getItemsDB();
            ItemHasCreatorModel.getItemHasCreatorDB();
            InventoryModel.getInventoryDB();

            for (int i = 0; ItemModel.items.size() > 0; i++) {
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
        Session.getInstance().getAdminController().backMethod("Admin");
    }

    public void onExitButtonClick() {
        exit();
    }
    }




