import java.util.HashMap;
import java.util.Scanner;

public class Cliente {
    
    private  HashMap<String, Usuarios> listaUsuarios = new HashMap<>();

    public void agregarCuenta() {
        Scanner input = new Scanner(System.in);
        System.out.println("A continuación, ingrese sus datos para crear su cuenta: ");
        
        while (true) { 
            System.out.print("Nombre completo: ");
            String nombre = input.nextLine();
            
            System.out.print("Número de cédula: ");
            String cedula = input.nextLine();
            
            System.out.print("Ingrese una contraseña de cuatro números: ");
            while (!input.hasNextInt()) {
                System.out.println("Error al ingresar la contraseña! Debe ser un número.");
                input.next(); 
                System.out.print("Ingrese una contraseña de cuatro números: ");
            }
            int contraceña = input.nextInt();
            input.nextLine(); 
            
            
            Usuarios nuevoUsuario = new Usuarios(nombre, cedula, contraceña);
            
            listaUsuarios.put(cedula, nuevoUsuario);
            
            System.out.println("Usuario agregado exitosamente.");
           break;
        }
    }

    public void mostrarInformacion() {
        for (Usuarios usuario : listaUsuarios.values()) {
            System.out.println("Nombre: " + usuario.getNombre() + ", Cédula: " + usuario.getCedula());
        }
    }
}


 class Usuarios {
    private String nombre;
    private String cedula;
    private int contrasena; 

    // Constructor
    public Usuarios(String nombre, String cedula, int contrasena) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.contrasena = contrasena;
    }

    
    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public int getContrasena() { 
        return contrasena;
    }

 
}
