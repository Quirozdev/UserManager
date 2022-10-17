import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Functions {
    public static void construirComboBoxUsuarios(JComboBox comboBox, Statement statement) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT User FROM mysql.user");
            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString(1));
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void agregarTodasLasTablasAlComboBox(JComboBox comboBox, String bd, Statement statement) {
        String query;
        // para que cada vez que se actualice el combobox no muestre las tablas que ya tenía de alguna otra base de datos
        comboBox.removeAllItems();
        if (bd.equals("Todas")) {
            query = String.format("SHOW TABLES;");
        } else {
            query = String.format("SHOW TABLES FROM %s;", bd);
        }

        comboBox.addItem("Todas");
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString(1));
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void agregarTodasLasBDAlComboBox(JComboBox comboBox, Statement statement) {
        String query = "SHOW DATABASES";
        comboBox.addItem("Todas");
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                comboBox.addItem(resultSet.getString(1));
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // recorro todos los checkbox en el arraylist que tengo formado y a cada uno le pongo la propiedad Selected = true, para que se marque el checkbox
    public static void seleccionarTodosLosPrivilegios(ArrayList<JCheckBox> privilegios) {
        for (JCheckBox privilegio : privilegios) {
            privilegio.setSelected(true);
        }
    }

    // lo contrario que la función anterior
    public static void deseleccionarTodosLosPrivilegios(ArrayList<JCheckBox> privilegios) {
        for (JCheckBox privilegio : privilegios) {
            privilegio.setSelected(false);
        }
    }

    // crea un arraylist de checkboxes, dado un arreglo de strings con nombres de cada uno de los privilegios
    public static ArrayList<JCheckBox> generarCheckBoxes() {
        String[] nombresCheckBoxes = {"SELECT", "INSERT", "UPDATE", "DELETE", "CREATE", "DROP", "RELOAD", "SHUTDOWN",
                "PROCESS", "FILE", "GRANT OPTION", "REFERENCES", "INDEX", "ALTER", "SHOW DATABASES",
                "SUPER", "CREATE TEMPORARY TABLES", "LOCK TABLES", "EXECUTE", "REPLICATION SLAVE",
                "REPLICATION CLIENT", "CREATE VIEW", "SHOW VIEW", "CREATE ROUTINE", "ALTER ROUTINE",
                "CREATE USER", "EVENT", "TRIGGER", "CREATE TABLESPACE", "USAGE"};
        ArrayList<JCheckBox> listaCheckBoxes = new ArrayList<>();
        JCheckBox auxiliarCheckBox;
        for (String nombre : nombresCheckBoxes) {
            auxiliarCheckBox = new JCheckBox(nombre);
            listaCheckBoxes.add(auxiliarCheckBox);
        }
        return listaCheckBoxes;
    }

    // esta función se va a encargar de llenar de checkboxes el panel inferior del jdialog de otorgar privilegios.
    // como aún no sé muy bien como usar esa parte de los Layouts como Grid, voy a hacerlo medio manual/medio dinámico
    public static void agregarCheckBoxesAlPanel(ArrayList<JCheckBox> checkBoxes, JPanel jPanel) {
        // posiciones iniciales para los checkboxes respecto al panel
        int x = 20;
        int y = 20;
        int ancho = 150;
        int alto = 25;
        int espacioInferior = 20;
        // recorro todos los checkboxes que cree con la función generarCheckBoxes()
        for (JCheckBox checkBox : checkBoxes) {
            // como voy a colocar de manera "dinámica" cada checkbox y no de manera estática cada uno de ellos poniendo
            // sus coordenadas de manera manual, voy a estar modificando las coordenadas x e y de cada checkbox, primero me
            // aseguro que la coordenada y sumada a la altura del checkbox no sea mayor o igual que la altura del panel menos
            // el espacio inferior, para que los checkboxes no se salgan de manera vertical del panel y para que no queden pegaditos
            // al borde inferior del panel
            if (y + alto >= jPanel.getHeight() - espacioInferior) {
                // en dado caso que se cumpla esto, entonces eso quiere decir que una primera "columna" de checkboxes ya
                // está llena, por lo que la coordenada x la aumento en un poquito más que el ancho de cada checkbox para que inicien
                // otra columna y no estén pegaditos, "reseteando" el valor de y
                x += ancho + 25;
                y = 20;
            }
            // asigno las coordenadas de manera "dinámica"
            checkBox.setBounds(x, y, ancho, alto);
            // al panel le agrego cada checkbox
            jPanel.add(checkBox);
            // aumento la coordenada y para que se vayan separando verticalmente los checkboxes
            y += alto + 5;
        }
    }

    public static void salir() {
        System.exit(0);
    }

}
