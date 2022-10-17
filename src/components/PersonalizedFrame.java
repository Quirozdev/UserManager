package components;

import javax.swing.*;

public class PersonalizedFrame extends JFrame {
    public PersonalizedFrame(String titulo, int x, int y, int ancho, int alto) {
        setTitle(titulo);
        ImageIcon icono = new ImageIcon(getClass().getResource("images/appicon.jpg"));
        setIconImage(icono .getImage());
        setLayout(null);
        setResizable(false);
        setBounds(x, y, ancho, alto);
        setVisible(true);
    }
}
