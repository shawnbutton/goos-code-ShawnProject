package com.shawnbutton.auctionsniper;

public interface AuctionEventListener {

    enum PriceSource {
        FromSniper, FromOtherBidder
    }

    public void auctionClosed();

    void currentPrice(int i, int i1, PriceSource priceSource);
}
