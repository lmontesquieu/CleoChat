package com.technologies.cleo.cleomove.addcontact.ui;

/**
 * Created by Pepe on 10/24/16.
 */

public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
}
