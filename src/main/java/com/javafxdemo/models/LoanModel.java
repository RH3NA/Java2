package com.javafxdemo.models;


import java.security.Timestamp;

public class LoanModel {
    private int idLoan;
    private Timestamp loanDate;
    private Timestamp expiryDate;


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


}
