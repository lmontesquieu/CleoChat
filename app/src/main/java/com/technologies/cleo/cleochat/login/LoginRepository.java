package com.technologies.cleo.cleochat.login;

/**
 * Created by Pepe on 10/13/2016.
 */

public interface LoginRepository {
    void signIn(String email, String password);
    void signUp(String email, String password);
    void checkSession();
}
