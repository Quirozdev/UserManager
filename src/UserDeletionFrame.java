import components.PersonalizedButton;
import components.PersonalizedFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDeletionFrame extends PersonalizedFrame {
    public UserDeletionFrame(Statement statement, String server) {
        super("Borrar usuario", 30, 30, 400, 320);
        JLabel etiquetaUsuario = new JLabel("Usuario");
        etiquetaUsuario.setBounds(140, 10, 80, 25);
        JComboBox<String> usuarios = new JComboBox<>();
        usuarios.setBounds(100, 45, 150, 30);
        Functions.construirComboBoxUsuarios(usuarios, statement);
        PersonalizedButton botonBorrar = new PersonalizedButton("Borrar", Color.yellow, 150, 140, 85, 25);
        botonBorrar.addActionListener((ActionListener) -> {
            borrarUsuario(usuarios.getSelectedItem().toString(), server, statement);
            setVisible(false);
        });

        PersonalizedButton botonCancelar = new PersonalizedButton("Cancelar", Color.RED, 250, 140, 85, 25);
        botonCancelar.addActionListener((ActionListener) -> {
            setVisible(false);
        });
        add(etiquetaUsuario);
        add(botonCancelar);
        add(usuarios);
        add(botonBorrar);
    }

    public static void borrarUsuario(String userName, String server, Statement statement) {
        String query = String.format("DROP USER '%s'@'%s';", userName, server);
        try {
            statement.execute(query);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
