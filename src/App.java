public class App {
    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        // esto es para que se actualize la ventana y se vean los componentes, a veces aparecía y a veces no, por lo que tenía
        // que poner esta línea, lo raro es que esto no pasa con las demás pantallas.
        loginFrame.repaint();
    }
}
