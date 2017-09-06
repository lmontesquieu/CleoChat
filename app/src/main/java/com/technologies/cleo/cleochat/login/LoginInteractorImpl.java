package com.technologies.cleo.cleochat.login;

import android.util.Log;

/**
 * Created by Pepe on 10/13/2016.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    public LoginInteractorImpl() {
        //Log.e(TAG,"Constructor");
        this.loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        //Log.e(TAG,"checkSession");
        loginRepository.checkSession();
    }

    @Override
    public void doSignUp(String email, String password) {
        //Log.e(TAG,"doSignUp");
        loginRepository.signUp(email, password);
    }

    @Override
    public void doSignIn(String email, String password) {
        //Log.e(TAG,"doSignIn");
        loginRepository.signIn(email, password);
    }
}
