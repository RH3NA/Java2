package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;
import com.javafxdemo.models.InventoryModel;
import com.javafxdemo.models.ItemModel;
import com.javafxdemo.models.LoanModel;
import com.javafxdemo.models.UserModel;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.javafxdemo.models.ItemModel.searchAllModelObservableList;

//this controller controls everything related to the search function and its view
public class  SearchController extends ReusableButtonController implements Initializable {
    @FXML
    public TextField searchTextInputField;
    @FXML
    public TableView<ItemModel> searchResultsTableView;
    @FXML
    public TableColumn<ItemModel, String> idItemColumn;
    @FXML
    public TableColumn<ItemModel, String> titleColumn;
    @FXML
    public TableColumn<ItemModel, String> authorLastNameColumn;
    @FXML
    private TableColumn<ItemModel, String> authorFirstNameColumn;
    @FXML
    private TableColumn<ItemModel, String> publisherColumn;
    @FXML
    private TableColumn<ItemModel, String> isbnColumn;
    @FXML
    private TableColumn<ItemModel, String> categoryColumn;
    @FXML
    private TableColumn<ItemModel, String> numberInStockColumn;
    @FXML
    private TableColumn<ItemModel, String> idBarcodeColumn;
	@FXML
    private TableColumn<ItemModel, String> totalStockColumn;
    @FXML
    private Label errorLabel;
    @FXML
    Button loginRedirectButton;

    private Boolean isAvailableInStock;
    private Boolean isReference;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
		Session.getInstance().setCurrentScene("Search");
		// gets the database search item model data and displays it in the search table view.
	    try {
		    ItemModel.getSearchItemsDB();
            idItemColumn.setCellValueFactory(new PropertyValueFactory<>("idItem"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorLastName"));
            authorFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorFirstName"));
            publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
            isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            numberInStockColumn.setCellValueFactory(new PropertyValueFactory<>("numberInStock"));
            idBarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("idBarcode"));
            totalStockColumn.setCellValueFactory(new PropertyValueFactory<>("totalStock"));

            searchResultsTableView.setItems(searchAllModelObservableList);
		    System.out.println(searchAllModelObservableList);

        } catch (SQLException e) {
	        throw new RuntimeException(e);
        }
			//create a filtered list so a dynamic search can be done.
			FilteredList<ItemModel> filteredList = new FilteredList<>(searchAllModelObservableList, b -> true);

			searchTextInputField.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(ItemModel -> {

				String searchText = newValue.toLowerCase();

				if (String.valueOf(ItemModel.getIdItem()).toLowerCase().contains(searchText)) {
					return true;
				} else if (ItemModel.getTitle().toLowerCase().contains(searchText)) {
					return true;
				} else if (ItemModel.getAuthorLastName().toLowerCase().contains(searchText)) {
					return true;
				} else if (ItemModel.getAuthorFirstName().toLowerCase().contains(searchText)) {
					return true;
				} else if (ItemModel.getPublisher().toLowerCase().contains(searchText)) {
					return true;
				} else if (ItemModel.getIsbn().toLowerCase().contains(searchText)) {
					return true;
				} else if (ItemModel.getCategory().toLowerCase().contains(searchText)) {
					return true;
				} else if (String.valueOf(ItemModel.getNumberInStock()).toLowerCase().contains(searchText)) {
					return true;
			    } else if (String.valueOf(ItemModel.getIdBarcode()).toLowerCase().contains(searchText)) {
					return true;
				} else return String.valueOf(ItemModel.getTotalStock()).toLowerCase().contains(searchText);
			}));

        SortedList<ItemModel> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(searchResultsTableView.comparatorProperty());

        searchResultsTableView.setItems(sortedList);
		searchResultsTableView.setOnMouseClicked((MouseEvent event) -> { //Flags the item the mouse is clicked on.
		    if (event.getClickCount() >= 1) {
			    onEdit();
		    }
	    });
    }

	public void onEdit() {
		// check the table's selected item and get selected item
		if (searchResultsTableView.getSelectionModel().getSelectedItem() != null) {
			ItemModel selectedItem = searchResultsTableView.getSelectionModel().getSelectedItem();
			System.out.println(selectedItem);
			idItemColumn.setText(String.valueOf(selectedItem.getIdItem()));
			numberInStockColumn.setText(String.valueOf(selectedItem.getNumberInStock()));
			titleColumn.setText(selectedItem.getTitle());
			isbnColumn.setText(selectedItem.getIsbn());
			//totalStockColumn.setText(selectedItem.getIsbn());
			publisherColumn.setText(selectedItem.getPublisher());
			categoryColumn.setText(selectedItem.getCategory());
			idBarcodeColumn.setText(String.valueOf(selectedItem.getIdBarcode()));
			authorLastNameColumn.setText(selectedItem.getAuthorLastName());
			authorFirstNameColumn.setText(selectedItem.getAuthorFirstName());
			totalStockColumn.setText(String.valueOf(selectedItem.getTotalStock()));

			Session.getInstance().setCurrentSearch(selectedItem); //sets the selected item in the current search instance
			System.out.println(selectedItem);
		}
	}

    public void setSceneSearch() throws IOException {
        Scene sceneSearch = new Scene(FXMLLoader.load(LibraryApplication.class.getResource("fxml/search-view.fxml")));
        Stage stage = (Stage) LibraryApplication.getStage().getScene().getWindow();
        stage.setScene(sceneSearch);
        stage.show();
    }

    public Boolean getIsReference() {
		return isReference; }
    public void setIsReference(Boolean isReference) {
		this.isReference = isReference; }

    public Boolean getAvailableInStock() {
		return isAvailableInStock;}
    public void setAvailableInStock(Boolean availableInStock) {
		isAvailableInStock = availableInStock;}

    public void onSearchResultsLoanButton(ActionEvent a) throws IOException, SQLException {
        errorLabel.setText("");
        UserModel currentUser = Session.getInstance().getCurrentUser(); //simplifying the long calls
	    ItemModel currentSearch = Session.getInstance().getCurrentSearch();
	    System.out.println((Session.getInstance().getCurrentSearch()));
		//will not allow reference material to be borrowed
        if ((Session.getInstance().getCurrentSearch().getCategory().toLowerCase().matches("reference")) && (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE)) {
	        System.out.println(Session.getInstance().getCurrentSearch().getCategory().toLowerCase());
	        System.out.println(currentUser.getCurrentlyLoggedIn());
			System.out.println("You may not borrow reference literature.");
            errorLabel.setText("You may not borrow reference literature.");
        }
		//notifies if there are currently no items in stock
        else if ((Session.getInstance().getCurrentSearch().getNumberInStock() == 0 ) && (currentUser.getCurrentlyLoggedIn() == Boolean.TRUE)) {
	        System.out.println("No items available in stock." + (currentSearch.getNumberInStock()));
			errorLabel.setText("No items available in stock.");
        }
		//will not allow a loan to take place unless a user is logged in
        else if (currentUser.getIdUser() == 0) {
	        System.out.println(currentUser.getIdUser());
            System.out.println("You need to be logged in to proceed.");
            errorLabel.setText("You need to be logged in to proceed.");
            loginRedirectButton.setVisible(true); //prompting a log in
        }
		//takes user to loan page
		else if ((currentUser.getCurrentlyLoggedIn() == Boolean.TRUE) && (ItemModel.isbnExists(currentSearch.getIsbn()) == Boolean.TRUE) && ((Session.getInstance().getCurrentSearch().getCategory().equalsIgnoreCase("reference")) == Boolean.FALSE) && (Session.getInstance().getCurrentSearch().getNumberInStock() >= 0 )) { //some error handling
		    System.out.println(currentUser.getCurrentlyLoggedIn());
		    System.out.println(currentSearch.getIsbn());
		    System.out.println(Session.getInstance().getCurrentSearch().getCategory());
		    System.out.println(Session.getInstance().getCurrentSearch().getNumberInStock());
		    InventoryModel.getInventoryDB(); //refreshing the inventory list
		    Session.getInstance().setCurrentLoan(new LoanModel(currentUser.getIdUser(), InventoryModel.availableBarcode(currentSearch.getIdItem()), null, null));
		    System.out.println(Session.getInstance().getCurrentLoan());
		    Session.getInstance().getLoanController().setSceneLoan();
	        }
		else {
	        System.out.println("Something went wrong. Try again"); //tags when a different error not meeting above parameters occurs
	        }
    }

    public void onLoginRedirectButtonClick (ActionEvent e) throws IOException {
        LibraryApplication.setSceneStartPage();
    }

    public void onBackButtonClick() throws IOException {
        backMethod(Session.getInstance().getPreviousScene());
    }

    public void onExitButtonClick() {
        exit();
    }
}