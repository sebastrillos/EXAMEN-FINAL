# CUENTA BANCARIA (Bancotech)

**Integrantes**

LUIS FELIPE CUADRRO, 192533<br>
SEBASTIAN TRILLOS, 192477<br>
JORGE CASTILLA, 192495<br>


## ROLES
JHON SEBASTIAN : LOGICA Y VENTANAS GRAFICAS<br>
JOSE JORGE : LOGICA Y PERSISTENCIA DE DATOS <br>
LUIS FELIPE : LOGICA ,DOCUMENTACION Y PRUEBAS<br>



## Descripción del problema

El código  implementa un simulador de banca en línea en Java, en donde los usuarios (clientes) pueden  
Registrarse y autenticarse con un PIN.  
Crear y gestionar cuentas bancarias de tipo "ahorro" o "corriente".  
Realizar operaciones como depósitos, retiros, transferencias y ver el historial de transacciones.  
Simular el cálculo de un préstamo.  



## Solucion del codigo.

1. Prevenir clientes duplicados<br>
En el método registrarCliente(), antes de registrar un nuevo cliente, se valida si ya existe uno con la misma cédula. Si es así, se muestra un mensaje de advertencia y se cancela el registro.<br>

2. Validar correctamente el tipo de cuenta<br>
Cuando el usuario ingresa el tipo de cuenta (ahorro o corriente), se fuerza a que escriba correctamente. Si escribe algo mal, se le vuelve a preguntar hasta que lo haga bien.<br>

3. Validar que los montos sean mayores a cero<br>
Se modifica el método obtenerMonto() para que verifique que el monto ingresado sea mayor a cero. Si no lo es, se muestra un mensaje de error y se retorna un valor inválido (-1) para evitar que la operación continúe.<br>

4. Confirmar operaciones exitosas<br>
Después de un depósito, retiro o transferencia, el programa informa al usuario que la operación fue exitosa y muestra el nuevo saldo de la cuenta involucrada. Esto mejora la retroalimentación del sistema.<br>

5. Explicar mejor el cálculo del préstamo<br>
Se agregan mensajes aclaratorios para indicar que el cálculo del préstamo es una estimación y que se basa en una fórmula de interés compuesto mensual. Esto ayuda al usuario a comprender mejor el resultado<br>


## Datos Iniciales
El sistema incluye datos de prueba:<br>

Cliente: Juan Perez (cédula: 123, PIN: 1111)<br>

1 cuenta de ahorro (tasa 5%)<br>

1 cuenta corriente (límite $500)<br>

Cliente: Maria Lopez (cédula: 456, PIN: 2222)<br>

1 cuenta de ahorro (tasa 3%)<br>


## Características Principales

Registro de nuevos clientes con cédula, nombre y PIN<br>

Inicio de sesión seguro con validación de credenciales<br>



## Uso Básico

Registrar un nuevo cliente:<br>

Seleccionar "Registrar nuevo cliente"<br>

Ingresar cédula, nombre completo y PIN de 4 dígitos<br>


## Iniciar sesión:

Seleccionar "Iniciar sesión"<br>

Ingresar cédula y PIN registrados<br>

Operaciones bancarias:<br>

Crear cuentas (ahorro/corriente)<br>

Realizar depósitos y retiros<br>

Transferir entre cuentas<br>

Consultar historial<br>


## Descripción

BancoTech es una aplicación de banca en línea desarrollada en Java con interfaz gráfica Swing que permite:<br>

Registro y autenticación de clientes<br>

Gestión de cuentas (ahorro y corriente)<br>

Operaciones bancarias básicas (depósitos, retiros, transferencias)<br>

Consulta de historial de transacciones<br>

Cálculo de préstamos<br>

Gestión de Cuentas<br>

Creación de cuentas de ahorro (con tasa de interés)<br>

Creación de cuentas corrientes (con límite de sobregiro)<br>

Visualización de saldos y cuentas asociadas<br>

Operaciones Bancarias<br>

Depósitos y retiros de dinero<br>

Transferencias entre cuentas<br>

Historial completo de transacciones<br>

Cálculo de cuotas para préstamos<br>

## Tecnologías Utilizadas

Lenguaje: Java <br>

Interfaz Gráfica: Java Swing<br>

Gestión de Dependencias: Java estándar<br>

Control de Versiones: Git<br>

## EXPLIACACION DEL CODIGO

**1. public static void main(String[] args)**

Función: Punto de entrada del programa<br>

Detalle:<br>

Carga datos iniciales (clientes demo)<br>

Muestra menú principal en bucle<br>

Gestiona la navegación entre opciones<br>

Cierra recursos al salir<br>


private static void cargarDatosIniciales()<br>
Función: Crea datos de prueba<br>

Detalle:<br>

Crea 2 clientes (Juan y María)<br>

Asigna cuentas de ahorro/corriente<br>

Usa tasas de interés y límites de ejemplo<br>

**2. Métodos de menú y Navegación**

private static void mostrarMenuPrincipal()
Función: Muestra las opciones principales

Salida:

1. Registrar nuevo cliente
2. Iniciar sesión
3. Acceder a operaciones
4. Salir

private static int obtenerOpcion()
Función: Lee la selección del usuario

Control de errores:

Maneja entradas no numéricas

Limpia el buffer del scanner

**3. Métodos de Gestión de Clientes**
java
private static void registrarCliente()
Proceso:

Pide cédula, nombre y PIN

Crea objeto Cliente

Lo registra en el banco

Validaciones: Ninguna (en versión básica)


private static void iniciarSesion()
Proceso:

Solicita cédula y PIN

Busca cliente en el banco

Verifica coincidencia de PIN

Establece clienteLogueado si es correcto

**4. Métodos de Operaciones Bancarias (requieren sesión)**

private static void menuOperacionesCliente()
Función: Menú de operaciones post-login

Opciones:

Gestión de cuentas

Transacciones

Consultas

Cierre de sesión


private static void crearNuevaCuenta()
Proceso:

Pide tipo de cuenta (ahorro/corriente)

Solicita parámetros específicos:

Ahorro: Tasa de interés

Corriente: Límite de sobregiro

Llama a banco.crearCuenta()


private static void verCuentasYSaldos()
Salida: Lista todas las cuentas del cliente con formato:

CuentaBancaria{Número='A1B2C3D4', Saldo=100.00, Tipo='Ahorro'}


**5. Métodos de Transacciones**

private static void realizarDeposito()
Proceso:

Valida que la cuenta pertenezca al cliente

Asegura monto positivo

Ejecuta cuenta.depositar(monto)


private static void realizarRetiro()
Proceso similar a deposito:

Usa cuenta.retirar(monto)

En cuentas corrientes valida límite de sobregiro


private static void realizarTransferencia()
Validaciones extra:

Cuenta destino existe

No es transferencia a misma cuenta

Saldo suficiente en origen

**6. Métodos de Consulta**

private static void verHistorialTransacciones()
Salida: Muestra cada transacción con formato:

Tipo: DEPÓSITO, Monto: 100.00, Fecha: 05/06/2023 14:30:00, Cuenta: A1B2C3D4

private static void calcularPrestamo()
Cálculo: Usa fórmula de amortización


pago = (monto * tasaMensual) / (1 - Math.pow(1 + tasaMensual, -periodos))
Salida: Muestra cuota mensual formateada (ej: 256.43)

**7. Métodos de Utilidad**

private static double obtenerMonto()
Función: Lee cantidades monetarias

Control de errores:

Maneja entradas no numéricas

Retorna -1 en caso de error

Flujo Típico de Uso
Registro:

registrarCliente() → banco.agregarCliente()
Operación Bancaria:

realizarDeposito() → cuenta.depositar() → transaccion.guardar()
Consulta:

verHistorialTransacciones() → cuenta.getHistorial() → transaccion.toString()
Características Clave
Seguridad Básica:

Valida propiedad de cuentas antes de operar

Verifica PIN en login