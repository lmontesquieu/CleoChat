package com.technologies.cleo.cleochat.chat.ui;

import com.technologies.cleo.cleochat.entities.ChatMessage;

/**
 * Created by Pepe on 10/25/16.
 */

public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
