package com.javafxdemo;

public class LoggedInSession {
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    private String idUser;
    private String userTypeId;

    public LoggedInSession(String idUser, String userTypeId) {
        this.idUser = idUser;
        this.userTypeId = userTypeId;
    }
}
