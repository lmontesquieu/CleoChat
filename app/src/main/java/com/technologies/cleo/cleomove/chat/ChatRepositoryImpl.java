package com.technologies.cleo.cleomove.chat;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.technologies.cleo.cleomove.chat.events.ChatEvent;
import com.technologies.cleo.cleomove.domain.FirebaseHelper;
import com.technologies.cleo.cleomove.entities.ChatMessage;
import com.technologies.cleo.cleomove.lib.EventBus;
import com.technologies.cleo.cleomove.lib.GreenRobotEventBus;

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
                    Log.d(TAG, "onChildAdded");
                    Log.d(TAG, chatMessage.getMsg());
                    Log.d(TAG, chatMessage.getSender());
                    Log.d(TAG, String.valueOf(messageSender.equals(helper.getAuthUserEmail())));
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
