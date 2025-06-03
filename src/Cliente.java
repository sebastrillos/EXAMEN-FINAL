public class Cliente {
    private String nombre;
    private CuentaBancaria cuenta;

    public Cliente(String nombre, CuentaBancaria cuenta) {
        this.nombre = nombre;
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }
}