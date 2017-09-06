package com.technologies.cleo.cleochat.addcontact;

import com.technologies.cleo.cleochat.addcontact.events.AddContactEvent;

/**
 * Created by Pepe on 10/24/16.
 */

public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
