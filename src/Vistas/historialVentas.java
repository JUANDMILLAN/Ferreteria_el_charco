package Vistas;

import javax.swing.*;

public class historialVentas extends JPanel {
    private JPanel mainPanel;
    private JTable table1;

    public historialVentas() {
        add(mainPanel); // necesario para que el formulario .form se vea
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
