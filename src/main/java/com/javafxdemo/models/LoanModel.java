package com.javafxdemo.models;


import com.javafxdemo.DBConnection;
import com.javafxdemo.Session;

import java.sql.*;
import java.util.ArrayList;

//this class is based on the Loan table in the database
public class LoanModel {

    public static ArrayList<LoanModel> getCurrentUserLoans() {
        return currentUserLoans;
    }


    public static void setCurrentUserLoans(ArrayList<LoanModel> currentUserLoans) {
        LoanModel.currentUserLoans = currentUserLoans;
    }

    public static ArrayList<LoanModel> currentUserLoans = new ArrayList<>();
    public static ArrayList<LoanModel> currentUserLatestLoan = new ArrayList<>();
    private final int idUser;

    public int getIdBarcode() {
        return idBarcode;
    }

    public void setIdBarcode(int idBarcode) {
        this.idBarcode = idBarcode;
    }

    private int idBarcode;
    private int idLoan;
    private Timestamp loanDate;
    private final Timestamp expiryDate;
    private int daysOverdue;
    public static ArrayList<LoanModel> loans = new ArrayList<>();
    public static ArrayList<LoanModel> loansIncludingReturned = new ArrayList<>();
    public static ArrayList<LoanModel> overdueLoans = new ArrayList<>();

    public LoanModel(int idUser, int idBarcode, Timestamp loanDate, Timestamp expiryDate) { //constructor
        this.idBarcode = idBarcode;
        this.loanDate = loanDate;
        this.expiryDate = expiryDate;
        this.idUser = idUser;
    }

    public LoanModel(int idUser, int idBarcode, Timestamp loanDate, Timestamp expiryDate, int idLoan) { //second constructor
        this.idBarcode = idBarcode;
        this.loanDate = loanDate;
        this.expiryDate = expiryDate;
        this.idUser = idUser;
        this.idLoan = idLoan;
    }
    public LoanModel (int idLoan, int idUser, int idBarcode, Timestamp loanDate, Timestamp expiryDate, int daysOverdue) { //third constructor
        this.idLoan = idLoan;
        this.idUser = idUser;
        this.idBarcode = idBarcode;
        this.loanDate = loanDate;
        this.expiryDate = expiryDate;
        this.daysOverdue = daysOverdue;
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

    public static void getLoansDB() throws SQLException { //gets all the loans from the database and stores them in an arraylist
        loans.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Loan\n" +
                "Where idLoan NOT IN (Select Loan_idLoan From Loanreturn);";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            LoanModel loan = new LoanModel(rst.getInt("User_idUser"), rst.getInt("Inventory_idBarcode"), rst.getTimestamp("loanDate"), rst.getTimestamp("expiryDate"), rst.getInt("idLoan"));
            loans.add(loan);
        }
        conn.close();
    }
    public static void getLoansDBIncludingReturned() throws SQLException { //gets ALL the loans from the database, including returned loans
        loansIncludingReturned.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select * From Loan;";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            LoanModel loan = new LoanModel(rst.getInt("User_idUser"), rst.getInt("Inventory_idBarcode"), rst.getTimestamp("loanDate"), rst.getTimestamp("expiryDate"), rst.getInt("idLoan"));
            loansIncludingReturned.add(loan);
        }
        conn.close();
    }

    public static void insertLoan(int idLoan, int idUser, int idBarcode, Timestamp loanDate, Timestamp expiryDate) { //inserts a new loan in the database
        try {
            DBConnection connectNow = new DBConnection();

            Connection conn = connectNow.getConnection();

            String query = " insert into Loan (idLoan, User_idUser, Inventory_idBarcode, loanDate, expiryDate)"
                    + " values (?, ?, ? , ? , ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idLoan);
            preparedStmt.setInt(2, idUser);
            preparedStmt.setInt(3, idBarcode);
            preparedStmt.setTimestamp(4, null); //these values are null since we have a trigger creating automatic loandates and expirydates on insert
            preparedStmt.setTimestamp(5, null);
            preparedStmt.execute();

            getLatestLoanDBidUser(idUser);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Session.getInstance().getCurrentUser().setHasTooManyLoans(Boolean.TRUE); //checks if the user has too many loans already
        }
    }


    public static void getLatestLoanDBidUser(int idUser) throws SQLException { //gets the single latest loan from a single user
        currentUserLatestLoan.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();

        String sql = "Select *\n" +
                "From loan\n" +
                "Where User_idUser = '" + idUser +
                "'AND idLoan NOT IN (Select Loan_idLoan From Loanreturn)\n" +
                "Order by loanDate desc\n" +
                "Limit 1;";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        LoanModel loan = null;
        while (rst.next()) {
            loan = new LoanModel(rst.getInt("User_idUser"), rst.getInt("Inventory_idBarcode"), rst.getTimestamp("loanDate"), rst.getTimestamp("expiryDate"), rst.getInt("idLoan"));
            currentUserLatestLoan.add(loan);
            break;
        }
    }


    public static void getAllLoansIdUser(int idUser) throws SQLException { //gets ALL loans for a single user
        currentUserLoans.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();

        String sql = "Select *\n" +
                "From loan\n" +
                "Where User_idUser = '" + idUser +
                "'AND idLoan NOT IN (Select Loan_idLoan From Loanreturn)\n" +
                "Order by loanDate desc";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            LoanModel loan = new LoanModel(rst.getInt("User_idUser"), rst.getInt("Inventory_idBarcode"), rst.getTimestamp("loanDate"), rst.getTimestamp("expiryDate"), rst.getInt("idLoan"));
            currentUserLoans.add(loan);
        }
        if (currentUserLoans.isEmpty()) {
            System.out.println("No active loans");
        }
    }

    public static void checkOverdueLoansDB() throws SQLException { //checks for overdue loans by calling our stored procedure ReminderTest
        overdueLoans.clear();
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        CallableStatement statement = conn.prepareCall("{CALL ReminderTest()}");
        ResultSet rst;
        rst = statement.executeQuery();
        while (rst.next()) {
            LoanModel overdueLoan = new LoanModel(rst.getInt("idLoan"), rst.getInt("User_idUser"),
                    rst.getInt("Inventory_idBarcode"), rst.getTimestamp("loanDate"), rst.getTimestamp("expiryDate"), rst.getInt("Days overdue"));
            overdueLoans.add(overdueLoan);
        }
    }
    public static int getLoanCountIdUser(int IdUser) throws SQLException { //counts how many active loans a single user has based on their user ID, this then shows up on their startpage
        int loanCount = 0;
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "Select Count(idLoan) As 'Active Loans' From Loan\n" +
                "Where idLoan NOT IN (Select Loan_idLoan From Loanreturn)\n" +
                "And User_idUser = " + IdUser;
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            loanCount = rst.getInt("Active Loans");
        }
        conn.close();
        return loanCount;
    }
    public static int getOverdueLoansCount(int idUser) throws SQLException { //checks how many overdue loans a single user has based on their user ID, which is then presented on their startpage
        int overdueLoansCount = 0;
        DBConnection connectNow = new DBConnection();
        Connection conn = connectNow.getConnection();
        Statement stm;
        stm = conn.createStatement();
        String sql = "SELECT Count(case when DATEDIFF(curDate(), expiryDate) > 0 then 1 end) as 'Total Count'\n" +
                " FROM Loan\n" +
                " WHERE idLoan NOT IN (SELECT Loan_idLoan FROM Loanreturn)\n" +
                " And User_idUser = " + idUser + "\n" +
                " Group by 'totalCount'\n" +
                " ORDER BY (DATEDIFF(curDate(), expiryDate)) desc;";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        while (rst.next()) {
            overdueLoansCount = rst.getInt("Total Count");
        }
        conn.close();
        return overdueLoansCount;
    }
}








