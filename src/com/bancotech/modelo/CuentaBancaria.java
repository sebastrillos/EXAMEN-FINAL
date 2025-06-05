package com.bancotech.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class CuentaBancaria {
    protected String numeroCuenta;
    protected double saldo;
    protected Cliente titular; 
    protected List<Transaccion> historialTransacciones;

    public CuentaBancaria(String numeroCuenta, Cliente titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.historialTransacciones = new ArrayList<>();
    }


    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public Cliente getTitular() {
        return titular;
    }

    public List<Transaccion> getHistorialTransacciones() {
        return historialTransacciones;
    }

   
    public abstract String getTipoCuenta();

    public boolean depositar(double monto) {
        if (monto <= 0) {
            System.out.println("Error: El monto a depositar debe ser positivo.");
            return false;
        }
        this.saldo += monto;
        this.historialTransacciones.add(new Transaccion(
            "DEPÓSITO", monto, LocalDateTime.now(), this.numeroCuenta, this.numeroCuenta
        ));
        System.out.println("Depósito de " + monto + " realizado. Nuevo saldo: " + this.saldo);
        return true;
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            System.out.println("Error: El monto a retirar debe ser positivo.");
            return false;
        }
        if (monto > this.saldo) {
            System.out.println("Error: Saldo insuficiente. Saldo actual: " + this.saldo);
            return false;
        }
        this.saldo -= monto;
        this.historialTransacciones.add(new Transaccion(
            "RETIRO", monto, LocalDateTime.now(), this.numeroCuenta, this.numeroCuenta
        ));
        System.out.println("Retiro de " + monto + " realizado. Nuevo saldo: " + this.saldo);
        return true;
    }

    public boolean transferir(CuentaBancaria cuentaDestino, double monto) {
        if (monto <= 0) {
            System.out.println("Error: El monto a transferir debe ser positivo.");
            return false;
        }
        if (monto > this.saldo) {
            System.out.println("Error: Saldo insuficiente para la transferencia. Saldo actual: " + this.saldo);
            return false;
        }
        
        
        this.saldo -= monto;
        this.historialTransacciones.add(new Transaccion(
            "TRANSFERENCIA_SALIDA", monto, LocalDateTime.now(), this.numeroCuenta, cuentaDestino.getNumeroCuenta()
        ));
        
    
        cuentaDestino.saldo += monto;
        cuentaDestino.historialTransacciones.add(new Transaccion(
            "TRANSFERENCIA_ENTRADA", monto, LocalDateTime.now(), this.numeroCuenta, cuentaDestino.getNumeroCuenta()
        ));

        System.out.println("Transferencia de " + monto + " de " + this.numeroCuenta + " a " + cuentaDestino.getNumeroCuenta() + " realizada.");
        System.out.println("Nuevo saldo en " + this.numeroCuenta + ": " + this.saldo);
        System.out.println("Nuevo saldo en " + cuentaDestino.getNumeroCuenta() + ": " + cuentaDestino.getSaldo());
        return true;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
               "Número='" + numeroCuenta + '\'' +
               ", Saldo=" + String.format("%.2f", saldo) +
               ", Tipo='" + getTipoCuenta() + '\'' +
               '}';
    }
}