package com.javafxdemo.controller;

import com.javafxdemo.DBConnection;
import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
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
import java.util.ResourceBundle;


public class  SearchController extends ReusableButtonController implements Initializable {

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


 /*    @FXML
    private Button searchButton;

   public void setSceneSearch() throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) searchButton.getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
        Session.getInstance().setPreviousScene("StartpageLoggedIn");
    }*/

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session.getInstance().setCurrentScene("Search");
    }

    public void setSceneSearch() throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

    public Boolean getIsReference() { return isReference; }
    public void setIsReference(Boolean isReference) { this.isReference = isReference; }

    public Boolean getAvailableInStock() { return isAvailableInStock;}
    public void setAvailableInStock(Boolean availableInStock) { isAvailableInStock = availableInStock;}

    public void onSearchForResultButton(ActionEvent a) throws Exception {  // throwing out exceptions so the system doesn't crash & also ensure connection and statement closes
        String author;
        String category;
        errorLabel.setText("");
        DBConnection connectNow = new DBConnection();       //This initiates a new connection instance of 'conn' using the DBConnection Class
        Connection conn = connectNow.getConnection(); // THis opens a 'conn' connection using the DBConnection

        CallableStatement statement = conn.prepareCall("{CALL SearchByIsbn(?)}");  //makes a statement call ready to execute the procedure in database

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
                numberInStockLabel.setText("No. in stock: " + currentSearch.getNumberInStock());
                categoryLabel.setText("Category: " + category);
                authorLabel.setText("Author: " + author);

                if (category.equalsIgnoreCase("Reference")) {
                    setIsReference(Boolean.TRUE);
                    System.out.println("Not available to loan");
                } else {
                    setIsReference(Boolean.FALSE);
                }
                if (Session.getInstance().getCurrentSearch().getNumberInStock() == 0) {
                    setAvailableInStock(Boolean.FALSE);
                } else {
                    setAvailableInStock(Boolean.TRUE);
                }
            }
                hadResults = statement.getMoreResults(); // will loop until no more results available
            }
            statement.close(); // closes query
            System.out.println(Session.getInstance().getCurrentSearch());
        }

    public void onSearchResultsLoanButton(ActionEvent a) throws IOException, SQLException {
        errorLabel.setText("");
        UserModel currentUser = Session.getInstance().getCurrentUser();
        ItemModel currentSearch = Session.getInstance().getCurrentSearch();
        if ((currentUser.getCurrentlyLoggedIn() == Boolean.TRUE) && (ItemModel.isbnExists(currentSearch.getIsbn()) == Boolean.TRUE) && (getIsReference() == Boolean.FALSE) && (getAvailableInStock() == Boolean.TRUE)) {
            InventoryModel.getInventoryDB();
            Session.getInstance().setCurrentLoan(new LoanModel(currentUser.getIdUser(), InventoryModel.availableBarcode(currentSearch.getIdItem()), null, null));
            System.out.println(Session.getInstance().getCurrentLoan());
            Session.getInstance().getLoanController().setSceneLoan();
        }
        if ((getIsReference() == Boolean.TRUE) && (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE)) {
            System.out.println("You may not borrow reference literature.");
            errorLabel.setText("You may not borrow reference literature.");
            System.out.println(currentUser.getCurrentlyLoggedIn());
        }
        if ((getAvailableInStock() == Boolean.FALSE) && (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE)) {
            errorLabel.setText("No items available in stock.");
        }
        else if (currentUser.getIdUser() == 0) {
            System.out.println("You need to be logged in to proceed.");
            errorLabel.setText("You need to be logged in to proceed.");
            loginRedirectButton.setVisible(true);
        }
    }

    public void onLoginRedirectButtonClick (ActionEvent e) throws IOException {
        LibraryApplication.setSceneStartPage();
    }

    public void onBackButtonClick() throws IOException {
        backMethod(Session.getInstance().getPreviousScene());
    }
}