package com.technologies.cleo.cleochat.contactlist;

/**
 * Created by Pepe on 10/20/16.
 */

public interface ContactListInteractor {
    void subscribe();
    void unsuscribe();
    void destroyListener();
    void removeContact(String email);
}
