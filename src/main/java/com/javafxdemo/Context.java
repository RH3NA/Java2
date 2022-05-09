package com.javafxdemo;


import com.javafxdemo.models.ItemModel;
import com.javafxdemo.models.LoanModel;
import com.javafxdemo.models.UserModel;

import java.sql.Timestamp;

public class Context {

    private final static Context instance = new Context(); //creates new instance of the application

    public Context () {
    }

    public static Context getInstance() { //getter for our instance
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

    private ItemModel currentSearch = new ItemModel(idItem, numberInStock, title, isbn, publisher);
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