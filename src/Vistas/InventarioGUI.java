package Vistas;

import Modelos.InventarioDAO;

import javax.swing.*;

public class InventarioGUI extends JFrame {











    InventarioDAO inventarioDAO = new InventarioDAO();
    private JPanel main;
    private JTable table1;
    private JList list1;

    public InventarioGUI(){
        setContentPane(main);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
