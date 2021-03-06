package com.technologies.cleo.cleochat.login;

import com.technologies.cleo.cleochat.login.events.LoginEvent;

/**
 * Created by Pepe on 10/13/2016.
 */

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
