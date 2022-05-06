module com.example.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.java;

    opens com.javafxdemo to javafx.fxml;
    exports com.javafxdemo;
    exports com.javafxdemo.controller;
    opens com.javafxdemo.controller to javafx.fxml;
}