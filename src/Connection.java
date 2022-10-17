import javax.swing.*;
import java.sql.*;

public class Connection {
    private String url;
    private String user;
    private String password;
    private String server;
    private Statement statement;

    public Connection(String url, String user, String password, String server, LoginFrame loginFrame) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.server = server;
        try {
            // esto es para indicar que el SMBD a conectarse es MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.sql.Connection connection = DriverManager.getConnection(url, user, password);
            this.statement = connection.createStatement();
            new GUIPrincipal(this, this.server);
            // se oculta la ventana de login
            loginFrame.setVisible(false);
            // ResultSet resultSet = statement.executeQuery("SELECT * FROM alumnos");
            /*
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }
             */
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Statement getStatement() {
        return this.statement;
    }
}
