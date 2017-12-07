package com.technologies.cleo.cleomove.chat.ui;

import com.technologies.cleo.cleomove.entities.ChatMessage;

/**
 * Created by Pepe on 10/25/16.
 */

public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
