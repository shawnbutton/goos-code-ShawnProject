package com.shawnbutton.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class SingleMessageListener implements MessageListener {
    private final ArrayBlockingQueue<Message> messages =
            new ArrayBlockingQueue<Message>(1);

    public void processMessage(Chat chat, Message message) {
        messages.add(message);
    }

    public void receivesAMessage() throws InterruptedException {
        Message message = messages.poll(25, TimeUnit.SECONDS);
        assertThat("Message", message, is(notNullValue()));
    }
}