package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.javafxdemo.LibraryApplication.setSceneStartPage;

public class ReusableButtonController {

    public void exit() {
        System.exit(0);
    }

    public void backMethod(String previousScene) throws IOException {
        if (previousScene.equalsIgnoreCase("StartpageLoggedIn") && Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == Boolean.TRUE) {
            Session.getInstance().getStartpageLoggedInController().setSceneStartpageLoggedIn();
        }
        if (previousScene.equalsIgnoreCase("Startpage")) {
            setSceneStartPage();
        }
        if (previousScene.equalsIgnoreCase("Admin") && (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == (Boolean.TRUE)) && ((Session.getInstance().getCurrentUser().getUserType() == 1114) || Session.getInstance().getCurrentUser().getUserType() == 1115)) {
            Session.getInstance().getAdminController().setSceneAdmin();
        }
        if (previousScene.equalsIgnoreCase("Delete") && (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == (Boolean.TRUE)) && ((Session.getInstance().getCurrentUser().getUserType() == 1114) || Session.getInstance().getCurrentUser().getUserType() == 1115)) {
            Session.getInstance().getDeleteController().setSceneDeleteItem();
        }
    }

    public void onDeleteButtonClick(ActionEvent a) throws IOException {
        Scene sceneDeleteItem = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/delete-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneDeleteItem);
        stage.show();
    }

}
