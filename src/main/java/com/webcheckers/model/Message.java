package com.webcheckers.model;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/13/2017.
 */
public class Message {
    private String text;
    private type type;

    public enum type {info, error, test}

    public Message(String message, type type) {
        this.text = message;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public type getType() {
        return type;
    }
}
