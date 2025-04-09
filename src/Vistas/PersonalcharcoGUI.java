package Vistas;

import javax.swing.*;

public class PersonalcharcoGUI extends JPanel {
    private JPanel mainPanel;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;

    public PersonalcharcoGUI() {
        // Este panel ya está armado desde el diseñador y contiene todos los componentes
        add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Aquí puedes ir agregando la lógica de los botones si lo necesitas
}
