package com.bancotech.app;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true)); 
    }
}
