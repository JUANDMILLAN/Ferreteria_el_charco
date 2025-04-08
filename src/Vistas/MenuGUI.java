package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import CHAT.servidorChat;
import CHAT.chatGUI;
import com.formdev.flatlaf.FlatDarculaLaf;

public class MenuGUI {
    private JPanel main;
    private JComboBox<String> comboBox1;
    private JButton venderButton;
    private JButton chatButton;
    private JButton salirButton;
    private JPanel panelContenido;
    private JButton historialButton;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Menú Principal");
        frame.setContentPane(new MenuGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1120, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MenuGUI() {
        // Iniciar servidor de chat en un hilo separado
        new Thread(() -> {
            try {
                servidorChat.main(new String[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        comboBox1.removeAllItems();
        comboBox1.addItem("Clientes");
        comboBox1.addItem("Personal");
        comboBox1.addItem("Proveedores");
        comboBox1.addItem("Inventario");

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBox1.getSelectedItem();

                panelContenido.removeAll();
                panelContenido.setLayout(new BorderLayout());

                if (seleccion != null && seleccion.equals("Clientes")) {
                    ClientescharcoGUI clientesGUI = new ClientescharcoGUI();
                    clientesGUI.getMainPanel().setPreferredSize(new Dimension(100, 100));
                    panelContenido.add(clientesGUI.getMainPanel());

                } else if (seleccion.equals("Personal")) {
                    PersonalcharcoGUI personalGUI = new PersonalcharcoGUI();
                    personalGUI.getMainPanel().setPreferredSize(new Dimension(100, 100));
                    panelContenido.add(personalGUI.getMainPanel());

                } else if (seleccion.equals("Proveedores")) {
                    ProveedoresGUI proveedoresGUI = new ProveedoresGUI();
                    proveedoresGUI.getMainPanel().setPreferredSize(new Dimension(100, 100));
                    panelContenido.add(proveedoresGUI.getMainPanel());

                } else if (seleccion.equals("Inventario")) {
                    InventarioGUI inventarioGUI = new InventarioGUI();
                    inventarioGUI.getMainPanel().setPreferredSize(new Dimension(100, 100));
                    panelContenido.add(inventarioGUI.getMainPanel());
                }

                panelContenido.revalidate();
                panelContenido.repaint();
            }
        });

        venderButton.addActionListener(e ->
        {
            panelContenido.removeAll();


            VentasGUI ventasGUI = new VentasGUI();
            ventasGUI.getMainPanel().setPreferredSize(new Dimension(100, 100));

            panelContenido.setLayout(new BorderLayout());
            panelContenido.add(ventasGUI.getMainPanel());

            panelContenido.revalidate();
            panelContenido.repaint();
        });

        chatButton.addActionListener(e -> new chatGUI("EMPLEADO").setVisible(true));

        salirButton.addActionListener(e -> System.exit(0));
    }
}
