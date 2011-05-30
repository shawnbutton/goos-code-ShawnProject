package com.shawnbutton.auctionsniper;

import org.jivesoftware.smack.XMPPException;

public interface Auction {
    public void bid(int bidAmount);

    public void join() throws XMPPException;
}
