package com.technologies.cleo.cleochat.contactlist;

import com.technologies.cleo.cleochat.contactlist.events.ContactListEvent;

/**
 * Created by Pepe on 10/20/16.
 */

public interface ContactListPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);

}
