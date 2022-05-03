package com.javafxdemo;

import javafx.application.Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraryApplication {


    public static void main(String[] args) {
        Session session = new Session();
        Application.launch(session.getClass(), args);
    }
}
