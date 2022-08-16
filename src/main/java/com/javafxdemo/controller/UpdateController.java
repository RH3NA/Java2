package com.javafxdemo.controller;

import com.javafxdemo.InheritedMethods;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.ReusableInterface;
import com.javafxdemo.Session;
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

//this controller controls everything related to the Update function and its view
public class UpdateController extends InheritedMethods implements Initializable, ReusableInterface {
    @FXML
    private Label textLabel;
    @FXML
    private TextField textInput;
    @FXML
    private Button ConfirmButton;
    int inputID;
    String attributeInput;
    String valueInput;
    int confirmButtonCounter = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //builds the first connection to the query, which is the id Item. this ensures that we connect it to the right item
        Session.getInstance().setCurrentScene("Update");
        if (textInput.getText().isBlank()) {
            textInput.setText("Please fill in the item ID.");
        }
    }

    public void onConfirmButton(ActionEvent a) throws SQLException {
        if (confirmButtonCounter == 0) { //loops to check how many times the confirm button has been pressed
            inputID = Integer.parseInt(textInput.getText()); //takes the value from the first input and stores it to be used later
            System.out.println(inputID + attributeInput + valueInput);
            confirmButtonCounter++;
        }
        if (confirmButtonCounter == 1) {
            textLabel.setText("Which attribute do you want to change?");
            if (!textInput.getText().equalsIgnoreCase(String.valueOf(inputID))) { //goes to the next step which is checking which attribute one wants to change, eg "title" "totalStock" etc
                attributeInput = textInput.getText();
                System.out.println(inputID + attributeInput + valueInput);
                confirmButtonCounter++;
            }
        }
        if (confirmButtonCounter == 2) {
            textLabel.setText("Please type the new value.");
            if (!textInput.getText().equalsIgnoreCase(String.valueOf(attributeInput))) { //goes to the next step which is checking what the new value should be
                valueInput = textInput.getText();
                System.out.println(inputID + attributeInput + valueInput);
                confirmButtonCounter++;
            }
        }
        if (confirmButtonCounter == 3) { //goes to the third step which is building the SQL query with the updateItem method that finally updates it
            ItemModel.updateItem(inputID, attributeInput, valueInput); //puts the stored values as parameters to the method
            ItemModel.getItemsDB(); //refreshes the item list
            for (int i = 0; ItemModel.items.size() > 0; i++) {
                if (ItemModel.items.get(i).getIdItem() == inputID) {
                    if (ItemModel.items.get(i).toString().contains(valueInput)) { //checks if there's a match in the stored database arraylist with the new changed value
                        System.out.println("Success");
                        textLabel.setText("Successfully added!");
                        break;
                    }
                }
            }


        }
    }


    public void setSceneUpdate() throws IOException {
        Scene sceneUpdate = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/update-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneUpdate);
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
