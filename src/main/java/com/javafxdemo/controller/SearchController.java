package com.javafxdemo.controller;

import com.javafxdemo.*;
import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.ItemModel;
import com.javafxdemo.models.LoanModel;
import com.javafxdemo.models.UserModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;



//this controller controls everything related to the search function and its view
public class  SearchController extends InheritedMethods implements Initializable, ReusableInterface {

    @FXML
    public TextField searchTextInputField;
    @FXML
    public Button searchForResultButton;
    @FXML
    private Label searchResultsArea;
    @FXML
    private Label isbnLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label numberInStockLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Button searchResultsLoanButton;
    @FXML
    private Label errorLabel;
    @FXML
    Button loginRedirectButton;

    private Boolean isAvailableInStock;
    private Boolean isReference;
    @FXML
    private ChoiceBox<String> choiceBox;
    private final String[] choices = {"Title", "ISBN", "Publisher"}; //setting the choices in the choicebox
    @FXML
    private TableView tableView;
    @FXML
    private final ObservableList<String> choiceList = FXCollections.observableArrayList(choices); //setting the choicelist into an observable list

    private ObservableList<ObservableList> data;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.getInstance().setCurrentScene("Search");
        try {
            ItemModel.getItemsDB();
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

    public void setSceneSearch() throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

    public Boolean getIsReference() {
        return isReference;
    }

    public void setIsReference(Boolean isReference) {
        this.isReference = isReference;
    }

    public Boolean getAvailableInStock() {
        return isAvailableInStock;
    }

    public void setAvailableInStock(Boolean availableInStock) {
        isAvailableInStock = availableInStock;
    }

    public void onSearchForResultButton(ActionEvent a) throws Exception {  // throwing out exceptions so the system doesn't crash & also ensure connection and statement closes
        if (!searchTextInputField.getText().isBlank()) {
            if ((choiceBox.getSelectionModel().getSelectedIndex()) == 0) {
                buildSearch("SELECT DISTINCT idItem, numberInStock, title, isbn, totalStock, publisher, category, firstName, lastName\n" +
                        "FROM Item\n" +
                        "Inner join Inventory on Item.idItem = Inventory.Items_idItems\n" +
                        "Inner join Item_has_creator ON Item.idItem = Item_has_creator.Item_idItem\n" +
                        "WHERE title LIKE CONCAT('%','" + searchTextInputField.getText() + "', '%');");
            }
        /*String author;
        String category;
        errorLabel.setText("");
        DBConnection connectNow = new DBConnection();       //This initiates a new connection instance of 'conn' using the DBConnection Class
        Connection conn = connectNow.getConnection(); // THis opens a 'conn' connection using the DBConnection
        if (choiceBox.getValue().toString().equalsIgnoreCase("Title")) {
            buildSearch("Title");
            searchTextInputField.setText("Enter the title you wish to search for.");
            if (!searchTextInputField.getText().isBlank()) {
                CallableStatement statement = conn.prepareCall("{CALL NewTitleSearch(?)}");
                //CallableStatement statement = conn.prepareCall("{CALL SearchByIsbn(?)}");  //makes a statement call ready to execute the procedure in database

                statement.setString(1, searchTextInputField.getText()); // gets input text from JavaFX 'search bar'
                boolean hadResults = statement.execute(); // executes sql query

                while (hadResults) {
                    ResultSet resultSet = statement.getResultSet(); // will run the query until all results collected
                    while (resultSet.next()) {  //outputs the query result back to the search results area (label) on search page
                        Session.getInstance().setCurrentSearch(new ItemModel(resultSet.getInt("idItem"), resultSet.getInt("numberInStock"), resultSet.getString("title"), resultSet.getString("isbn"), resultSet.getInt("totalStock"), resultSet.getString("publisher")));
                        ItemModel currentSearch = Session.getInstance().getCurrentSearch();
                        author = "" + ((resultSet.getString("firstName")) + " " + (resultSet.getString("lastName")));
                        category = resultSet.getString("category");
                        isbnLabel.setText("ISBN: " + currentSearch.getIsbn());
                        titleLabel.setText("Title: " + currentSearch.getTitle());
                        publisherLabel.setText("Publisher: " + currentSearch.getPublisher());
                        categoryLabel.setText("Category: " + category);
                        authorLabel.setText("Author: " + author);

                        if (category.equalsIgnoreCase("Reference")) {
                            setIsReference(Boolean.TRUE);
                            System.out.println("Not available to loan");
                        } else {
                            setIsReference(Boolean.FALSE);
                        }
                    }
                    hadResults = statement.getMoreResults(); // will loop until no more results available
                }

                statement.close(); // closes query
                System.out.println(Session.getInstance().getCurrentSearch());
            }
        }*/
        }
    }


    public void onSearchResultsLoanButton(ActionEvent a) throws IOException, SQLException {
        errorLabel.setText("");
        UserModel currentUser = Session.getInstance().getCurrentUser(); //simplifying the long calls
        TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        ObservableList<String> selectedItems = selectionModel.getSelectedItems();
        System.out.println(selectedItems);
        String splitter = String.valueOf(selectedItems);
        String splitted = splitter.substring(0, splitter.indexOf(","));
        System.out.println(splitter);
        String newIdItem = splitted.replaceAll("[\\[\\]]", "");
        System.out.println(newIdItem);

        for (int i = 0; ItemModel.items.size() > i; i++) {
            if (ItemModel.items.get(i).getIdItem() == Integer.parseInt(newIdItem)) {
                Session.getInstance().setCurrentSearch(new ItemModel(ItemModel.items.get(i).getIdItem(), ItemModel.items.get(i).getNumberInStock(), ItemModel.items.get(i).getTitle(),
                        ItemModel.items.get(i).getIsbn(), ItemModel.items.get(i).getTotalStock(), ItemModel.items.get(i).getPublisher()));
            }
        }

        ItemModel currentSearch = Session.getInstance().getCurrentSearch();
        System.out.println(Session.getInstance().getCurrentSearch());
        checkReference(Session.getInstance().getCurrentSearch().getIdItem());
        if ((getIsReference() == Boolean.TRUE) && (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE)) {
            System.out.println("You may not borrow reference literature.");
            errorLabel.setText("You may not borrow reference literature.");
            System.out.println(currentUser.getCurrentlyLoggedIn());
        }

        int availableBarcode = InventoryModel.availableBarcode(Session.getInstance().getCurrentSearch().getIdItem());
        Boolean barcode = InventoryModel.checkAvailableBarcode(availableBarcode);
        setAvailableInStock(barcode);

        if (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE && getIsReference() == Boolean.FALSE && getAvailableInStock() == Boolean.TRUE) { //some error handling
            InventoryModel.getInventoryDB(); //refreshing the inventory list
            Session.getInstance().setCurrentLoan(new LoanModel(currentUser.getIdUser(), InventoryModel.availableBarcode(currentSearch.getIdItem()), null, null));
            System.out.println(Session.getInstance().getCurrentLoan());
            Session.getInstance().getLoanController().setSceneLoan();
        }
        if ((getAvailableInStock() == Boolean.FALSE) && (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE)) {
            errorLabel.setText("No items available in stock.");
        } else if (currentUser.getIdUser() == 0) {
            System.out.println("You need to be logged in to proceed.");
            errorLabel.setText("You need to be logged in to proceed.");
            loginRedirectButton.setVisible(true); //prompting a log in
        }
    }

    public void onLoginRedirectButtonClick(ActionEvent e) throws IOException {
        LibraryApplication.setSceneStartPage();
    }

    public void onBackButtonClick() throws IOException {
        backMethod();
    }

    @Override
    public void backMethod() throws IOException {
        if (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == Boolean.TRUE) {
            Session.getInstance().getStartpageLoggedInController().setSceneStartpageLoggedIn();
        } else {
            LibraryApplication.setSceneStartPage();

        }
    }

    public void buildSearch(String string) {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableView.refresh();
        data = FXCollections.observableArrayList();

        try {
            DBConnection connectNow = new DBConnection();
            Connection conn = connectNow.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(string); //preparing the query based on the choice selected

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableView.getColumns().addAll(col);
                System.out.println("Column [" + i + "] "); //building the columns
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row); //building the rows
                data.add(row);

            }

            tableView.setItems(data); //setting the items
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void onChoiceBoxSelection(ChoiceBox<String> choiceBox) throws SQLException { //building the page based on what option you select with a select query that goes into the build data method
        if ((choiceBox.getSelectionModel().getSelectedIndex()) == 0) {
            if (!searchTextInputField.getText().isBlank()) {
                searchTextInputField.setText("Enter your searchword.");
            }
        /*if ((choiceBox.getSelectionModel().getSelectedIndex()) == 1) {
            buildSearch("Select * From User");
        }
        if ((choiceBox.getSelectionModel().getSelectedIndex()) == 2) {
            buildSearch("Select * From Item");
        }
        if ((choiceBox.getSelectionModel().getSelectedIndex()) == 3) {
            buildSearch("CALL ReminderTest()");
        }*/
        }
    }

    public void checkReference(int idItem) throws SQLException {
        setIsReference(false);
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();

        //builds the query based on what the input parameters are
        String query = "Select category\n" +
                "From item\n" +
                "Inner Join Inventory on Item.idItem = Inventory.Items_idItems\n" +
                "Where idItem = " + idItem;

        System.out.println(query); //prints the query in the console to check for any SQL syntax error
        ResultSet rst;
        rst = stm.executeQuery(query);
        while (rst.next()) {
            String category = rst.getString("category");
            if (category.equalsIgnoreCase("Reference")) {
                setIsReference(Boolean.TRUE);
            }
        }
    }
}