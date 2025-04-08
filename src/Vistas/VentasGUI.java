package Vistas;

import javax.swing.*;

public class VentasGUI extends JPanel {
    public JPanel mainPanel;
    private JTable table1;
    private JTable table2;
    private JButton imprimirFacturaButton;
    private JButton guardarVentaButton;
    private JButton agregarButton;
    private JButton cancelarButton;

    public VentasGUI (){
        add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
