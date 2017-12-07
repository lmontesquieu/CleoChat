package com.technologies.cleo.cleomove.contactlist;

/**
 * Created by Pepe on 10/20/16.
 */

public interface ContactListInteractor {
    void subscribe();
    void unsuscribe();
    void destroyListener();
    void removeContact(String email);
}
