package com.bancotech.modelo;

public class CuentaCorriente extends CuentaBancaria {
    private double limiteSobregiro; 

    public CuentaCorriente(String numeroCuenta, Cliente titular, double limiteSobregiro) {
        super(numeroCuenta, titular);
        this.limiteSobregiro = limiteSobregiro;
    }

    @Override
    public String getTipoCuenta() {
        return "Corriente";
    }

    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }

    
    @Override
    public boolean retirar(double monto) {
        if (monto <= 0) {
            System.out.println("Error: El monto a retirar debe ser positivo.");
            return false;
        }
        
        if (this.saldo - monto < -this.limiteSobregiro) {
            System.out.println("Error: Límite de sobregiro excedido. Saldo actual: " + this.saldo + ", Límite de sobregiro: " + this.limiteSobregiro);
            return false;
        }
        this.saldo -= monto;
        this.historialTransacciones.add(new Transaccion(
            "RETIRO", monto, java.time.LocalDateTime.now(), this.numeroCuenta, this.numeroCuenta
        ));
        System.out.println("Retiro de " + monto + " realizado. Nuevo saldo: " + this.saldo);
        return true;
    }
}