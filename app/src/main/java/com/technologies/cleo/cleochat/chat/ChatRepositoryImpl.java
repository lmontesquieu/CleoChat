package com.technologies.cleo.cleochat.chat;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.technologies.cleo.cleochat.R;
import com.technologies.cleo.cleochat.chat.events.ChatEvent;
import com.technologies.cleo.cleochat.domain.FirebaseHelper;
import com.technologies.cleo.cleochat.entities.ChatMessage;
import com.technologies.cleo.cleochat.lib.EventBus;
import com.technologies.cleo.cleochat.lib.GreenRobotEventBus;

import butterknife.OnClick;

/**
 * Created by Pepe on 10/26/16.
 */
public class ChatRepositoryImpl implements ChatRepository {

    private String recipient;
    private EventBus eventBus;
    private FirebaseHelper helper;
    private ChildEventListener chatEventListener;
    private final static String TAG = "ChatRepositoryImpl";

    public ChatRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }

    @Override
    public void sendMessage(String msg) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(helper.getAuthUserEmail());
        chatMessage.setMsg(msg);

        DatabaseReference chatsReference = helper.getChatsReference(recipient);
        chatsReference.push().setValue(chatMessage);
    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public void subscribe() {
        if (chatEventListener == null) {
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String messageSender = chatMessage.getSender();

                    chatMessage.setSentByMe(messageSender.equals(helper.getAuthUserEmail()));

                    ChatEvent chatEvent = new ChatEvent();
                    chatEvent.setMessage(chatMessage);
                    Log.e(TAG, "onChildAdded");
                    Log.e(TAG, chatMessage.getMsg());
                    Log.e(TAG, chatMessage.getSender());
                    Log.e(TAG, String.valueOf(messageSender.equals(helper.getAuthUserEmail())));
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
        helper.getChatsReference(recipient).addChildEventListener(chatEventListener);
    }

    @Override
    public void unsubscribe() {
        if (chatEventListener != null) {
            helper.getChatsReference(recipient).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void destroyListener() {
        chatEventListener = null;
    }


}
