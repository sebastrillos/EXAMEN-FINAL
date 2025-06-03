import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VentanaGrafica {

    private Cliente acciones;
    private JPanel panel;
    private JTextArea areaInformacion;

    public VentanaGrafica() {
        this.acciones = new Cliente();
    }
   
    public void inicio() {
        JFrame ventana = new JFrame("Bancotech");
        ventana.setSize(400, 300);
        ventana.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bienvenido a Bancotech", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        ventana.add(titulo, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton registrar = new JButton("Registrarme");
        JButton iniciarSeccion = new JButton("Iniciar Sección");
        JButton salir = new JButton("Salir");

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acciones.agregarCuenta();
            }
        });

        iniciarSeccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInformacion();
            }
        });

        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(registrar);
        panel.add(iniciarSeccion);
        panel.add(salir);
        ventana.add(panel, BorderLayout.CENTER);

        areaInformacion = new JTextArea();
        areaInformacion.setEditable(false);
        ventana.add(areaInformacion, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }

    private void mostrarInformacion() {
        StringBuilder informacion = new StringBuilder();
        for (Usuarios usuario : acciones.ListaUsuarios().values()) {
            informacion.append("Nombre: ").append(usuario.getNombre())
                       .append(", Cédula: ").append(usuario.getCedula()).append("\n");
        }
        areaInformacion.setText(informacion.toString());
        panel.setVisible(false);
    }
}





