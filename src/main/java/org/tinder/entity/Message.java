package org.tinder.entity;

import java.time.LocalDateTime;

public class Message {
    private final long id;
    private final String text;
    private final LocalDateTime sendMessage;
    private final long acceptedUserID;

    public Message(long id, String text, LocalDateTime sendMessage, long acceptedUserID) {
        this.id = id;
        this.text = text;
        this.sendMessage = sendMessage;
        this.acceptedUserID = acceptedUserID;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSendMessage() {
        return sendMessage;
    }

    public long getAcceptedUserID() {
        return acceptedUserID;
    }

}
