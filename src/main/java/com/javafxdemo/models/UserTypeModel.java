package com.javafxdemo.models;

//This class is based on the UserType table in our database, however we haven't worked with this one since we had to limit our project to the project requirements.
public class UserTypeModel {
    private int idUserType;
    private String userType;

    public int getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(int idUserType) {
        this.idUserType = idUserType;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}
