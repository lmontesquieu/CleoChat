package com.technologies.cleo.cleochat.chat.events;

import com.technologies.cleo.cleochat.entities.ChatMessage;

/**
 * Created by Pepe on 10/25/16.
 */
public class ChatEvent {
    private ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
