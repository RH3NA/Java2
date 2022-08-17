package com.javafxdemo.models;


import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

//this class is based on the LoanReturn table in the database
public class LoanreturnModel extends DBConnection {

    public LoanreturnModel (int loanIdLoan) { //constructor
        this.loanIdLoan = loanIdLoan;
    }
    private int loanIdLoan;
    private Timestamp returnDate;


    public int getLoanIdLoan() {
        return loanIdLoan;
    }

    public void setLoanIdLoan(int loanIdLoan) {
        this.loanIdLoan = loanIdLoan;
    }


    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public static void returnLoan(int idLoan) { //method to return a single loan
        try {
            DBConnection connectNow = new DBConnection();
            Connection conn = connectNow.getConnection();

            String query = " insert into Loanreturn (Loan_idLoan, returnDate)"
                    + " values (?, curdate())";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idLoan);
            preparedStmt.execute();
            conn.close();
            System.out.println("Success!");
            Session.getInstance().getCurrentUser().setHasTooManyLoans(Boolean.FALSE); //makes the user able to loan again if they were previously blocked
        } catch (SQLException e) {
            System.out.println("Something went wrong.");
        }

    }
    }


