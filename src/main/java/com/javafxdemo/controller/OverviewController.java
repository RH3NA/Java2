package com.javafxdemo.controller;

import com.javafxdemo.*;
import com.javafxdemo.models.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.javafxdemo.models.ItemModel.getItemsDB;
import static com.javafxdemo.models.LoanModel.*;

//this controller controls the overview function and its view
public class OverviewController extends DBConnection implements Initializable, ReusableInterface {
    @FXML
    private TextArea textArea;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> choiceBox;
    private final String[] choices = {"Overview loans", "Overview users", "Overview items", "Overview overdue loans"}; //setting the choices in the choicebox
    @FXML
    private TableView tableView;
    @FXML
    private final ObservableList<String> choiceList = FXCollections.observableArrayList(choices); //setting the choicelist into an observable list

    private ObservableList<ObservableList> data;

    public void setSceneOverview() throws IOException {
        Scene sceneOverview = new Scene(FXMLLoader.load(LibraryApplication.class.getResource(("fxml/overview-view.fxml"))));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneOverview);
        stage.show();
    }

    public void onChoiceBoxSelection(ChoiceBox<String> choiceBox) throws SQLException { //building the page based on what option you select with a select query that goes into the build data method
        if ((choiceBox.getSelectionModel().getSelectedIndex()) == 0) {
            buildData("Select * From Loan");
        }
        if ((choiceBox.getSelectionModel().getSelectedIndex()) == 1) {
            buildData("Select * From User");
        }
        if ((choiceBox.getSelectionModel().getSelectedIndex()) == 2) {
            buildData("Select * From Item");
        }
        if ((choiceBox.getSelectionModel().getSelectedIndex()) == 3) {
            buildData("CALL ReminderTest()");
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //refreshing all lists
        Session.getInstance().setPreviousScene("Admin");
        Session.getInstance().setCurrentScene("Overview");
        try {
            checkOverdueLoansDB();
            Session.getInstance().getCurrentUser().getUsersDB();
            getLoansDBIncludingReturned();
            getItemsDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        choiceBox.setItems(choiceList); //building the choicebox
        choiceBox.setOnAction(e -> {
            try {
                onChoiceBoxSelection(choiceBox);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void buildData(String s){ //building the data based on the choice picked
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableView.refresh();
        data = FXCollections.observableArrayList();
        try{
            Connection conn = super.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(s); //preparing the query based on the choice selected

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableView.getColumns().addAll(col);
                System.out.println("Column ["+i+"] "); //building the columns
            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row ); //building the rows
                data.add(row);

            }

            tableView.setItems(data); //setting the items
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void onBackButtonClick() throws IOException {
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