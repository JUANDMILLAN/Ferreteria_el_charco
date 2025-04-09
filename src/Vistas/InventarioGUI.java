package Vistas;

import javax.swing.*;

public class InventarioGUI extends JPanel {
    private JPanel mainPanel;
    private JButton eliminarButton;
    private JButton agregarButton;
    private JButton editarButton;
    private JTable table1;

    public InventarioGUI() {
        add(mainPanel);
        // Agregamos el panel generado por el GUI Designer
    }

    public JPanel getMainPanel() {

        return mainPanel;
    }
}
