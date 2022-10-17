import components.PersonalizedButton;
import components.PersonalizedFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends PersonalizedFrame {

    public LoginFrame() {
        super("Entrar", 300, 100, 700, 550);
        // se puede introducir html básico en un JLabel, esto lo hago para que permita saltos de línea con la etiqueta <br />
        JLabel etiquetaInformacion = new JLabel("<html>IMPORTANTE: Accede con un usuario que tenga<br /> privilegios para otorgar privilegios (GRANT)<br /> y " +
                "para revocarlos (REVOKE)</html>");
        etiquetaInformacion.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        etiquetaInformacion.setBounds(100, 20, 600, 100);
        JLabel etiquetaUsuario = new JLabel("Usuario");
        etiquetaUsuario.setBounds(100, 160, 100, 30);
        JTextField campoUsuario = new JTextField("root");
        campoUsuario.setBounds(210, 160, 180, 30);
        JLabel etiquetaContrasenia = new JLabel("Contraseña");
        etiquetaContrasenia.setBounds(100, 210, 100, 30);
        JPasswordField campoContrasenia = new JPasswordField();
        campoContrasenia.setBounds(210, 210, 180, 30);
        JLabel etiquetaServidor = new JLabel("Servidor");
        etiquetaServidor.setBounds(100, 260, 100, 30);
        JTextField campoServidor = new JTextField("localhost");
        campoServidor.setBounds(210, 260, 180, 30);
        JLabel etiquetaPuerto = new JLabel("Puerto");
        etiquetaPuerto.setBounds(100, 310, 100, 30);
        JTextField campoPuerto = new JTextField("3306");
        campoPuerto.setBounds(210, 310, 180, 30);
        PersonalizedButton botonConectarse = new PersonalizedButton("Conectarse", Color.green, 350, 380, 80, 25);
        botonConectarse.addActionListener((ActionListener) -> {
            String nombreUsuario = campoUsuario.getText();
            // getPassword() devuelve un arreglo de caracteres, por lo que hay que convertirlo a un String
            String contrasenia = String.valueOf(campoContrasenia.getPassword());
            String servidor = campoServidor.getText();
            String puerto = campoPuerto.getText();
            realizarConexion(nombreUsuario, contrasenia, servidor, puerto, this);
        });
        PersonalizedButton botonSalir = new PersonalizedButton("Salir", Color.red, 450, 380, 80, 25);
        botonSalir.addActionListener((ActionListener) -> {
            Functions.salir();
        });

        add(etiquetaInformacion);
        add(etiquetaUsuario);
        add(campoUsuario);
        add(etiquetaContrasenia);
        add(campoContrasenia);
        add(etiquetaServidor);
        add(campoServidor);
        add(etiquetaPuerto);
        add(campoPuerto);
        add(botonConectarse);
        add(botonSalir);
    }

    private static void realizarConexion(String nombreUsuario, String contrasenia, String servidor, String puerto, LoginFrame loginFrame) {
        // jdbc:mysql://localhost:3306/?user=root
        String url = String.format("jdbc:mysql://%s:%s/?user=%s", servidor, puerto, nombreUsuario);
        Connection connection = new Connection(url, nombreUsuario, contrasenia, servidor, loginFrame);
    }

}
