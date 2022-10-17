import components.PersonalizedButton;
import components.PersonalizedFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCreationFrame extends PersonalizedFrame {
    public UserCreationFrame(Statement statement, String server) {
        // llamo al constructor de PersonalizedFrame para asignarle estos atributos personalizados a la ventana
        super("Creación de usuarios", 30, 30, 400, 320);
        JLabel etiquetaUserName = new JLabel("Introduce el nombre de usuario");
        etiquetaUserName.setBounds(100, 40, 180, 30);
        JTextField campoUserName = new JTextField();
        campoUserName.setBounds(100, 80, 180, 30);
        JLabel contrasenia = new JLabel("Introduce la contraseña");
        contrasenia.setBounds(100, 120, 180, 30);
        JPasswordField campoContrasenia = new JPasswordField();
        campoContrasenia.setBounds(100, 160, 180, 30);
        PersonalizedButton botonCrear = new PersonalizedButton("Crear", Color.green, 140, 220, 85, 25);
        botonCrear.addActionListener((ActionListener) -> {
            boolean usuarioCreadoExitosamente = crearUsuario(campoUserName.getText(), campoContrasenia.getPassword(), server, statement);
            if (usuarioCreadoExitosamente) {
                setVisible(false);
            }
        });
        PersonalizedButton botonCancelar = new PersonalizedButton("Cancelar", Color.RED, 235, 220, 85, 25);
        botonCancelar.addActionListener((ActionListener) -> {
            setVisible(false);
        });
        add(etiquetaUserName);
        add(campoUserName);
        add(contrasenia);
        add(campoContrasenia);
        add(botonCrear);
        add(botonCancelar);
    }

    public static boolean crearUsuario(String userName, char[] password, String server, Statement statement) {
        if (userName.equals("")) {
            System.out.println("Nombre de usuario requerido");
            return false;
        }
        if (password.length == 0) {
            System.out.println("Contraseña requerida");
            return false;
        }
        // convierte el array de caracteres en un String
        String realPassword = String.valueOf(password);
        /*
        for (char caracter : password) {
            realPassword += caracter;
        }
         */
        String query = String.format("CREATE USER '%s'@'%s' IDENTIFIED BY '%s';", userName, server, realPassword);
        try {
            statement.execute(query);
            return true;
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return  false;
        }

    }
}
