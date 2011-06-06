package com.shawnbutton.auctionsniper.endToEnd;

import com.shawnbutton.auctionsniper.Main;
import com.shawnbutton.auctionsniper.ui.MainWindow;

import javax.swing.*;

import static com.shawnbutton.auctionsniper.endToEnd.FakeAuctionServer.*;
import static org.junit.Assert.fail;

public class ApplicationRunner {

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    private AuctionSniperDriver driver;
    public static final String SNIPER_XMPP_ID = "sniper@localhost/Auction";

    private static final String STATUS_BIDDING = "bidding";
    private static final String STATUS_JOINING = "joining";
    private static final String STATUS_LOST = "lost";
    private static final String STATUS_WINNING = "winning";
    private static final String STATUS_WON = "won";

    public void startBiddingIn(final FakeAuctionServer auction) {

        Thread thread = new Thread("Test Application") {

            @Override
            public void run() {

                try {

                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        };


        thread.setDaemon(true);
        thread.start();

        makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock();

        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(STATUS_JOINING);
    }

    private void makeSureAwtIsLoadedBeforeStartingTheDriverOnOSXToStopDeadlock() {
        try {
	    SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
            }
        });
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }

    public void showsSniperHasLostAuction() {

        driver.showsSniperStatus(STATUS_LOST);

    }

    public void hasShownSniperIsBidding() {
        driver.showsSniperStatus(STATUS_BIDDING);

    }

    public void hasShownSniperIsWinning() {
        driver.showsSniperStatus(STATUS_WINNING);
    }

    public void showsSniperHasWonAuction() {
        driver.showsSniperStatus(STATUS_WON);
    }
}
