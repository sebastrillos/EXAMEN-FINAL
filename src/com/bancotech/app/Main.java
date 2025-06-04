package com.bancotech.app;

import com.bancotech.modelo.Cliente;
import com.bancotech.modelo.CuentaBancaria;
import com.bancotech.modelo.Transaccion;
import com.bancotech.servicio.Banco;
import java.awt.*;
import javax.swing.*;

public class Main {
    private static Banco banco = new Banco();
    private static Cliente clienteLogueado = null;

    public static Banco getBanco() {
        return banco;
    }

    public static Cliente getClienteLogueado() {
        return clienteLogueado;
    }

    public static void setClienteLogueado(Cliente cliente) {
        clienteLogueado = cliente;
    }

    public static void cargarDatosIniciales() {
        Cliente c1 = new Cliente("123", "Juan Perez", "1111");
        banco.agregarCliente(c1);
        banco.crearCuenta(c1, "ahorro", 0.05, 0);
        banco.crearCuenta(c1, "corriente", 0, 500.0);

        Cliente c2 = new Cliente("456", "Maria Lopez", "2222");
        banco.agregarCliente(c2);
        banco.crearCuenta(c2, "ahorro", 0.03, 0);
    }
}

class MainFrame extends JFrame {
    private JTextArea textArea;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("BancoTech");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        MenuPrincipal();
        PanelRegistro();
        PanelLogin();
        PanelOperaciones();

        add(scrollPane, BorderLayout.CENTER);
        add(cardPanel, BorderLayout.SOUTH);

        cardLayout.show(cardPanel, "menu");
    }

    private void MenuPrincipal() {
        JPanel panelInicial = new JPanel(new GridLayout(4, 1, 5, 5));

        JButton btnRegistro = new JButton("Registrar nuevo cliente");
        btnRegistro.addActionListener(e -> cardLayout.show(cardPanel, "registro"));

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.addActionListener(e -> cardLayout.show(cardPanel, "login"));

        JButton btnOperaciones = new JButton("Acceder a operaciones");
        btnOperaciones.addActionListener(e -> {
            if (Main.getClienteLogueado() != null) {
                cardLayout.show(cardPanel, "operaciones");
            } else {
                textArea.append("Por favor, inicie sesión primero.\n");
            }
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));

        panelInicial.add(btnRegistro);
        panelInicial.add(btnLogin);
        panelInicial.add(btnOperaciones);
        panelInicial.add(btnSalir);

        cardPanel.add(panelInicial, "menu");
        cardLayout.show(cardPanel, "menu");
    }

    private void PanelRegistro() {
        JPanel panelRegistro = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtPin = new JTextField();

        panelRegistro.add(new JLabel("Cédula:"));
        panelRegistro.add(txtId);
        panelRegistro.add(new JLabel("Nombre completo:"));
        panelRegistro.add(txtNombre);
        panelRegistro.add(new JLabel("PIN (4 dígitos):"));
        panelRegistro.add(txtPin);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            Cliente nuevoCliente = new Cliente(txtId.getText(), txtNombre.getText(), txtPin.getText());
            Main.getBanco().agregarCliente(nuevoCliente);
            textArea.append("Cliente registrado: " + txtNombre.getText() + "\n");
            cardLayout.show(cardPanel, "menu");
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(cardPanel, "menu"));

        panelRegistro.add(btnRegistrar);
        panelRegistro.add(btnCancelar);

        cardPanel.add(panelRegistro, "registro");
    }

    private void PanelLogin() {
        JPanel panelLogin = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField txtId = new JTextField();
        JTextField txtPin = new JTextField();

        panelLogin.add(new JLabel("Cédula:"));
        panelLogin.add(txtId);
        panelLogin.add(new JLabel("PIN:"));
        panelLogin.add(txtPin);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.addActionListener(e -> {
            Cliente cliente = Main.getBanco().buscarCliente(txtId.getText());
            if (cliente != null && cliente.validarPin(txtPin.getText())) {
                Main.setClienteLogueado(cliente);
                textArea.append("Sesión iniciada: " + cliente.getNombre() + "\n");
                txtId.setText("");
                txtPin.setText("");
                cardLayout.show(cardPanel, "menu");
            } else {
                textArea.append("Credenciales incorrectas\n");
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            txtId.setText("");
            txtPin.setText("");
            cardLayout.show(cardPanel, "menu");
        });

        panelLogin.add(btnLogin);
        panelLogin.add(btnCancelar);

        cardPanel.add(panelLogin, "login");
    }

    private void PanelOperaciones() {
        JPanel panelDos = new JPanel(new GridLayout(9, 1, 5, 5));

        JButton btnCrearCuenta = new JButton("Crear nueva cuenta");
        btnCrearCuenta.addActionListener(e -> crearCuenta());

        JButton btnVerCuentas = new JButton("Ver mis cuentas y saldos");
        btnVerCuentas.addActionListener(e -> verCuentas());

        JButton btnDepositar = new JButton("Depositar dinero");
        btnDepositar.addActionListener(e -> operacionCuenta("depositar"));

        JButton btnRetirar = new JButton("Retirar dinero");
        btnRetirar.addActionListener(e -> operacionCuenta("retirar"));

        JButton btnTransferir = new JButton("Transferir dinero");
        btnTransferir.addActionListener(e -> transferir());

        JButton btnHistorial = new JButton("Ver historial de transacciones");
        btnHistorial.addActionListener(e -> verHistorial());

        JButton btnPrestamo = new JButton("Calcular préstamo");
        btnPrestamo.addActionListener(e -> calcularPrestamo());

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.addActionListener(e -> {
            textArea.append("Sesión cerrada para " + Main.getClienteLogueado().getNombre() + "\n");
            Main.setClienteLogueado(null);
            cardLayout.show(cardPanel, "menu");
        });

        JButton btnVolver = new JButton("Volver al menú principal");
        btnVolver.addActionListener(e -> cardLayout.show(cardPanel, "menu"));

        panelDos.add(btnCrearCuenta);
        panelDos.add(btnVerCuentas);
        panelDos.add(btnDepositar);
        panelDos.add(btnRetirar);
        panelDos.add(btnTransferir);
        panelDos.add(btnHistorial);
        panelDos.add(btnPrestamo);
        panelDos.add(btnCerrarSesion);
        panelDos.add(btnVolver);

        cardPanel.add(panelDos, "operaciones");
        cardLayout.show(cardPanel, "operaciones");
    }

    private void crearCuenta() {
        JPanel panelCuenta = new JPanel(new GridLayout(4, 2, 5, 5));

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"ahorro", "corriente"});
        JTextField txtTasa = new JTextField("0.05");
        JTextField txtLimite = new JTextField("0");

        panelCuenta.add(new JLabel("Tipo de cuenta:"));
        panelCuenta.add(cbTipo);
        panelCuenta.add(new JLabel("Tasa de interés:"));
        panelCuenta.add(txtTasa);
        panelCuenta.add(new JLabel("Límite sobregiro:"));
        panelCuenta.add(txtLimite);

        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener(e -> {
            String tipo = (String) cbTipo.getSelectedItem();
            double tasa = Double.parseDouble(txtTasa.getText());
            double limite = Double.parseDouble(txtLimite.getText());
            Main.getBanco().crearCuenta(Main.getClienteLogueado(), tipo, tasa, limite);
            textArea.append("Cuenta " + tipo + " creada\n");
            cardLayout.show(cardPanel, "operaciones");
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(cardPanel, "operaciones"));

        panelCuenta.add(btnCrear);
        panelCuenta.add(btnCancelar);

        cardPanel.removeAll();
        cardPanel.add(panelCuenta, "crearCuenta");
        cardLayout.show(cardPanel, "crearCuenta");
    }

    private void verCuentas() {
        textArea.append("\n--- MIS CUENTAS Y SALDOS ---\n");
        if (Main.getClienteLogueado().getCuentas().isEmpty()) {
            textArea.append("No tiene cuentas registradas.\n");
            return;
        }
        for (CuentaBancaria cuenta : Main.getClienteLogueado().getCuentas()) {
            textArea.append("- " + cuenta + "\n");
        }
    }

    private void operacionCuenta(String tipo) {
        JPanel panelOperacionCuenta = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField txtCuenta = new JTextField();
        JTextField txtMonto = new JTextField();

        panelOperacionCuenta.add(new JLabel("Número de cuenta:"));
        panelOperacionCuenta.add(txtCuenta);
        panelOperacionCuenta.add(new JLabel("Monto:"));
        panelOperacionCuenta.add(txtMonto);

        JButton confirmar = new JButton(tipo.equals("depositar") ? "Depositar" : "Retirar");
        confirmar.addActionListener(e -> {
            CuentaBancaria cuenta = Main.getBanco().buscarCuenta(txtCuenta.getText());
            if (cuenta != null && Main.getClienteLogueado().getCuentas().contains(cuenta)) {
                double monto = Double.parseDouble(txtMonto.getText());
                if (tipo.equals("depositar")) {
                    cuenta.depositar(monto);
                    textArea.append("Depósito de " + monto + " en cuenta " + cuenta.getNumeroCuenta() + "\n");
                } else {
                    cuenta.retirar(monto);
                    textArea.append("Retiro de " + monto + " de cuenta " + cuenta.getNumeroCuenta() + "\n");
                }
            } else {
                textArea.append("Cuenta no válida\n");
            }
            cardLayout.show(cardPanel, "operaciones");
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(cardPanel, "operaciones"));

        panelOperacionCuenta.add(confirmar);
        panelOperacionCuenta.add(btnCancelar);

        cardPanel.removeAll();
        cardPanel.add(panelOperacionCuenta, "operacionCuenta");
        cardLayout.show(cardPanel, "operacionCuenta");
    }

    private void transferir() {
        JPanel panelTransferir = new JPanel(new GridLayout(4, 2, 5, 5));

        JTextField txtCuentaOrigen = new JTextField();
        JTextField txtCuentaDestino = new JTextField();
        JTextField txtMonto = new JTextField();

        panelTransferir.add(new JLabel("Cuenta origen:"));
        panelTransferir.add(txtCuentaOrigen);
        panelTransferir.add(new JLabel("Cuenta destino:"));
        panelTransferir.add(txtCuentaDestino);
        panelTransferir.add(new JLabel("Monto:"));
        panelTransferir.add(txtMonto);

        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.addActionListener(e -> {
            CuentaBancaria origen = Main.getBanco().buscarCuenta(txtCuentaOrigen.getText());
            CuentaBancaria destino = Main.getBanco().buscarCuenta(txtCuentaDestino.getText());
            if (origen != null && destino != null && Main.getClienteLogueado().getCuentas().contains(origen)) {
                double monto = Double.parseDouble(txtMonto.getText());
                origen.transferir(destino, monto);
                textArea.append("Transferencia de " + monto + " de cuenta " + origen.getNumeroCuenta()
                        + " a cuenta " + destino.getNumeroCuenta() + "\n");
            } else {
                textArea.append("Cuentas no válidas\n");
            }
            cardLayout.show(cardPanel, "operaciones");
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(cardPanel, "operaciones"));

        panelTransferir.add(btnTransferir);
        panelTransferir.add(btnCancelar);

        cardPanel.removeAll();
        cardPanel.add(panelTransferir, "transferir");
        cardLayout.show(cardPanel, "transferir");
    }

    private void verHistorial() {
        JPanel panelHistorial = new JPanel(new GridLayout(2, 2, 5, 5));

        JTextField txtCuenta = new JTextField();
        panelHistorial.add(new JLabel("Número de cuenta:"));
        panelHistorial.add(txtCuenta);

        JButton btnVer = new JButton("Ver historial");
        btnVer.addActionListener(e -> {
            CuentaBancaria cuenta = Main.getBanco().buscarCuenta(txtCuenta.getText());
            if (cuenta != null && Main.getClienteLogueado().getCuentas().contains(cuenta)) {
                textArea.append("\n--- HISTORIAL DE " + cuenta.getNumeroCuenta() + " ---\n");
                for (Transaccion t : cuenta.getHistorialTransacciones()) {
                    textArea.append("- " + t + "\n");
                }
            } else {
                textArea.append("Cuenta no válida\n");
            }
            cardLayout.show(cardPanel, "operaciones");
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(cardPanel, "operaciones"));

        panelHistorial.add(btnVer);
        panelHistorial.add(btnCancelar);

        cardPanel.removeAll();
        cardPanel.add(panelHistorial, "historial");
        cardLayout.show(cardPanel, "historial");
    }

    private void calcularPrestamo() {
        JPanel panelPrestamo = new JPanel(new GridLayout(5, 2, 5, 5));

        JTextField txtMonto = new JTextField();
        JTextField txtPeriodos = new JTextField();
        JTextField txtTasa = new JTextField();

        panelPrestamo.add(new JLabel("Monto del préstamo:"));
        panelPrestamo.add(txtMonto);
        panelPrestamo.add(new JLabel("Número de meses:"));
        panelPrestamo.add(txtPeriodos);
        panelPrestamo.add(new JLabel("Tasa de interés anual (%):"));
        panelPrestamo.add(txtTasa);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.addActionListener(e -> {
            double monto = Double.parseDouble(txtMonto.getText());
            int periodos = Integer.parseInt(txtPeriodos.getText());
            double tasa = Double.parseDouble(txtTasa.getText());
            double pago = Main.getBanco().calcularPagoMensualPrestamo(monto, periodos, tasa);
            textArea.append(String.format("Pago mensual estimado: %.2f\n", pago));
            cardLayout.show(cardPanel, "operaciones");
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(cardPanel, "operaciones"));

        panelPrestamo.add(btnCalcular);
        panelPrestamo.add(btnCancelar);

        cardPanel.removeAll();
        cardPanel.add(panelPrestamo, "prestamo");
        cardLayout.show(cardPanel, "prestamo");
    }
}

