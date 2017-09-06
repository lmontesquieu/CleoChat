package com.technologies.cleo.cleochat.chat;

import android.util.Log;

import com.technologies.cleo.cleochat.chat.events.ChatEvent;
import com.technologies.cleo.cleochat.chat.ui.ChatView;
import com.technologies.cleo.cleochat.entities.User;
import com.technologies.cleo.cleochat.lib.EventBus;
import com.technologies.cleo.cleochat.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Pepe on 10/25/16.
 */
public class ChatPresenterImpl implements ChatPresenter {

    private EventBus eventBus;
    private ChatView view;
    private ChatInteractor chatInteractor;
    private ChatSessionInteractor chatSessionInteractor;
    private final static String TAG = "ChatPresenterImpl";

    public ChatPresenterImpl(ChatView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        chatInteractor.unsubscribe();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribe();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyListener();
        view = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        Log.e(TAG,"OnEventMainThread");
        if(view != null) {
            Log.e(TAG,"OnEventMainThread");
            view.onMessageReceived(event.getMessage());
        }
    }
}
