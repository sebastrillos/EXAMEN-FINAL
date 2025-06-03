package com.bancotech.app;

import com.bancotech.modelo.Cliente;
import com.bancotech.modelo.CuentaBancaria;
import com.bancotech.modelo.Transaccion;
import com.bancotech.servicio.Banco;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Banco banco = new Banco();
    private static Cliente clienteLogueado = null; 

    public static void main(String[] args) {
  
        cargarDatosIniciales();

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = obtenerOpcion();

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    if (clienteLogueado != null) {
                        menuOperacionesCliente();
                    } else {
                        System.out.println("Por favor, inicie sesión primero.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del simulador. ¡Gracias por usar BancoTech!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
            System.out.println("\n"); 
        } while (opcion != 4);

        scanner.close();
    }

    private static void cargarDatosIniciales() {
       
        Cliente c1 = new Cliente("123", "Juan Perez", "1111");
        banco.agregarCliente(c1);
        banco.crearCuenta(c1, "ahorro", 0.05, 0); 
        banco.crearCuenta(c1, "corriente", 0, 500.0); 

        Cliente c2 = new Cliente("456", "Maria Lopez", "2222");
        banco.agregarCliente(c2);
        banco.crearCuenta(c2, "ahorro", 0.03, 0);
    }


    private static void mostrarMenuPrincipal() {
        System.out.println("===== BIENVENIDO A BANCOTECH =====");
        System.out.println("1. Registrar nuevo cliente");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Acceder a operaciones (Requiere sesión)");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcion() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            scanner.next(); 
            return -1; 
        } finally {
            scanner.nextLine(); 
        }
    }

    private static void registrarCliente() {
        System.out.println("\n--- REGISTRO DE NUEVO CLIENTE ---");
        System.out.print("Ingrese Cedula del cliente: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Establezca un PIN (4 dígitos, simulación): ");
        String pin = scanner.nextLine();
        
        Cliente nuevoCliente = new Cliente(id, nombre, pin);
        banco.agregarCliente(nuevoCliente);
    }

    private static void iniciarSesion() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese su Cedula de cliente: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese su PIN: ");
        String pin = scanner.nextLine();

        Cliente cliente = banco.buscarCliente(id);
        if (cliente != null && cliente.validarPin(pin)) {
            clienteLogueado = cliente;
            System.out.println("¡Bienvenido, " + clienteLogueado.getNombre() + "!");
        } else {
            System.out.println("Cedula o PIN incorrectos. Intente de nuevo.");
            clienteLogueado = null;
        }
    }

    private static void menuOperacionesCliente() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ DE OPERACIONES DE CLIENTE (" + clienteLogueado.getNombre() + ") ---");
            System.out.println("1. Crear nueva cuenta");
            System.out.println("2. Ver mis cuentas y saldos");
            System.out.println("3. Depositar dinero");
            System.out.println("4. Retirar dinero");
            System.out.println("5. Transferir dinero");
            System.out.println("6. Ver historial de transacciones de una cuenta");
            System.out.println("7. Calcular préstamo (Opcional)");
            System.out.println("8. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            opcion = obtenerOpcion();

            switch (opcion) {
                case 1:
                    crearNuevaCuenta();
                    break;
                case 2:
                    verCuentasYSaldos();
                    break;
                case 3:
                    realizarDeposito();
                    break;
                case 4:
                    realizarRetiro();
                    break;
                case 5:
                    realizarTransferencia();
                    break;
                case 6:
                    verHistorialTransacciones();
                    break;
                case 7:
                
                    calcularPrestamo();
                    break;
                case 8:
                    System.out.println("Cerrando sesión para " + clienteLogueado.getNombre() + ".");
                    clienteLogueado = null;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 8);
    }

    private static void crearNuevaCuenta() {
        System.out.println("\n--- CREAR NUEVA CUENTA ---");
        System.out.print("Tipo de cuenta (ahorro/corriente): ");
        String tipo = scanner.nextLine();

        double tasaInteres = 0;
        double limiteSobregiro = 0;

        if (tipo.equalsIgnoreCase("ahorro")) {
            System.out.print("Ingrese tasa de interés (ej. 0.05 para 5%): ");
            try {
                tasaInteres = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Se usará 0% de interés.");
            } finally {
                scanner.nextLine();
            }
        } else if (tipo.equalsIgnoreCase("corriente")) {
            System.out.print("Ingrese límite de sobregiro (ej. 500.0): ");
            try {
                limiteSobregiro = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Se usará 0 de límite de sobregiro.");
            } finally {
                scanner.nextLine();
            }
        } else {
            System.out.println("Tipo de cuenta no reconocido.");
            return;
        }

        banco.crearCuenta(clienteLogueado, tipo, tasaInteres, limiteSobregiro);
    }

    private static void verCuentasYSaldos() {
        System.out.println("\n--- MIS CUENTAS Y SALDOS ---");
        if (clienteLogueado.getCuentas().isEmpty()) {
            System.out.println("No tiene cuentas registradas.");
            return;
        }
        for (CuentaBancaria cuenta : clienteLogueado.getCuentas()) {
            System.out.println("- " + cuenta);
        }
    }

    private static void realizarDeposito() {
        System.out.println("\n--- REALIZAR DEPÓSITO ---");
        System.out.print("Ingrese el número de cuenta donde desea depositar: ");
        String numCuenta = scanner.nextLine();
        CuentaBancaria cuenta = banco.buscarCuenta(numCuenta);

        if (cuenta == null || !clienteLogueado.getCuentas().contains(cuenta)) {
            System.out.println("Cuenta no encontrada o no pertenece a este cliente.");
            return;
        }

        System.out.print("Ingrese el monto a depositar: ");
        double monto = obtenerMonto();
        if (monto > 0) {
            cuenta.depositar(monto);
        } else {
            System.out.println("Monto de depósito inválido.");
        }
    }

    private static void realizarRetiro() {
        System.out.println("\n--- REALIZAR RETIRO ---");
        System.out.print("Ingrese el número de cuenta de la que desea retirar: ");
        String numCuenta = scanner.nextLine();
        CuentaBancaria cuenta = banco.buscarCuenta(numCuenta);

        if (cuenta == null || !clienteLogueado.getCuentas().contains(cuenta)) {
            System.out.println("Cuenta no encontrada o no pertenece a este cliente.");
            return;
        }

        System.out.print("Ingrese el monto a retirar: ");
        double monto = obtenerMonto();
        if (monto > 0) {
            cuenta.retirar(monto);
        } else {
            System.out.println("Monto de retiro inválido.");
        }
    }

    private static void realizarTransferencia() {
        System.out.println("\n--- REALIZAR TRANSFERENCIA ---");
        System.out.print("Ingrese el número de su cuenta de ORIGEN: ");
        String numCuentaOrigen = scanner.nextLine();
        CuentaBancaria cuentaOrigen = banco.buscarCuenta(numCuentaOrigen);

        if (cuentaOrigen == null || !clienteLogueado.getCuentas().contains(cuentaOrigen)) {
            System.out.println("Cuenta de origen no encontrada o no pertenece a este cliente.");
            return;
        }

        System.out.print("Ingrese el número de cuenta de DESTINO: ");
        String numCuentaDestino = scanner.nextLine();
        CuentaBancaria cuentaDestino = banco.buscarCuenta(numCuentaDestino);

        if (cuentaDestino == null) {
            System.out.println("Cuenta de destino no encontrada.");
            return;
        }

        if (cuentaOrigen.equals(cuentaDestino)) {
            System.out.println("No se puede transferir a la misma cuenta.");
            return;
        }

        System.out.print("Ingrese el monto a transferir: ");
        double monto = obtenerMonto();
        if (monto > 0) {
            cuentaOrigen.transferir(cuentaDestino, monto);
        } else {
            System.out.println("Monto de transferencia inválido.");
        }
    }

    private static void verHistorialTransacciones() {
        System.out.println("\n--- HISTORIAL DE TRANSACCIONES ---");
        System.out.print("Ingrese el número de cuenta para ver su historial: ");
        String numCuenta = scanner.nextLine();
        CuentaBancaria cuenta = banco.buscarCuenta(numCuenta);

        if (cuenta == null || !clienteLogueado.getCuentas().contains(cuenta)) {
            System.out.println("Cuenta no encontrada o no pertenece a este cliente.");
            return;
        }

        if (cuenta.getHistorialTransacciones().isEmpty()) {
            System.out.println("No hay transacciones para esta cuenta.");
            return;
        }

        System.out.println("Historial de la cuenta " + numCuenta + ":");
        for (Transaccion transaccion : cuenta.getHistorialTransacciones()) {
            System.out.println("- " + transaccion);
        }
    }

    private static void calcularPrestamo() {
        System.out.println("\n--- CÁLCULO DE PRÉSTAMO (OPCIONAL) ---");
        System.out.print("Ingrese el monto del préstamo: ");
        double monto = obtenerMonto();
        if (monto <= 0) {
            System.out.println("Monto de préstamo inválido.");
            return;
        }

        System.out.print("Ingrese el número de meses (periodos): ");
        int periodos = -1;
        try {
            periodos = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida para periodos.");
        } finally {
            scanner.nextLine();
        }

        if (periodos <= 0) {
            System.out.println("Número de periodos inválido.");
            return;
        }

        System.out.print("Ingrese la tasa de interés ANUAL (ej. 5 para 5%): ");
        double tasaAnual = -1;
        try {
            tasaAnual = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida para tasa de interés.");
        } finally {
            scanner.nextLine();
        }

        if (tasaAnual < 0) {
            System.out.println("Tasa de interés inválida.");
            return;
        }

        double pagoMensual = banco.calcularPagoMensualPrestamo(monto, periodos, tasaAnual);
        System.out.printf("El pago mensual estimado para el préstamo es: %.2f%n", pagoMensual);
        System.out.println("Tenga en cuenta que este es un cálculo simplificado.");
    }


    private static double obtenerMonto() {
        double monto = -1;
        try {
            monto = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
        } finally {
            scanner.nextLine(); 
        }
        return monto;
    }
}