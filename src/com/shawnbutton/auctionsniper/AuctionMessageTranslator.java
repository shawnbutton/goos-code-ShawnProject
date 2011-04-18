package com.shawnbutton.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;

public class AuctionMessageTranslator implements MessageListener {
    private AuctionEventListener listener;

    public AuctionMessageTranslator(AuctionEventListener listener) {

        this.listener = listener;
    }

    public void processMessage(Chat unusedChat, Message message) {
        listener.auctionClosed();




    }



}