package com.technologies.cleo.cleomove.chat;

/**
 * Created by Pepe on 10/25/16.
 */

public interface ChatInteractor {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
}
