package Vistas;

import Modelos.VentaDAO;

import javax.swing.*;

public class VentaGUI extends JFrame {

    private JPanel main;







    VentaDAO ventaDAO = new VentaDAO();

    public VentaGUI(){
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(10000, 700);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
