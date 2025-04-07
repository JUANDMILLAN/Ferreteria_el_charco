package Vistas;

import Modelos.ClientescharcoDAO;

import javax.swing.*;

public class ClientescharcoGUI extends JFrame{
    private JPanel main;
    private JTable table1;
    private JList list1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;


    ClientescharcoDAO clientescharcoDAO = new ClientescharcoDAO();

    public ClientescharcoGUI(){
        setContentPane(main);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}
