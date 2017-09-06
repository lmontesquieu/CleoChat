package com.technologies.cleo.cleochat.contactlist;

/**
 * Created by Pepe on 10/20/16.
 */

public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);
}
