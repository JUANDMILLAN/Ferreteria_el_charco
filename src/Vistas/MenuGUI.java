package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import CHAT.servidorChat;
import CHAT.chatGUI;
import Servicios.VentaServicio;
import com.formdev.flatlaf.FlatDarculaLaf;

public class MenuGUI {
    private JPanel main;
    private JComboBox<String> comboBox1;
    private JButton venderButton;
    private JButton chatButton;
    private JButton salirButton;
    private JPanel panelContenido;
    private JButton inicioButton;
    private JComboBox comboBox2;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Menú Principal");
        frame.setContentPane(new MenuGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // Cargar Home al inicio
        cargarHome();

        // Inicializar combo
        comboBox1.removeAllItems();
        comboBox1.addItem("Clientes");
        comboBox1.addItem("Personal");
        comboBox1.addItem("Proveedores");
        comboBox1.addItem("Inventario");

        // Acciones del combo
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBox1.getSelectedItem();
                panelContenido.removeAll();
                panelContenido.setLayout(new BorderLayout());

                if ("Clientes".equals(seleccion)) {
                    ClientescharcoGUI clientesGUI = new ClientescharcoGUI();
                    panelContenido.add(clientesGUI.getMainPanel(), BorderLayout.CENTER);

                } else if ("Personal".equals(seleccion)) {
                    PersonalcharcoGUI personalGUI = new PersonalcharcoGUI();
                    panelContenido.add(personalGUI.getMainPanel(), BorderLayout.CENTER);

                } else if ("Proveedores".equals(seleccion)) {
                    ProveedoresGUI proveedoresGUI = new ProveedoresGUI();
                    panelContenido.add(proveedoresGUI.getMainPanel(), BorderLayout.CENTER);

                } else if ("Inventario".equals(seleccion)) {
                    InventarioGUI inventarioGUI = new InventarioGUI();
                    panelContenido.add(inventarioGUI.getMainPanel(), BorderLayout.CENTER);
                }
                panelContenido.revalidate();
                panelContenido.repaint();
            }
        });

        comboBox2.removeAllItems();
        comboBox2.addItem("Historial de ventas");
        comboBox2.addItem("Historial de órdenes");

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBox2.getSelectedItem();
                panelContenido.removeAll();
                panelContenido.setLayout(new BorderLayout());

                if ("Historial de ventas".equals(seleccion)) {
                    historialVentas historial = new historialVentas();
                    panelContenido.add(historial.getMainPanel(), BorderLayout.CENTER);
                } else if ("Historial de órdenes".equals(seleccion)) {
                    HistorialOrdenesGUI historialOrdenes = new HistorialOrdenesGUI();
                    panelContenido.add(historialOrdenes.getMainPanel(), BorderLayout.CENTER);
                }

                panelContenido.revalidate();
                panelContenido.repaint();
            }
        });

        // Botón Vender
        venderButton.addActionListener(e -> {
            panelContenido.removeAll();
            panelContenido.setLayout(new BorderLayout());

            VentasGUI ventasGUI = new VentasGUI();
            panelContenido.add(ventasGUI.mainPanel, BorderLayout.CENTER);

            panelContenido.revalidate();
            panelContenido.repaint();
        });

        // Botón Chat
        chatButton.addActionListener(e -> new chatGUI("EMPLEADO").setVisible(true));

        // Botón Salir
        salirButton.addActionListener(e -> System.exit(0));
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelContenido.removeAll();

                HomePanel homePanel = new HomePanel();
                homePanel.getMainPanel().setPreferredSize(new Dimension(750, 600));

                panelContenido.setLayout(new BorderLayout());
                panelContenido.add(homePanel.getMainPanel(), BorderLayout.CENTER);

                panelContenido.revalidate();
                panelContenido.repaint();
            }
        });

    }

    private void cargarHome() {
        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());

        HomePanel home = new HomePanel();
        panelContenido.add(home.getMainPanel(), BorderLayout.CENTER);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
}
