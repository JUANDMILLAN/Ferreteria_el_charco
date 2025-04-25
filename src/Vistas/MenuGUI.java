package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import CHAT.servidorChat;
import CHAT.chatGUI;
import com.formdev.flatlaf.FlatDarculaLaf;

/**
 * Clase que representa el menú principal de la aplicación.
 * Esta clase gestiona la interfaz de usuario para la navegación entre diferentes vistas,
 * como ventas, historial de ventas, clientes, proveedores, inventario, y chat.
 */
public class MenuGUI extends JFrame {
    private JPanel main; // Panel principal que contiene todos los componentes.
    private JComboBox<String> comboBox1; // ComboBox para seleccionar la vista (Clientes, Personal, Proveedores, Inventario).
    private JButton venderButton; // Botón para acceder a la vista de ventas.
    private JButton chatButton; // Botón para abrir el chat.
    private JButton salirButton; // Botón para salir de la aplicación.
    private JPanel panelContenido; // Panel donde se cargan las diferentes vistas seleccionadas.
    private JButton inicioButton; // Botón para volver a la vista principal (Home).
    private JButton historialDeVentasButton; // Botón para acceder al historial de ventas.

    /**
     * Constructor de la clase MenuGUI.
     * Inicializa la interfaz de usuario, establece las acciones de los botones y carga el servidor de chat en un hilo separado.
     */
    public MenuGUI() {
        super("Menú Principal");

        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 700);
        setResizable(false);
        setLocationRelativeTo(null);

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

                // Cambiar la vista según la selección del comboBox
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

        // Botón Historial de ventas
        historialDeVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialVentas historial = new historialVentas();
                panelContenido.removeAll();
                panelContenido.add(historial.getMainPanel(), BorderLayout.CENTER);
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

        // Botón Inicio
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

    /**
     * Método que carga la vista inicial de la aplicación (Home).
     */
    private void cargarHome() {
        panelContenido.removeAll();
        panelContenido.setLayout(new BorderLayout());

        HomePanel home = new HomePanel();
        panelContenido.add(home.getMainPanel(), BorderLayout.CENTER);

        panelContenido.revalidate();
        panelContenido.repaint();
    }
}
