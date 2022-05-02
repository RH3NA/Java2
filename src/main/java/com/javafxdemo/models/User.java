package com.javafxdemo.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    private int idUser;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String email;
    private int userType;

    public User(int idUser, String lastName, String firstName, String phoneNumber, String email, int userType) {
        this.idUser = idUser;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
    }

    @Override
    public String toString()
    {
        return "     idUser = " + this.idUser +
                "    lastName = " + this.lastName +
                "    firstName = " + this.firstName +
                "    phoneNumber = " + this.phoneNumber +
                "    email = " + this.email +
                "    userType = " + this.userType;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

}