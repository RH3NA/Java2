package com.javafxdemo.models;


import java.security.Timestamp;

public class LoanreturnModel {
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

}
