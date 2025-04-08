package Vistas;

import javax.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;

import java.awt.*;

public class ClientescharcoGUI extends JPanel {
    private JPanel mainPanel;
    private JTable table1;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    public ClientescharcoGUI() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000,1000));
        add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
