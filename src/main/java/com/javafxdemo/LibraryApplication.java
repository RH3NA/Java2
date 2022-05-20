package com.javafxdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//the main class, which contains the main method and the start method where it builds the primary stage as well
public class LibraryApplication extends Application {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage stage) {
        LibraryApplication.stage = stage;
    }

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

    public static void setSceneStartPage() throws IOException {
        Scene sceneStartPage = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/startpage-view.fxml")));
        sceneStartPage.getStylesheets().add(LibraryApplication.class.getResource("LibraryStylesheet.css").toExternalForm());
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneStartPage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}