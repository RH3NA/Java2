package com.javafxdemo;

import com.javafxdemo.controller.*;
import com.javafxdemo.models.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static com.javafxdemo.models.UserModel.getUsersDB;

public class LibraryApplication extends Application {
    public static Stage getStage() {
        return stage;
    }
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/startpage-view.fxml"));  // (LibraryApplication.class.getResource("login-fxml.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("LibraryStylesheet.css").toExternalForm());
        stage = primaryStage;
        stage.setTitle("Ruminski Library");
        stage.setScene(scene);
        stage.show();
        }


    public static void main(String[] args) {
        launch(args);
    }
}