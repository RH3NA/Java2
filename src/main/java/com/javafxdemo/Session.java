package com.javafxdemo;


import com.javafxdemo.controller.*;
import com.javafxdemo.models.ItemModel;
import com.javafxdemo.models.LoanModel;
import com.javafxdemo.models.UserModel;

import java.sql.Timestamp;

public class Session {

    private final static Session instance = new Session(new LoanController(), new SearchController(), new StartpageController(),
                                            new StartpageLoggedInController(), new UserController(), new LoanReturnController());

    public LoanController getLoanController() {
        return loanController;
    }

    public void setLoanController(LoanController loanController) {
        this.loanController = loanController;
    }

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


    public Session(LoanController loanController, SearchController searchController, StartpageController startpageController,
                   StartpageLoggedInController startpageLoggedInController, UserController userController, LoanReturnController loanReturnController) {
        this.loanController = loanController;
        this.searchController = searchController;
        this.startpageController = startpageController;
        this.startpageLoggedInController = startpageLoggedInController;
        this.userController = userController;
        this.loanReturnController = loanReturnController;
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
    private int numberInStock;
    private String title;
    private String isbn;
    private String publisher;
    private int totalStock;

    private ItemModel currentSearch = new ItemModel(idItem, numberInStock, title, isbn, publisher, totalStock);

    public ItemModel getCurrentSearch() {
        return currentSearch;
    }

    public void setCurrentSearch(ItemModel currentSearch) {
        this.currentSearch = currentSearch;
    }

    public ItemModel getCurrentUpdate() {
        return currentUpdate;
    }

    public void setCurrentUpdate(ItemModel currentUpdate) {
        this.currentUpdate = currentUpdate;
    }

    private ItemModel currentUpdate = new ItemModel(idItem, numberInStock, title, isbn, publisher, totalStock);

    private int idLoan;
    private int idBarcode;
    private Timestamp loanDate;
    private Timestamp expiryDate;

    private LoanModel currentLoan = new LoanModel(idUser, idBarcode, loanDate, expiryDate);

    public LoanModel getCurrentLoan() {
        return currentLoan;
    }

    public void setCurrentLoan(LoanModel currentLoan) {
        this.currentLoan = currentLoan;
    }


    /*public static void initializeSession() {
        Session session = new Session(new LoanController(), new SearchController(), new StartpageController(),
                new StartpageLoggedInController(), new UserController());
    }*/
}


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