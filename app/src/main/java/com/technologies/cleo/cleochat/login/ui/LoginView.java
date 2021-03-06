package com.technologies.cleo.cleochat.login.ui;

/**
 * Created by Pepe on 10/13/2016.
 */

public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignIn();
    void handleSignUp();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);
}
