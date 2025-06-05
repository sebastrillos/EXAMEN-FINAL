package com.bancotech.modelo;

public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres; 

    public CuentaAhorro(String numeroCuenta, Cliente titular, double tasaInteres) {
        super(numeroCuenta, titular);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public String getTipoCuenta() {
        return "Ahorro";
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

   
    public void aplicarIntereses() {

    }
}