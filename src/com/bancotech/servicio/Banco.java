package com.bancotech.servicio;

import com.bancotech.modelo.Cliente;
import com.bancotech.modelo.CuentaBancaria;
import com.bancotech.modelo.CuentaAhorro;
import com.bancotech.modelo.CuentaCorriente;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID; 

public class Banco {
    private Map<String, Cliente> clientes; 
    private Map<String, CuentaBancaria> cuentas; 

    public Banco() {
        this.clientes = new HashMap<>();
        this.cuentas = new HashMap<>();
    }

    public Cliente buscarCliente(String idCliente) {
        return clientes.get(idCliente);
    }

    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        return cuentas.get(numeroCuenta);
    }

    public boolean agregarCliente(Cliente cliente) {
        if (clientes.containsKey(cliente.getCedula())) {
            System.out.println("Error: Ya existe un cliente con el ID " + cliente.getCedula());
            return false;
        }
        clientes.put(cliente.getCedula(), cliente);
        System.out.println("Cliente " + cliente.getNombre() + " agregado con éxito.");
        return true;
    }

    public String crearCuenta(Cliente cliente, String tipoCuenta, double tasaInteresOpcional, double limiteSobregiroOpcional) {
        
        String numeroCuenta = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); 
        CuentaBancaria nuevaCuenta = null;

        switch (tipoCuenta.toLowerCase()) {
            case "ahorro":
                nuevaCuenta = new CuentaAhorro(numeroCuenta, cliente, tasaInteresOpcional);
                break;
            case "corriente":
                nuevaCuenta = new CuentaCorriente(numeroCuenta, cliente, limiteSobregiroOpcional);
                break;
            default:
                System.out.println("Tipo de cuenta no válido.");
                return null;
        }

        if (nuevaCuenta != null) {
            cuentas.put(numeroCuenta, nuevaCuenta);
            cliente.agregarCuenta(nuevaCuenta);
            System.out.println("Cuenta " + tipoCuenta + " creada con éxito para " + cliente.getNombre() + ". Número de cuenta: " + numeroCuenta);
            return numeroCuenta;
        }
        return null;
    }

   
    public double calcularPagoMensualPrestamo(double monto, int periodosMeses, double tasaInteresAnual) {
        double tasaInteresMensual = (tasaInteresAnual / 100) / 12;
        if (tasaInteresMensual == 0) {
            return monto / periodosMeses; 
        }
        return (monto * tasaInteresMensual) / (1 - Math.pow(1 + tasaInteresMensual, -periodosMeses));
    }
}