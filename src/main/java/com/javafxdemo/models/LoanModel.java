package com.javafxdemo.models;


import com.javafxdemo.DBConnection;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LoanModel {
    private int idUser;
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

    /*public static void getLoansDB() throws SQLException { //added a method to get and store the users from the DB in a static arraylist,
        // the only issue rn is that i didnt set any limits so if you run this method twice,
        // there will be duplicates.. easy to fix probs :)
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Loan";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            LoanModel loan = new LoanModel(rst.getInt("idLoan"), rst.getInt("User_idUser"), rst.getInt("Inventory_idBarcode"), rst.getTimestamp("loanDate"), rst.getTimestamp("expiryDate"));
            loans.add(loan);
        }


    }
    */

}
