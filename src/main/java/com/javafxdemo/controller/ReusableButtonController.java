package com.javafxdemo.controller;

import com.javafxdemo.LibraryApplication;
import com.javafxdemo.Session;

import java.io.IOException;

import static com.javafxdemo.LibraryApplication.getStage;
import static com.javafxdemo.LibraryApplication.setSceneStartPage;

public class ReusableButtonController {

    public void exit() {
        System.exit(0);
    }

    public void backMethod(String previousScene) throws IOException {
        if (previousScene.equalsIgnoreCase("StartpageLoggedIn") && Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == Boolean.TRUE) {
            Session.getInstance().getStartpageLoggedInController().setSceneStartpageLoggedIn();
        }
        if (previousScene.equalsIgnoreCase("Startpage")) {
            setSceneStartPage();
        }
        if (previousScene.equalsIgnoreCase("Admin") && (Session.getInstance().getCurrentUser().getCurrentlyLoggedIn() == (Boolean.TRUE)) && ((Session.getInstance().getCurrentUser().getUserType() == 1114) || Session.getInstance().getCurrentUser().getUserType() == 1115)) {
            Session.getInstance().getAdminController().setSceneAdmin();
        }
    }
}
