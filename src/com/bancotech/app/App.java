package com.bancotech.app;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        com.bancotech.app.Main.cargarDatosIniciales();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true)); 
    }
}
