package Vistas;

import Modelos.ProveedorescharcoDAO;

import javax.swing.*;

public class ProveedoresGUI extends JFrame {











    ProveedorescharcoDAO proveedorescharcoDAO = new ProveedorescharcoDAO();
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton eliminarButton;
    private JButton editarButton;
    private JButton agregarButton;


    public ProveedoresGUI(){
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(10000, 700);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
