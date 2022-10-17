import components.PersonalizedButton;
import components.PersonalizedFrame;

import javax.swing.*;
import java.awt.*;

public class GUIPrincipal extends PersonalizedFrame {

    public GUIPrincipal(Connection connection, String server) {
        // llamo al constructor de la clase padre para personalizar esta ventana que comparte varias cosas en común, con otras ventanas
        super("Manejador de usuarios", 250, 100, 800, 550);
        setBackground(Color.lightGray);

        JLabel imagenIzquierda = new JLabel();
        ImageIcon iconoIzquierdo = new ImageIcon(getClass().getResource("images/imagenPrincipal.png"));
        imagenIzquierda.setIcon(iconoIzquierdo);
        imagenIzquierda.setBounds(50, 30, iconoIzquierdo.getIconWidth(), iconoIzquierdo.getIconHeight());

        JLabel imagenDerecha = new JLabel();
        ImageIcon iconoDerecho = new ImageIcon(getClass().getResource("images/imagenPrincipal2.png"));
        imagenDerecha.setIcon(iconoDerecho);
        imagenDerecha.setBounds(550, 100, iconoDerecho.getIconWidth(), iconoDerecho.getIconHeight());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        PersonalizedButton botonCrearUsuario = new PersonalizedButton("Crear usuario", Color.orange, 340, 40, 150, 40);
        botonCrearUsuario.addActionListener((ActionListener) -> {
            UserCreationFrame interfazDeCreacion = new UserCreationFrame(connection.getStatement(), server);
        });
        PersonalizedButton botonOtorgarPrivilegios = new PersonalizedButton("Otorgar privilegios", Color.orange, 340, 100, 150, 40);
        botonOtorgarPrivilegios.addActionListener((ActionListener) -> {
            GrantPrivilegesFrame interfazDeOtorgarPrivilegios = new GrantPrivilegesFrame(server, connection.getStatement());
        });

        PersonalizedButton botonRevocarPrivilegios = new PersonalizedButton("Revocar privilegios", Color.orange, 340, 160, 150, 40);
        botonRevocarPrivilegios.addActionListener((ActionListener) -> {
            RevokePrivilegesFrame interfazDeRevocarPrivilegios = new RevokePrivilegesFrame(server, connection.getStatement());
        });

        PersonalizedButton botonBorrarUsuario = new PersonalizedButton("Borrar usuario", Color.orange, 340, 220, 150, 40);
        botonBorrarUsuario.addActionListener((ActionListener) -> {
            UserDeletionFrame interfazDeBorrado = new UserDeletionFrame(connection.getStatement(), server);
        });
        PersonalizedButton botonSalir = new PersonalizedButton("Salir", Color.RED, 340, 300, 125, 40);
        botonSalir.addActionListener((ActionListener) -> {
            Functions.salir();
        });
        add(imagenIzquierda);
        add(imagenDerecha);
        add(botonCrearUsuario);
        add(botonBorrarUsuario);
        add(botonOtorgarPrivilegios);
        add(botonRevocarPrivilegios);
        add(botonSalir);
    }



}
