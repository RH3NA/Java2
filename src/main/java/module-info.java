module com.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.java;

    opens com.javafxdemo to javafx.fxml;
    exports com.javafxdemo;
    opens com.javafxdemo.controller to javafx.fxml;
    exports com.javafxdemo.controller;
    opens com.javafxdemo.models to javafx.fxml;
    exports com.javafxdemo.models;

}