package com.shawnbutton.auctionsniper;

import com.shawnbutton.auctionsniper.ui.MainWindow;

import javax.swing.*;

public class Main {
    public static final String MAIN_WINDOW_NAME = "MainWindow";
    public static final String SNIPER_STATUS_NAME = "SniperStatus";
    public static final String STATUS_JOINING = "joining";

    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                ui = new MainWindow();
            }
        });
    }

}
