package components;

import javax.swing.*;
import java.awt.*;

public class PersonalizedButton extends JButton {

    public PersonalizedButton(String nombre, Color colorFondo, int x, int y, int ancho, int alto) {
        setText(nombre);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setBackground(colorFondo);
        setBounds(x, y, ancho, alto);
    }
}
