package com.javafxdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Session extends Application {

    public Session() {
    }

        @Override
        public void start(Stage stage) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/login-view.fxml"));  // (LibraryApplication.class.getResource("login-fxml.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("LibraryStylesheet.css").toExternalForm());
            stage.setTitle("Ruminski Library");
            stage.setScene(scene);
            stage.show();
        }

    }


