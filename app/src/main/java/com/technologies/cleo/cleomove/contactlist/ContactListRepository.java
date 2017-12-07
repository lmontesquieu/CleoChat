package com.technologies.cleo.cleomove.contactlist;

/**
 * Created by Pepe on 10/20/16.
 */

public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void destroyListener();
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void changeConnectionStatus(boolean online);
}
