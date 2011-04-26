package com.shawnbutton.auctionsniper;

public class AuctionSniper implements AuctionEventListener {
    private SniperListener sniperListener;

    public AuctionSniper(SniperListener sniperListener) {
        this.sniperListener = sniperListener;
    }

    public void auctionClosed() {
        sniperListener.sniperLost();
    }

    public void currentPrice(int price, int increment) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
