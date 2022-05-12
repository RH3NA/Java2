package com.javafxdemo.models;


import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;

public class LoanModel {
    private int idUser;

    public int getIdBarcode() {
        return idBarcode;
    }

    public void setIdBarcode(int idBarcode) {
        this.idBarcode = idBarcode;
    }

    private int idBarcode;
    private int idLoan;
    private Timestamp loanDate;
    private Timestamp expiryDate;
    public static ArrayList<LoanModel> loans = new ArrayList<>();

    public LoanModel(int idUser, int idBarcode, Timestamp loanDate, Timestamp expiryDate) {
        this.idBarcode = idBarcode;
        this.loanDate = loanDate;
        this.expiryDate = expiryDate;
        this.idUser = idUser;
    }

    public LoanModel(int idUser, int idBarcode, Timestamp loanDate, Timestamp expiryDate, int idLoan) {
        this.idBarcode = idBarcode;
        this.loanDate = loanDate;
        this.expiryDate = expiryDate;
        this.idUser = idUser;
        this.idLoan = idLoan;
    }

    @Override
    public String toString() //overriding toString method so we get the values instead of the hashcodes from the arraylist prints
    {
        return "idUser = " + this.idUser +
                " idBarCode = " + this.idBarcode +
                " loanDate = " + this.loanDate +
                " expiryDate = " + this.expiryDate;
    }


    public int getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(int idLoan) {
        this.idLoan = idLoan;
    }


    public Timestamp getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Timestamp loanDate) {
        this.loanDate = loanDate;
    }


    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public static void getLoansDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist,
        // the only issue rn is that i didnt set any limits so if you run this method twice,
        // there will be duplicates.. easy to fix probs :)
        loans.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Loan";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            LoanModel loan = new LoanModel(rst.getInt("User_idUser"), rst.getInt("Inventory_idBarcode"), rst.getTimestamp("loanDate"), rst.getTimestamp("expiryDate"), rst.getInt("idLoan"));
            loans.add(loan);
        }


    }

    public static void insertLoan(int idLoan, int idUser, int idBarcode, Timestamp loanDate, Timestamp expiryDate) throws SQLException {
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();

        String query = " insert into Loan (idLoan, User_idUser, Inventory_idBarcode, loanDate, expiryDate)"
                + " values (?, ?, ? , ? , ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, idLoan);
        preparedStmt.setInt(2, idUser);
        preparedStmt.setInt(3, idBarcode);
        preparedStmt.setTimestamp(4, null);
        preparedStmt.setTimestamp(5, null);
        preparedStmt.execute();


        conn.close();

    }
}





