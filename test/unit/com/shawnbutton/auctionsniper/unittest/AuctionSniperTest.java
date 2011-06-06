package com.shawnbutton.auctionsniper.unittest;

import com.shawnbutton.auctionsniper.Auction;
import com.shawnbutton.auctionsniper.AuctionEventListener;
import com.shawnbutton.auctionsniper.AuctionSniper;
import com.shawnbutton.auctionsniper.SniperListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.shawnbutton.auctionsniper.AuctionEventListener.*;
import static com.shawnbutton.auctionsniper.AuctionEventListener.PriceSource.*;
import static com.shawnbutton.auctionsniper.AuctionEventListener.PriceSource.FromSniper;

@RunWith(JMock.class)
public class AuctionSniperTest {

    private final Mockery context = new Mockery();
    private final SniperListener sniperListener = context.mock(SniperListener.class);
    private final Auction auction = context.mock(Auction.class);
    private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);

    private final States sniperState = context.states("sniper");

    @Test
    public void reportsLostIfAuctionClosesImmediately() {

        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperLost();
        }});

        sniper.auctionClosed();

    }


    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrives() {
        final int price = 1001;
        final int increment = 25;
        context.checking(new Expectations() {{
            one(auction).bid(price + increment);
            atLeast(1).of(sniperListener).sniperBidding();
        }});
        sniper.currentPrice(price, increment, FromOtherBidder);
    }


    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperWinning();
        }});
        sniper.currentPrice(123, 45, FromSniper);
    }

    @Test
    public void reportsLostIfAuctionClosesWhenBidding() {
        context.checking(new Expectations() {{
            ignoring(auction);

            allowing(sniperListener).sniperBidding();
            then(sniperState.is("bidding"));

            atLeast(1).of(sniperListener).sniperLost();
            when(sniperState.is("bidding"));

        }});
        sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);

        sniper.auctionClosed();
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperWinning();
            then(sniperState.is("winning"));
            atLeast(1).of(sniperListener).sniperWon();
            when(sniperState.is("winning"));
        }});
        sniper.currentPrice(123, 45, FromSniper);
        sniper.auctionClosed();
    }
}