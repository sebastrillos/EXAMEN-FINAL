package com.bancotech.modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String cedula;
    private String nombre;
    private String pin; 
    private List<CuentaBancaria> cuentas;

    public Cliente(String cedula, String nombre, String pin) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.pin = pin;
        this.cuentas = new ArrayList<>();
    }

  
    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

   
    public boolean validarPin(String pinIngresado) {
        return this.pin.equals(pinIngresado);
    }

    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    
    public void agregarCuenta(CuentaBancaria cuenta) {
        this.cuentas.add(cuenta);
    }

  

    @Override
    public String toString() {
        return "Cliente{" +
               "Cedula='" + cedula + '\'' +
               ", Nombre='" + nombre + '\'' +
               ", Cuentas=" + cuentas.size() +
               '}';
    }
}