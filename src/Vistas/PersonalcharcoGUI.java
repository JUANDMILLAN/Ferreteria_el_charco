package Vistas;

import Modelos.PersonalcharcoDAO;

import javax.swing.*;

public class PersonalcharcoGUI extends JFrame {
    PersonalcharcoDAO personalcharcoDAO = new PersonalcharcoDAO();
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JTextField textField3;
    private JList list1;
    private JButton agregarButton;
    private JButton volverButton;
    private JButton actualizarButton;
    private JButton eliminarButton;


    public PersonalcharcoGUI(){
        setContentPane(main);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        JList jList = new JList();
        jList.setSize(100,100);








    }

}
