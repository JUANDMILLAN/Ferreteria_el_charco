package Vistas;

import Conexion.conexionBD;
import Entidad.Proveedorescharco;
import Modelos.ProveedorescharcoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que representa la vista para gestionar los proveedores de la empresa.
 * Permite agregar, eliminar y editar proveedores, y visualizar la lista de
 * proveedores en una tabla.
 */
public class ProveedoresGUI extends JPanel {
    public JPanel mainPanel; // Panel principal de la vista
    private JTable table1; // Tabla para mostrar los proveedores
    private JButton agregarButton; // Botón para agregar un nuevo proveedor
    private JButton eliminarButton; // Botón para eliminar un proveedor
    private JButton editarButton; // Botón para editar un proveedor
    private JTextField textField1; // Campo de texto para ingresar el nombre del proveedor
    private JTextField textField2; // Campo de texto para ingresar el contacto del proveedor
    private JTextField textField3; // Campo de texto para mostrar el ID del proveedor seleccionado
    private JTextField textField4;
    int filas = 0; // Variable para almacenar la fila seleccionada en la tabla

    // Instancia de ProveedorescharcoDAO para manejar las operaciones con la base de datos
    ProveedorescharcoDAO proveedorescharcoDAO = new ProveedorescharcoDAO();

    /**
     * Constructor de la clase ProveedoresGUI.
     * Inicializa los componentes de la interfaz y configura las acciones
     * de los botones para agregar, eliminar y editar proveedores.
     */
    public ProveedoresGUI() {
        // Añadir el panel principal y mostrar los datos
        add(mainPanel);
        mostrarDatos();
        textField3.setEnabled(false); // El campo de ID no es editable

        // Acción del botón para agregar un nuevo proveedor
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                String contacto = textField2.getText();
                String productosuministro = textField4.getText();

                // Crear un objeto Proveedorescharco con los datos del formulario
                Proveedorescharco proveedorescharco = new Proveedorescharco(0, nombre, contacto, productosuministro);
                proveedorescharcoDAO.agregar(proveedorescharco); // Agregar el nuevo proveedor
                clear(); // Limpiar los campos
                mostrarDatos(); // Actualizar la tabla con los nuevos datos
            }
        });

        // Acción del botón para eliminar un proveedor
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_proveedor = Integer.parseInt(textField3.getText());
                proveedorescharcoDAO.eliminar(id_proveedor); // Eliminar el proveedor por su ID
                clear(); // Limpiar los campos
                mostrarDatos(); // Actualizar la tabla
            }
        });

        // Acción del botón para editar un proveedor
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_proveedor = Integer.parseInt(textField3.getText());
                String nombre = textField1.getText();
                String contacto = textField2.getText();
                String productosuministro = textField4.getText();

                // Crear un objeto Proveedorescharco con los datos del formulario
                Proveedorescharco proveedorescharco = new Proveedorescharco(id_proveedor, nombre, contacto, productosuministro);
                proveedorescharcoDAO.actualizar(proveedorescharco); // Actualizar el proveedor
                clear(); // Limpiar los campos
                mostrarDatos(); // Actualizar la tabla
            }
        });

        // Acción al seleccionar una fila en la tabla
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selecfila = table1.getSelectedRow();
                if (selecfila >= 0) {
                    // Rellenar los campos con los datos de la fila seleccionada
                    textField3.setText(table1.getValueAt(selecfila, 0).toString()); // ID
                    textField1.setText(table1.getValueAt(selecfila, 1).toString());
                    textField2.setText(table1.getValueAt(selecfila, 2).toString());
                    textField4.setText(table1.getValueAt(selecfila, 3).toString());
                    filas = selecfila;
                }
            }
        });
    }

    /**
     * Limpia los campos del formulario.
     */
    public void clear() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
    }

    /**
     * Muestra los datos de los proveedores en la tabla.
     * Recupera los datos de la base de datos y los carga en la tabla.
     */
    public void mostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Contacto");
        modelo.addColumn("Producto Suministro");

        table1.setModel(modelo);

        Connection con = new conexionBD().getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM proveedores"; // Consulta para obtener los proveedores
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id_proveedor");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("contacto");
                fila[3] = rs.getString("productosuministro");

                modelo.addRow(fila); // Agregar fila a la tabla
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }

    /**
     * Método principal para ejecutar la vista de proveedores.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Proveedores");
        frame.setContentPane(new ProveedoresGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 500);
        frame.setResizable(false);
    }

    /**
     * Devuelve el panel principal de la vista.
     * @return El panel principal que contiene los componentes de la vista.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
