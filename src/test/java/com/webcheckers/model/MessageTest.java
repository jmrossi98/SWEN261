package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jennamacbeth on 11/6/17.
 */
public class MessageTest {

    private Message.type info = Message.type.info;
    private Message.type error = Message.type.error;
    private String text = "info";
    private String text2 = "error";

    private Message test = new Message(text,info);
    private Message test2 = new Message(text2,error);

    @Test
    public void test_GetText(){
        final String temp = test.getText();
        assertSame(temp,"info");
    }

    @Test
    public void test_GetText2(){
        final String temp = test2.getText();
        assertSame(temp,"error");
    }

    @Test
    public void test_GetType(){
        final Message.type temp = test.getType();
        assertSame(temp, Message.type.info);
    }

    @Test
    public void test_GetType2(){
        final Message.type temp = test2.getType();
        assertSame(temp, Message.type.error);
    }
}