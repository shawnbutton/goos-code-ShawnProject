package com.shawnbutton.auctionsniper.ui;

import javax.swing.*;
import static com.shawnbutton.auctionsniper.Main.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Auction Sniper");
        setName(MAIN_WINDOW_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}