package com.shawnbutton.auctionsniper;

import com.shawnbutton.auctionsniper.ui.MainWindow;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    @SuppressWarnings("unused") private Chat notToBeGCd;

    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;


    public static final String MAIN_WINDOW_NAME = "MainWindow";
    public static final String SNIPER_STATUS_NAME = "SniperStatus";
    public static final String STATUS_JOINING = "joining";
    public static final String STATUS_LOST = "lost";

    private static MainWindow ui;
    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();
        XMPPConnection connection = connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
        main.joinAuction(connection, args[ARG_ITEM_ID]);

    }

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {

        disconnectWhenUICloses(connection);

        final Chat chat = connection.getChatManager().createChat( auctionId(itemId, connection), null);

        this.notToBeGCd = chat;

        Auction auction = new XMPPAuction(chat);

        chat.addMessageListener(new AuctionMessageTranslator(connection.getUser(), new AuctionSniper(auction, new SniperStateDisplayer())));

        auction.join();


    }

    private void disconnectWhenUICloses(final XMPPConnection connection) {
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                connection.disconnect();
            }
        });
    }

    private static XMPPConnection connection(String hostname, String username, String password)  throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId,
                connection.getServiceName());
    }


    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    public void auctionClosed() {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ui.showStatus(STATUS_LOST);
            }
        });

    }

    public void currentPrice(int i, int i1) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public class SniperStateDisplayer implements SniperListener {

        public void sniperLost() {
            showStatus(MainWindow.STATUS_LOST);
        }

        public void sniperBidding() {
            showStatus(MainWindow.STATUS_BIDDING);
        }

        public void sniperWinning() {
            showStatus(MainWindow.STATUS_WINNING);
        }

        public void sniperWon() {
            showStatus(MainWindow.STATUS_WON);
        }

        private void showStatus(final String status) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ui.showStatus(status);
                }
            });
        }

        }

}
