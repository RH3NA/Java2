package com.javafxdemo;


import com.javafxdemo.controller.*;
import com.javafxdemo.models.*;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Session {

    private final static Session instance = new Session(new LoanController(), new SearchController(), new StartpageController(),
                                            new StartpageLoggedInController(), new UserController(), new LoanReturnController(), new OverviewController(), new AdminController(), new ReusableButtonController(), new UpdateController());
                                            new StartpageLoggedInController(), new UserController(), new LoanReturnController(), new OverviewController(), new AdminController(), new ReusableButtonController(), new DeleteController());

    public LoanController getLoanController() {
        return loanController;
    }

    public void setLoanController(LoanController loanController) {
        this.loanController = loanController;
    }

    public DeleteController getDeleteController() {return deleteController;}

    public void setDeleteController(DeleteController deleteController) {this.deleteController = deleteController; }


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

    public UserController getUserController() {
        return userController;
    }

    public ReusableButtonController getReusableButtonController() {
        return reusableButtonController;
    }

    public void setReusableButtonController(ReusableButtonController reusableButtonController) {
        this.reusableButtonController = reusableButtonController;
    }


    public void setUserController(UserController userController) {
        this.userController = userController;
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
    private UserController userController;
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


    public Session(LoanController loanController, SearchController searchController, StartpageController startpageController,
                   StartpageLoggedInController startpageLoggedInController, UserController userController, LoanReturnController loanReturnController, OverviewController overviewController,
                   AdminController adminController, ReusableButtonController reusableButtonController, DeleteController deleteController) {
        this.loanController = loanController;
        this.searchController = searchController;
        this.startpageController = startpageController;
        this.startpageLoggedInController = startpageLoggedInController;
        this.userController = userController;
        this.loanReturnController = loanReturnController;
        this.overviewController = overviewController;
        this.adminController = adminController;
        this.reusableButtonController = reusableButtonController;
        this.updateController = updateController;
    }

    public static Session getInstance() { //getter for our instance
        return instance;
    }


    private int idUser; //idk why these variables are greyed out still..
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;
    private int userType;
    private Boolean currentlyLoggedIn;
    private Boolean hasTooManyLoans;


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


    private ItemModel currentSearch = new ItemModel(idItem, numberInStock, title, isbn, publisher, totalStock);

    public ItemModel getCurrentSearch() {
        return currentSearch;
    }

    public void setCurrentSearch(ItemModel currentSearch) {
        this.currentSearch = currentSearch;
    }

    public ItemModel getCurrentAdd() {
        return currentAdd;
    }

    public void setCurrentAdd(ItemModel currentAdd) {
        this.currentAdd = currentAdd;
    }

    private ItemModel currentAdd = new ItemModel(idItem, numberInStock, title, isbn, publisher, totalStock);

    private int idLoan;
    private Timestamp loanDate;
    private Timestamp expiryDate;

    private LoanModel currentLoan = new LoanModel(idUser, idBarcode, loanDate, expiryDate);

    public LoanModel getCurrentLoan() {
        return currentLoan;
    }

    public void setCurrentLoan(LoanModel currentLoan) {
        this.currentLoan = currentLoan;
    }

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
    private final InventoryModel currentInventory = new InventoryModel(idBarcode, items_idItems, location_idLocation, category, available);

    public InventoryModel getcurrentInventory() {
        return currentInventory;
    }

    private final ItemHasCreatorModel currentItemHasCreator = new ItemHasCreatorModel(firstName,lastName);
    public  ItemHasCreatorModel getCurrentItemHasCreator() {return currentItemHasCreator;}

}




    /*public static void initializeSession() {
        Session session = new Session(new LoanController(), new SearchController(), new StartpageController(),
                new StartpageLoggedInController(), new UserController());
    }*/



   /*private String loggedInIdUser;
    public String getLoggedInIdUser() {
        return loggedInIdUser;
    }
    public void setLoggedInIdUser(String loggedInIdUser) {
        this.loggedInIdUser = loggedInIdUser;
    }
    private String loggedInUserType;
    public String getLoggedInUserType() {
        return loggedInUserType;
    }

    public void setLoggedInUserType(String loggedInUserType) {
        this.loggedInUserType = loggedInUserType;
    }*/