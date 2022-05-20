package com.javafxdemo.controller;

import com.javafxdemo.Session;

import java.io.IOException;

import static com.javafxdemo.LibraryApplication.setSceneStartPage;

//this controller controls everything that's reusable in the code, and acts as a helper class
public class ReusableButtonController {

    public void exit() {
        System.exit(0);
    } //reusable exit method

    public void backMethod(String previousScene) throws IOException { //reusable back method, although very simple
        if (previousScene.equalsIgnoreCase("StartpageLoggedIn") && Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == Boolean.TRUE) {
            Session.getInstance().getStartpageLoggedInController().setSceneStartpageLoggedIn();
        }
        if (previousScene.equalsIgnoreCase("Startpage")) {
            setSceneStartPage();
        }
        if (previousScene.equalsIgnoreCase("Admin") && (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == (Boolean.TRUE)) && ((Session.getInstance().getCurrentUser().getUserType() == 1114) || Session.getInstance().getCurrentUser().getUserType() == 1115)) {
            Session.getInstance().getAdminController().setSceneAdmin();
        }
        if (previousScene.equalsIgnoreCase("Delete") && (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == (Boolean.TRUE)) && ((Session.getInstance().getCurrentUser().getUserType() == 1114) || Session.getInstance().getCurrentUser().getUserType() == 1115)) {
            Session.getInstance().getDeleteController().setSceneDeleteItem();
        }
    }
}
