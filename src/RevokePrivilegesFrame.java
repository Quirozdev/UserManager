import components.PersonalizedButton;
import components.PersonalizedFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RevokePrivilegesFrame extends PersonalizedFrame {
    public RevokePrivilegesFrame(String server, Statement statement) {
        super("Revocar permisos", 70, 20, 1000, 800);
        JPanel panelSeleccion = new JPanel();
        // para acomodar libremente los componentes
        panelSeleccion.setLayout(null);
        panelSeleccion.setBounds(100,20, 600, 250);
        panelSeleccion.setBackground(Color.pink);
        panelSeleccion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        JLabel etiquetaUsuario = new JLabel("Usuario");
        etiquetaUsuario.setBounds(145, 40, 50, 30);
        JComboBox<String> usuariosDisponibles = new JComboBox<>();
        usuariosDisponibles.setBounds(200, 40, 200, 30);
        Functions.construirComboBoxUsuarios(usuariosDisponibles, statement);
        JLabel etiquetaBD = new JLabel("Base de datos");
        etiquetaBD.setBounds(100, 100, 90, 30);
        JComboBox<String> basesDeDatosDisponibles = new JComboBox<>();
        basesDeDatosDisponibles.setBounds(200, 100, 200, 30);
        Functions.agregarTodasLasBDAlComboBox(basesDeDatosDisponibles, statement);
        JLabel etiquetaTablas = new JLabel("Tabla");
        etiquetaTablas.setBounds(155, 160, 40, 30);
        JComboBox<String> tablas = new JComboBox<>();
        tablas.setBounds(200, 160, 200, 30);
        // por defecto el combobox de tablas va a estar deshabilitado porque la opción por defecto del combobox es "Todas",
        // esto es debido a que si la base de datos se describe como todas, entonces tablas también tienen que ser todas,
        // no se puede especificar una tabla en especifico, cuando se han especificado todas las bases de datos.
        // Ejemplo:
        // GRANT ALL PRIVILEGES ON *.* TO 'prueba'@'localhost';       <- No genera error en la sintaxis de MySQL, todas las bases de datos, todas las tablas
        // GRANT ALL PRIVILEGES ON *.alumnos TO 'prueba'@'localhost';  <- ERROR, todas las bases de datos, pero solo la tabla alumnos, esto genera un error de sintaxis en MySQL
        tablas.addItem("Todas");
        tablas.setEnabled(false);
        // este ItemListener me va a servir para detectar cuando se haga una selección de alguna de las opciones en el
        // combobox de bases de datos, si detecta algún cambio, el combobox de tablas se actualiza dependiendo de la
        // base de datos elegida en el primer combobox.
        basesDeDatosDisponibles.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // checo si el valor seleccionado en el checkbox de base de datos es "Todas"
                    if (basesDeDatosDisponibles.getSelectedItem().toString().equals("Todas")) {
                        // si es así, entonces al checkbox de tablas le pongo que el valor seleccionado sea "Todas" y que no se pueda modificar,
                        // hasta cambiar la selección de "Todas" en el checkbox de base de datos
                        tablas.setSelectedItem("Todas");
                        // inhabilito el checkbox de tablas
                        tablas.setEnabled(false);
                    } else {
                        // si se seleccionó otro elemento que no sea "Todas", entonces habilito el checkbox de tablas
                        tablas.setEnabled(true);
                        // y a ese checkbox le agrego todos los valores, dependiendo de la base de datos seleccionada en el checkbox de base de datos
                        Functions.agregarTodasLasTablasAlComboBox(tablas, basesDeDatosDisponibles.getSelectedItem().toString(), statement);
                    }
                }
            }
        });
        JPanel panelListaPrivilegios = new JPanel();
        panelListaPrivilegios.setBounds(100,300, 600, 400);
        panelListaPrivilegios.setBackground(Color.pink);
        panelListaPrivilegios.setLayout(null);
        panelListaPrivilegios.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        ArrayList<JCheckBox> privilegios = Functions.generarCheckBoxes();
        Functions.agregarCheckBoxesAlPanel(privilegios, panelListaPrivilegios);

        PersonalizedButton botonRevocar = new PersonalizedButton("Revocar", Color.MAGENTA, 800, 300, 120, 40);
        botonRevocar.addActionListener((ActionListener) -> {
            String usuarioSeleccionado = usuariosDisponibles.getSelectedItem().toString();
            String baseDeDatosSeleccionada = basesDeDatosDisponibles.getSelectedItem().toString();
            String tablaSeleccionada = tablas.getSelectedItem().toString();
            // este contador es para checar cuantos privilegios se seleccionaron y en caso de no haberse seleccionado ninguno,
            // mostrar un mensaje
            int contador = 0;
            for (JCheckBox privilegio : privilegios) {
                if (privilegio.isSelected()) {
                    contador++;
                }
            }
            // si se seleccionó por lo menos un privilegio, se manda a llamar a la función revocarPermisos y se "cierra" este jdialog
            if (contador != 0) {
                revocarPermisos(usuarioSeleccionado, baseDeDatosSeleccionada, tablaSeleccionada, server, privilegios, statement);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Elige por lo menos un privilegio", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        PersonalizedButton botonCancelar = new PersonalizedButton("Cancelar", Color.RED, 800, 360, 120, 40);
        botonCancelar.addActionListener((ActionListener) -> {
            setVisible(false);
        });

        // estos botones van a servir para seleccionar y deseleccionar todos los checkbox de los privilegios de manera rápida
        PersonalizedButton botonSeleccionarTodos = new PersonalizedButton("Seleccionar todos", Color.cyan, 400, 250, 180, 40);
        botonSeleccionarTodos.addActionListener((ActionListener) -> {
            Functions.seleccionarTodosLosPrivilegios(privilegios);
        });

        PersonalizedButton botonDeseleccionarTodos = new PersonalizedButton("Deseleccionar todos", Color.yellow, 400, 300, 180, 40);
        botonDeseleccionarTodos.addActionListener((ActionListener) -> {
            Functions.deseleccionarTodosLosPrivilegios(privilegios);
        });

        panelListaPrivilegios.add(botonSeleccionarTodos);
        panelListaPrivilegios.add(botonDeseleccionarTodos);
        panelSeleccion.add(etiquetaUsuario);
        panelSeleccion.add(usuariosDisponibles);
        panelSeleccion.add(etiquetaBD);
        panelSeleccion.add(basesDeDatosDisponibles);
        panelSeleccion.add(etiquetaTablas);
        panelSeleccion.add(tablas);
        add(panelSeleccion);
        add(panelListaPrivilegios);
        add(botonRevocar);
        add(botonCancelar);
    }

    private static void revocarPermisos(String usuario, String bd, String tabla, String server, ArrayList<JCheckBox> privilegios, Statement statement) {
        // REVOKE ALL PRIVILEGES ON *.* FROM 'prueba'@'localhost';
        // si en los checkboxes, se seleccionó la opción "Todas", este valor es cambiado por un "*", para poder usarlo
        // en la consulta de MySQL
        if (bd.equals("Todas")) {
            bd = "*";
        }
        if (tabla.equals("Todas")) {
            tabla = "*";
        }
        // filtro solo los checkbox / privilegios seleccionados
        ArrayList<JCheckBox> privilegiosSelecionados = new ArrayList<>();
        for (JCheckBox privilegio : privilegios) {
            if (privilegio.isSelected()) {
                privilegiosSelecionados.add(privilegio);
            }
        }
        // voy a ir construyendo la consulta
        String query = "REVOKE ";
        int cantidadPrivilegiosSeleccionados = privilegiosSelecionados.size();
        int privilegiosTotales = privilegios.size();
        // esto quiere decir que se seleccionaron todos los privilegios en los checkboxes
        if (cantidadPrivilegiosSeleccionados == privilegiosTotales) {
            // esto es para simplificar la consulta, para no tener que poner todos los privilegios uno por uno
            query += "ALL PRIVILEGES ";
        } else {
            // recorro los privilegios seleccionados, si no se seleccionaron todos y los voy agregando a la consulta
            for (int i = 0; i < cantidadPrivilegiosSeleccionados; i++) {
                JCheckBox privilegioActual = privilegiosSelecionados.get(i);
                // si es el ultimo elemento, no le agrego la coma al final, para que no haya un error de sintaxis SQL
                if (i == cantidadPrivilegiosSeleccionados - 1) {
                    query = query + privilegioActual.getText() + " ";
                } else {
                    query = query + privilegioActual.getText() + ", ";
                }
            }
        }
        // termino la consulta con los datos ya obtenidos como parámetros
        query += String.format("ON %s.%s FROM '%s'@'%s';", bd, tabla, usuario, server);
        System.out.println(query);
        try {
            statement.execute(query);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
