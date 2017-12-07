package com.technologies.cleo.cleomove.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.firebase.database.Exclude;

/**
 * Created by Pepe on 10/25/16.
 */
//@JsonIgnoreProperties({"sentByMe"})
@JsonIgnoreProperties(ignoreUnknown=true)
public class ChatMessage {
    private String msg;
    private String sender;
    @Exclude
    private boolean sentByMe;

    public ChatMessage() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Exclude
    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof ChatMessage) {
            ChatMessage message = (ChatMessage) obj;
            equal = this.sender.equals(message.getSender()) && this.msg.equals(message.getMsg())
                    && this.sentByMe == message.sentByMe;
        }
        return equal;
    }
}
