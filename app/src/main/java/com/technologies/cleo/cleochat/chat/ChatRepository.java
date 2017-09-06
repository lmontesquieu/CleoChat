package com.technologies.cleo.cleochat.chat;

/**
 * Created by Pepe on 10/25/16.
 */

public interface ChatRepository {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
    void changeConnectionStatus(boolean online);
}
