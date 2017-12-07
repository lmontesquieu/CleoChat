package com.technologies.cleo.cleomove.login;

/**
 * Created by Pepe on 10/13/2016.
 */

public interface LoginInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
