package Vistas;
import Entidad.Proveedorescharco;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuGUI {
    private JPanel main;
    private JComboBox comboBox1;
    private JButton venderButton;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton chatButton;
    private JButton salirButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setContentPane(new MenuGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(880, 770);
        frame.setResizable(false);
    }
    public MenuGUI() {
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String seleccion = (String) comboBox1.getSelectedItem();



                if (seleccion != null && seleccion.equals("Clientes")){
                    ClientescharcoGUI clientescharcoGUI = new ClientescharcoGUI();
                    clientescharcoGUI.setVisible(true);
                    


                }else if (seleccion != null && seleccion.equals("Personal")){
                    PersonalcharcoGUI personalcharcoGUI = new PersonalcharcoGUI();
                    personalcharcoGUI.setVisible(true);

                }else if (seleccion != null && seleccion.equals("Proveedores")){
                    ProveedoresGUI proveedoresGUI = new ProveedoresGUI();
                    proveedoresGUI.setVisible(true);

                }else if (seleccion != null && seleccion.equals("Inventario")){
                    InventarioGUI inventarioGUI = new InventarioGUI();
                    inventarioGUI.setVisible(true);
                }

            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentaGUI ventaGUI = new VentaGUI();
                ventaGUI.setVisible(true);
            }
        });

    }
}





