
public class App {
    public static void main(String[] args) {
        Banco banco = new Banco();

        // Crear un cliente de ejemplo con una cuenta
        CuentaBancaria cuenta = new CuentaBancaria("12345678");
        Cliente cliente = new Cliente("Juan Pérez", cuenta);

        banco.agregarCliente(cliente);

        // Mostrar interfaz gráfica
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaGrafica(banco, cliente).setVisible(true);
        });
    }
}
