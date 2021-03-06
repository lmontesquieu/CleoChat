package com.technologies.cleo.cleochat.login;

import android.util.Log;

import com.technologies.cleo.cleochat.lib.EventBus;
import com.technologies.cleo.cleochat.lib.GreenRobotEventBus;
import com.technologies.cleo.cleochat.login.events.LoginEvent;
import com.technologies.cleo.cleochat.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Pepe on 10/13/2016.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";
    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        Log.e(TAG,"LoginPresenterImpl");
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (loginView != null) {
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        Log.e(TAG,"onEventMainThread");
        switch (event.getEventType()) {
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
        }
    }

    private void onSignInSuccess() {
        Log.e(TAG,"onSignInSuccess");
        if (loginView != null) {
            loginView.navigateToMainScreen();
        }
    }
    private void onSignInError(String error) {
        Log.e(TAG, error);
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }
    }
    private void onSignUpSuccess() {
        Log.e(TAG,"onSignUpSuccess");
        if (loginView != null) {
            loginView.newUserSuccess();
        }
    }
    private void onSignUpError(String error) {
        Log.e(TAG,"onSignUpError");
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }
    }
}
