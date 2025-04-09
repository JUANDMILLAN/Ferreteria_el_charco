package Vistas;

import javax.swing.*;

public class HomePanel extends JPanel {
    public JPanel mainPanel;
    public JTable table1;
    public JTable table2;
    public JTable table3;

    public HomePanel() {
        add(mainPanel); // Para mostrar el contenido del .form
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
