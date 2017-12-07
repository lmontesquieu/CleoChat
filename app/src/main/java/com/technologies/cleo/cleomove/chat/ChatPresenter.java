package com.technologies.cleo.cleomove.chat;

import com.technologies.cleo.cleomove.chat.events.ChatEvent;

/**
 * Created by Pepe on 10/25/16.
 */

public interface ChatPresenter {

    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}

