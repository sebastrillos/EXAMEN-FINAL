import java.util.ArrayList;
import java.util.List;

public class CuentaBancaria {
    private String numeroCuenta;
    private double saldo;
    private List<Transaccion> historial;

    public CuentaBancaria(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0.0;
        this.historial = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            historial.add(new Transaccion("Depósito", monto));
        }
    }

    public boolean retirar(double monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            historial.add(new Transaccion("Retiro", monto));
            return true;
        }
        return false;
    }

    public String obtenerHistorial() {
        StringBuilder sb = new StringBuilder();
        for (Transaccion t : historial) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }
}

