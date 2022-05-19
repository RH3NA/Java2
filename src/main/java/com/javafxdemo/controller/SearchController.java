package com.javafxdemo.controller;

import com.javafxdemo.Session;
import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.ItemModel;
import com.javafxdemo.models.LoanModel;
import com.javafxdemo.models.UserModel;
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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


public class  SearchController extends ReusableButtonController implements Initializable {

    @FXML
    public TextField searchTextInputField;
    @FXML
    public Button searchForResultButton;
    @FXML
    private Label searchResultsArea;

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNumberInStock() {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock) {
        this.numberInStock = numberInStock;
    }

    private int idItem;
    private int idBarcode;
    private String title;
    private String isbn;
    private String publisher;
    private int numberInStock;
    private String firstName;
    private String lastName;
    private String category;

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    private int totalStock;

    public String getSearchIsbn() {
        return searchIsbn;
    }

    public void setSearchIsbn(String searchIsbn) {
        this.searchIsbn = searchIsbn;
    }

    private String searchIsbn;


    public void onSearchForResultButton(ActionEvent a) throws Exception {  // throwing out exceptions so the system doesn't crash & also ensure connection and statement closes
        DBConnection connectNow = new DBConnection();       //This initiates a new connection instance of 'conn' using the DBConnection Class
        Connection conn = connectNow.getConnection(); // THis opens a 'conn' connection using the DBConnection

        CallableStatement statement = conn.prepareCall("{CALL SearchByIsbn(?)}");  //makes a statement call ready to execute the procedure in database

        statement.setString(1, searchTextInputField.getText()); // gets input text from JavaFX 'search bar'
        setSearchIsbn(searchTextInputField.getText());
        boolean hadResults = statement.execute(); // executes sql query

        while (hadResults) {
            ResultSet resultSet = statement.getResultSet(); // will run the query until all results collected

            while (resultSet.next()) {  //outputs the query result back to the search results area (label) on search page
                setIdItem(resultSet.getInt("idItem"));
                setTitle(resultSet.getString("title"));
                setIsbn(resultSet.getString("isbn"));
                setPublisher(resultSet.getString("publisher"));
                setNumberInStock(resultSet.getInt("numberInStock"));
                setTotalStock(resultSet.getInt(("totalStock")));
                searchResultsArea.setText("ISBN\t\t\t\t\tTitle\t\t\t\t\tPublisher\t\t\tNo. in Stock" +
                        getIsbn() + "   " +

                        getTitle() + "\t\t" +
                        getPublisher() + "\t\t" +
                        //getIdItem() + "\t\t\t\t" +            removed iditem showing here since it's not of any importance to the clients, they only need the barcode and idloan
                        getNumberInStock());

            }
            /* while (resultSet.next()) {  //outputs the query result back to the search results area (label) on search page
                Session.getInstance().setCurrentSearch(new ItemModel(resultSet.getInt("idItem"), resultSet.getInt("numberInStock"), resultSet.getString("title"), resultSet.getString("isbn"), resultSet.getString("publisher"), resultSet.getInt(("totalStock"))));
                ItemModel currentSearch = Session.getInstance().getCurrentSearch();
                author = "" + ((resultSet.getString("firstName")) + " " +(resultSet.getString("lastName")));
                category = resultSet.getString("category");
                isbnLabel.setText("ISBN: " + currentSearch.getIsbn());
                titleLabel.setText("Title: " + currentSearch.getTitle());
                publisherLabel.setText("Publisher: " + currentSearch.getPublisher());
                numberInStockLabel.setText("No. in stock: " + currentSearch.getNumberInStock());
                categoryLabel.setText("Category: " + category);
                authorLabel.setText("Author: " + author);

            } */
            hadResults = statement.getMoreResults(); // will loop until no more results available
        }
        statement.close(); // closes query
        Session.getInstance().setCurrentSearch(new ItemModel(getIdItem(),getNumberInStock(), getTitle(), getIsbn(), getPublisher(), getTotalStock()));
        System.out.println(Session.getInstance().getCurrentSearch());

    }


    private String getLastname() {
        return lastName;
    }

    private String getFirstName() {
        return firstName;
    }

    private String setCategory(String category) {
        return category;
    }

    private int setIdBarcode(int idBarcode) {
        return idBarcode;
    }

    private int getIdBarcode() {
        return idBarcode;

    }

    private String getCategory(){
        return category;
    }

    @FXML
    private Button searchResultsLoanButton;

    public void onSearchResultsLoanButton(ActionEvent a) throws IOException, SQLException {
        UserModel currentUser = Session.getInstance().getCurrentUser();
        ItemModel currentSearch = Session.getInstance().getCurrentSearch();
        if ((currentUser.getCurrentlyLoggedIn() == Boolean.TRUE) && (getSearchIsbn().equalsIgnoreCase(getIsbn()))) { // or         if ((currentUser.getCurrentlyLoggedIn() == Boolean.TRUE) && (ItemModel.isbnExists(currentSearch.getIsbn()) == Boolean.TRUE)) {
            InventoryModel.getInventoryDB();
            Session.getInstance().setCurrentLoan(new LoanModel(currentUser.getIdUser(), InventoryModel.availableBarcode(currentSearch.getIdItem()), null, null));
            System.out.println(Session.getInstance().getCurrentLoan());
            Session.getInstance().getLoanController().setSceneLoan();
        } else {
            System.out.println("You need to be logged in to proceed.");

            // add redirect to startpage + error label with this error text in the actual gui
        }

    }
    @FXML
    private Button backButton;
    public void onBackButtonClick() throws IOException {
        backMethod(Session.getInstance().getPreviousScene());
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.getInstance().setCurrentScene("Search");
    }

    public void setSceneSearch() throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));  // or         Scene sceneSearch = new Scene(FXMLLoader.load(Objects.requireNonNull(LibraryApplication.class.getResource("fxml/search-view.fxml"))));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }
}



        /*DBConnection connectNow = new DBConnection();       //This initiates a new connection instance of 'conn' using the DBConnection Class
        Connection conn = connectNow.getConnection(); // THis opens a 'conn' connection using the DBConnection

        String sqlInsert = "insert into Keyword (keyword) values ('" + searchTextInputField.getText() + "')";
       //String sqlProcedureSearchISBN = "{CALL SearchByIsbn(?)}";
        try {
            Statement statement = conn.createStatement();  // Allocate a 'Statement' object in the connection instance 'conn'

                                                                // INSERT (create) a partial record
                                                                // add a new  string (PARTIAL record, ie not all fields - autofields should self-fill -  into database (ie not all fields) via the searchTextInputField and searchForResultButton (Create/insert)
                                                                // sets textInput parameters (via constructor) to match database (sql) input syntax
            System.out.println("The SQL statement is: " + sqlInsert + "\n");  //This is an echo test for debugging
            int countInserted = statement.executeUpdate(sqlInsert);  // This calculates how many rows have been affected.
            System.out.println(countInserted + " records inserted.\n"); // This OUTPUTS " x records inserted"

                                                                *//* Other code also used by some:
                                                                String sql= "insert into user values('"+searchTextInputField.getText()+"')"; // INSTEAD OF INSERTING INTO TEXTAREA LIKE THIS ‹  searchResultsArea.setText(searchTextInputField.getText());  ›
                                                                Statement statement = conn.createStatement();
                                                                statement.executeUpdate(sql);  *//*

                                                                // Now this will SELECT(find) and show the new data from database into the search-fxml's searchResultsArea TextArea (Read/select))
            String sql = "select * from user; ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                searchTextInputField.setText(resultSet.getString(1));
            }

        } catch (SQLException ex) {
             ex.printStackTrace();
        }*/
