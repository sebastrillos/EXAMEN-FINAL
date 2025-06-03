import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaGrafica extends JFrame {
    private Banco banco;
    private Cliente cliente;
    private CuentaBancaria cuenta;

    private JComboBox<String> accionesCombo;
    private JButton ejecutarBtn;
    private JTextArea areaResultado;

    public VentanaGrafica(Banco banco, Cliente cliente) {
        this.banco = banco;
        this.cliente = cliente;
        this.cuenta = cliente.getCuenta();

        setTitle("Sistema Bancario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] acciones = {
                "Consultar Saldo",
                "Depositar",
                "Retirar",
                "Ver Historial"
        };

        accionesCombo = new JComboBox<>(acciones);
        ejecutarBtn = new JButton("Ejecutar Acción");
        areaResultado = new JTextArea(8, 30);
        areaResultado.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Seleccione una acción:"));
        panel.add(accionesCombo);
        panel.add(ejecutarBtn);
        panel.add(new JScrollPane(areaResultado));

        ejecutarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accion = (String) accionesCombo.getSelectedItem();
                realizarAccion(accion);
            }
        });

        add(panel);
    }

    private void realizarAccion(String accion) {
        switch (accion) {
            case "Consultar Saldo":
                areaResultado.setText("Saldo actual: $" + cuenta.getSaldo());
                break;

            case "Depositar":
                String montoDeposito = JOptionPane.showInputDialog(this, "Ingrese el monto a depositar:");
                try {
                    double monto = Double.parseDouble(montoDeposito);
                    cuenta.depositar(monto);
                    areaResultado.setText("Depósito exitoso. Nuevo saldo: $" + cuenta.getSaldo());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Monto inválido");
                }
                break;

            case "Retirar":
                String montoRetiro = JOptionPane.showInputDialog(this, "Ingrese el monto a retirar:");
                try {
                    double monto = Double.parseDouble(montoRetiro);
                    boolean exito = cuenta.retirar(monto);
                    if (exito) {
                        areaResultado.setText("Retiro exitoso. Nuevo saldo: $" + cuenta.getSaldo());
                    } else {
                        areaResultado.setText("Fondos insuficientes.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Monto inválido");
                }
                break;

            case "Ver Historial":
                areaResultado.setText(cuenta.obtenerHistorial());
                break;

            default:
                areaResultado.setText("Acción no reconocida.");
        }
    }

    public static void main(String[] args) {
        Banco banco = new Banco();
        Cliente cliente = new Cliente("Juan Pérez", new CuentaBancaria("123456"));
        banco.agregarCliente(cliente);

        SwingUtilities.invokeLater(() -> {
            new VentanaGrafica(banco, cliente).setVisible(true);
        });
    }
}
