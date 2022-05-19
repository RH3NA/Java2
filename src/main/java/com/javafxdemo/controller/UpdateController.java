package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
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

public class UpdateController extends ReusableButtonController implements Initializable {
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.getInstance().setCurrentScene("Update");
        if (textInput.getText().isBlank()) {
            textInput.setText("Please fill in the item ID.");
            }
        }

        public void onConfirmButton(ActionEvent a) throws SQLException {
            if (confirmButtonCounter == 0) {
                inputID = Integer.parseInt(textInput.getText());
                System.out.println(inputID + attributeInput + valueInput);
                confirmButtonCounter++;
            }
            if (confirmButtonCounter == 1) {
                textLabel.setText("Which attribute do you want to change?");
                if (!textInput.getText().equalsIgnoreCase(String.valueOf(inputID))) {
                    attributeInput = textInput.getText();
                    System.out.println(inputID + attributeInput + valueInput);
                    confirmButtonCounter++;
                }
            }
            if (confirmButtonCounter == 2) {
                textLabel.setText("Please type the new value.");
                if (!textInput.getText().equalsIgnoreCase(String.valueOf(attributeInput))) {
                    valueInput = textInput.getText();
                    System.out.println(inputID + attributeInput + valueInput);
                    confirmButtonCounter++;
                }
            }
            if (confirmButtonCounter == 3) {
                ItemModel.updateItem(inputID, attributeInput, valueInput);
                ItemModel.getItemsDB();
                for (int i = 0; ItemModel.items.size() > 0; i++) {
                    if (ItemModel.items.get(i).getIdItem() == inputID) {
                        if (ItemModel.items.get(i).toString().contains(valueInput)) {
                            System.out.println("Success");
                            textLabel.setText("Successfully added!");
                            break;
                        }
                    }
                }



                    }
                }





        public void setSceneUpdate () throws IOException {
            Scene sceneUpdate = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/update-view.fxml")));
            Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
            stage.setScene(sceneUpdate);
            stage.show();
        }

        public void onBackButtonClick (ActionEvent a) throws IOException {
            backMethod(Session.getInstance().getPreviousScene());
        }

        public void onExitButtonClick () {
            exit();
        }

    /*public void updateItem(int idItem) {
        if(!textInput.getText().isBlank()) {
            textInput.getText();
        }
    }*/
    }
