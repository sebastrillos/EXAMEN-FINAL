import java.awt.*;  // Librerías Swing
import javax.swing.*;     // Para layouts

public class BancoSwingApp {

    public static void main(String[] args) {
        // Crear ventana (JFrame)
        JFrame ventana = new JFrame("Banco Java");
        ventana.setSize(400, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // Etiqueta de bienvenida
        JLabel titulo = new JLabel("Bienvenido al Banco", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        ventana.add(titulo, BorderLayout.NORTH);

        // Panel central con botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton botonSaldo = new JButton("Consultar Saldo");
        JButton botonDeposito = new JButton("Depositar Dinero");
        JButton botonSalir = new JButton("Salir");

        panel.add(botonSaldo);
        panel.add(botonDeposito);
        panel.add(botonSalir);
        ventana.add(panel, BorderLayout.CENTER);

        // Evento para "Consultar Saldo"
        botonSaldo.addActionListener(e -> {
            JOptionPane.showMessageDialog(ventana, "Tu saldo actual es: $1000");
        });

        // Evento para "Depositar Dinero"
        botonDeposito.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(ventana, "¿Cuánto deseas depositar?");
            try {
                double cantidad = Double.parseDouble(input);
                JOptionPane.showMessageDialog(ventana, "Has depositado: $" + cantidad);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "Por favor, ingresa un número válido.");
            }
        });

        // Evento para "Salir"
        botonSalir.addActionListener(e -> {
            ventana.dispose(); // Cierra la ventana
        });

        // Mostrar ventana
        ventana.setVisible(true);
    }
}

