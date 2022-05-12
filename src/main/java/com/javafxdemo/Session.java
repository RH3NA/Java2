package com.javafxdemo;


import com.javafxdemo.controller.*;
import com.javafxdemo.models.ItemModel;
import com.javafxdemo.models.LoanModel;
import com.javafxdemo.models.UserModel;

import java.sql.Timestamp;

public class Session {

    private final static Session instance = new Session(new LoanController(), new SearchController(), new StartpageController(),
                                            new StartpageLoggedInController(), new UserController());

    public LoanController loanController;
    public SearchController searchController;
    public StartpageController startpageController;
    public StartpageLoggedInController startpageLoggedInController;
    public UserController userController;

    public Session(LoanController loanController, SearchController searchController, StartpageController startpageController,
                   StartpageLoggedInController startpageLoggedInController, UserController userController) {
        this.loanController = loanController;
        this.searchController = searchController;
        this.startpageController = startpageController;
        this.startpageLoggedInController = startpageLoggedInController;
        this.userController = userController;
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
    private UserModel currentUser = new UserModel(idUser, lastName, firstName, phoneNumber, email, userType, currentlyLoggedIn); //constructor to create a constructed object (currentUser instance) from our usermodel constructor

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