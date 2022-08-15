package com.javafxdemo;


import com.javafxdemo.controller.*;
import com.javafxdemo.models.*;

import java.sql.Timestamp;

//this is our context class that handles what is connected to for example the current session, the current user, the current loan, the current add or the current search,
//so that we can easily reuse stored values in for example other classes as well as keeping track of what's going on right now in this session of the application.

public class Session {

    //creates an object of the Session class
    private final static Session instance = new Session(new LoanController(), new SearchController(),
            new StartpageController(), new StartpageLoggedInController(),
            new LoanReturnController(), new OverviewController(), new AdminController(),
            new ReusableButtonController(), new UpdateController(), new DeleteController(), new AddController());

    public LoanController getLoanController() {
        return loanController;
    }
    public void setLoanController(LoanController loanController) {
        this.loanController = loanController;
    }

    public DeleteController getDeleteController() { return deleteController; }

    public void setDeleteController(DeleteController deleteController) { this.deleteController = deleteController; }


    public SearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    public StartpageController getStartpageController() {
        return startpageController;
    }

    public void setStartpageController(StartpageController startpageController) {
        this.startpageController = startpageController;
    }

    public StartpageLoggedInController getStartpageLoggedInController() {
        return startpageLoggedInController;
    }

    public void setStartpageLoggedInController(StartpageLoggedInController startpageLoggedInController) {
        this.startpageLoggedInController = startpageLoggedInController;
    }



    public ReusableButtonController getReusableButtonController() {
        return reusableButtonController;
    }

    public void setReusableButtonController(ReusableButtonController reusableButtonController) {
        this.reusableButtonController = reusableButtonController;
    }


    public LoanReturnController getLoanReturnController() {
        return loanReturnController;
    }

    public void setLoanReturnController(LoanReturnController loanReturnController) {
        this.loanReturnController = loanReturnController;
    }

    private LoanController loanController;
    private SearchController searchController;
    private StartpageController startpageController;
    private StartpageLoggedInController startpageLoggedInController;
    private LoanReturnController loanReturnController;

    public OverviewController getOverviewController() {
        return overviewController;
    }

    public void setOverviewController(OverviewController overviewController) {
        this.overviewController = overviewController;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    private OverviewController overviewController;
    private AdminController adminController;
    private ReusableButtonController reusableButtonController;
    private DeleteController deleteController;

    public UpdateController getUpdateController() {
        return updateController;
    }

    public void setUpdateController(UpdateController updateController) {
        this.updateController = updateController;
    }

    private UpdateController updateController;

//constructor for the Session class
    public Session(LoanController loanController, SearchController searchController, StartpageController startpageController,
                   StartpageLoggedInController startpageLoggedInController, LoanReturnController loanReturnController, OverviewController overviewController,
                   AdminController adminController, ReusableButtonController reusableButtonController, UpdateController updateController, DeleteController deleteController, AddController addController) {
        this.loanController = loanController;
        this.searchController = searchController;
        this.startpageController = startpageController;
        this.startpageLoggedInController = startpageLoggedInController;
        this.loanReturnController = loanReturnController;
        this.overviewController = overviewController;
        this.adminController = adminController;
        this.reusableButtonController = reusableButtonController;
        this.updateController = updateController;
        this.deleteController = deleteController;
        this.addController = addController;
    }

    public AddController getAddController() {
        return addController;
    }

    public void setAddController(AddController addController) {
        this.addController = addController;
    }

    private AddController addController;

    public static Session getInstance() { //getter for our instance
        return instance;
    } //getter to get the instance


    private int idUser;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;
    private int userType;
    private Boolean currentlyLoggedIn;
    private Boolean hasTooManyLoans;

//creating an object of UserModel as the current user
    private UserModel currentUser = new UserModel(idUser, lastName, firstName, phoneNumber, email, userType, currentlyLoggedIn, hasTooManyLoans); //constructor to create a constructed object (currentUser instance) from our usermodel constructor

    public UserModel getCurrentUser() { //getter to get the current user
        return currentUser;
    }

    public void setCurrentUser(UserModel currentUser) { //setter to set the current user
        this.currentUser = currentUser;
    }

    private int idItem;
    private int idBarcode;
    private int numberInStock;
    private String title;
    private String isbn;
    private String publisher;
    private int totalStock;
    private String category;
    private String authorLastName;
    private String authorFirstName;

    //creating an object of ItemModel as the current search
    //private ItemModel currentSearch = new ItemModel(idItem, numberInStock, title, isbn, totalStock, publisher);

    private ItemModel currentSearch = new ItemModel(idItem, numberInStock, title, isbn, totalStock, publisher, category, idBarcode, authorLastName, authorFirstName, totalStock);

    public ItemModel getCurrentSearch() {
        return currentSearch;
    }

    public ItemModel setCurrentSearch(ItemModel currentSearch) {
        this.currentSearch = currentSearch;
        return currentSearch;
    }

    public ItemModel getCurrentAdd() {
        return currentAdd;
    }

    public void setCurrentAdd(ItemModel currentAdd) {
        this.currentAdd = currentAdd;
    }

    //creating an object of ItemModel as the current add
    private ItemModel currentAdd = new ItemModel(idItem, numberInStock, title, isbn, totalStock, publisher);

    private int idLoan;
    private Timestamp loanDate;
    private Timestamp expiryDate;

    //creating an object of LoanModel as the current loan
    private LoanModel currentLoan = new LoanModel(idUser, idBarcode, loanDate, expiryDate);

    public LoanModel getCurrentLoan() {
        return currentLoan;
    }

    public void setCurrentLoan(LoanModel currentLoan) {
        this.currentLoan = currentLoan;
    }

    //setters and getters for the previous and current scenes
    public String getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(String previousScene) {
        this.previousScene = previousScene;
    }

    private String previousScene;

    public String getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(String currentScene) {
        this.currentScene = currentScene;
    }

    private String currentScene;

    private int items_idItems;
    private int location_idLocation;

    private Boolean available;

    //creating a new object of InventoryModel as the current inventory
    private final InventoryModel currentInventory = new InventoryModel(idBarcode, items_idItems, location_idLocation, category, available);

    public InventoryModel getcurrentInventory() {
        return currentInventory;
    }

    private int Item_idItem;

    //creating a new object of ItemHasCreatorModel as the current item has creator
    private final ItemHasCreatorModel currentItemHasCreator = new ItemHasCreatorModel(Item_idItem, firstName, lastName);

    public ItemHasCreatorModel getCurrentItemHasCreator() {
        return currentItemHasCreator;
    }

}