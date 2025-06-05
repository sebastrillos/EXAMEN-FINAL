package com.bancotech.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    private String tipo; 
    private double monto;
    private LocalDateTime fecha;
    private String cuentaOrigen;
    private String cuentaDestino; 

    public Transaccion(String tipo, double monto, LocalDateTime fecha, String cuentaOrigen, String cuentaDestino) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }


    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = fecha.format(formatter);

        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: ").append(tipo)
          .append(", Monto: ").append(String.format("%.2f", monto))
          .append(", Fecha: ").append(fechaFormateada);

        if (tipo.contains("TRANSFERENCIA")) {
            sb.append(", Origen: ").append(cuentaOrigen)
              .append(", Destino: ").append(cuentaDestino);
        } else {
            sb.append(", Cuenta: ").append(cuentaOrigen);
        }
        return sb.toString();
    }
}