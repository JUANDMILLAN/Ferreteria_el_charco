package Vistas;

import Conexion.conexionBD;
import Entidad.Clientescharco;
import Modelos.ClientescharcoDAO;

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
import java.awt.*;

/**
 * La clase {@code ClientescharcoGUI} proporciona una interfaz gráfica para gestionar la información de los clientes.
 * Permite agregar, editar, eliminar y mostrar los datos de los clientes en una tabla. También muestra información
 * adicional sobre los clientes, como el total de clientes y el cliente más frecuente.
 */
public class ClientescharcoGUI extends JPanel {
    private JPanel mainPanel;
    private JTable table1;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JTextField textFieldnombrecliente;
    private JTextField textFielddireccioncliente;
    private JTextField textFieldtelefonocliente;
    private JTextField textFieldcorreocliente;
    private JTextField textField1;
    private JLabel labelClientesTotales;
    private JLabel labelClienteFrecuente;
    int filas = 0;

    ClientescharcoDAO clientescharcoDAO = new ClientescharcoDAO();

    /**
     * Constructor de la clase {@code ClientescharcoGUI}. Inicializa los componentes gráficos y configura
     * los eventos de los botones.
     */
    public ClientescharcoGUI() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000,1000));
        add(mainPanel);
        textField1.setEnabled(false);
        mostrarDatos(); // Muestra los datos de los clientes en la tabla
        actualizarTarjetasClientes(); // Actualiza las tarjetas de clientes

        // Configuración del botón para agregar un cliente
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldnombrecliente.getText();
                String telefono = textFieldtelefonocliente.getText();
                String direccion = textFielddireccioncliente.getText();
                String correo = textFieldcorreocliente.getText();

                // Crear un nuevo cliente y agregarlo a la base de datos
                Clientescharco clientes = new Clientescharco(0, nombre, direccion, telefono, correo);
                clientescharcoDAO.agregar(clientes);
                clear(); // Limpiar los campos de texto
                mostrarDatos(); // Actualizar los datos en la tabla
                actualizarTarjetasClientes(); // Actualizar las tarjetas de clientes
            }
        });

        // Configuración del botón para editar un cliente
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldnombrecliente.getText();
                String telefono = textFieldtelefonocliente.getText();
                String direccion = textFielddireccioncliente.getText();
                String correo = textFieldcorreocliente.getText();
                int id = Integer.parseInt(textField1.getText());

                // Crear un cliente con los datos modificados y actualizar en la base de datos
                Clientescharco clientes = new Clientescharco(id, nombre, telefono, direccion, correo);
                clientescharcoDAO.actualizar(clientes);
                clear(); // Limpiar los campos de texto
                mostrarDatos(); // Actualizar los datos en la tabla
                actualizarTarjetasClientes(); // Actualizar las tarjetas de clientes
            }
        });

        // Configuración del botón para eliminar un cliente
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                // Eliminar el cliente de la base de datos
                clientescharcoDAO.eliminar(id);
                clear(); // Limpiar los campos de texto
                mostrarDatos(); // Actualizar los datos en la tabla
                actualizarTarjetasClientes(); // Actualizar las tarjetas de clientes
            }
        });

        // Evento para seleccionar un cliente de la tabla
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selecfila = table1.getSelectedRow();
                if (selecfila >= 0) {
                    // Llenar los campos de texto con los datos del cliente seleccionado
                    textField1.setText(table1.getValueAt(selecfila, 0).toString());
                    textFieldnombrecliente.setText((String) table1.getValueAt(selecfila, 1));
                    textFieldtelefonocliente.setText((String) table1.getValueAt(selecfila, 2));
                    textFielddireccioncliente.setText((String) table1.getValueAt(selecfila, 3));
                    textFieldcorreocliente.setText((String) table1.getValueAt(selecfila, 4));
                    filas = selecfila;
                }
            }
        });
    }

    /**
     * Limpia los campos de texto del formulario.
     */
    public void clear() {
        textFieldnombrecliente.setText("");
        textFieldtelefonocliente.setText("");
        textFielddireccioncliente.setText("");
        textFieldcorreocliente.setText("");
        textField1.setText(""); // Limpia el campo de ID
    }

    /**
     * Muestra los datos de los clientes en la tabla {@code table1}.
     */
    public void mostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Dirección");
        modelo.addColumn("Correo");

        table1.setModel(modelo);

        // Conexión a la base de datos para obtener los datos de los clientes
        Connection con = new conexionBD().getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM clientes";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_cliente");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("direccion");
                fila[4] = rs.getString("correo");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }

    /**
     * Obtiene el nombre del cliente más frecuente basado en las ventas realizadas.
     *
     * @return Nombre del cliente más frecuente.
     */
    public String obtenerClienteMasFrecuente() {
        String nombre = "";
        String sql = "SELECT c.nombre FROM registro_ventas rv " +
                "JOIN clientes c ON rv.id_cliente = c.id_cliente " +
                "GROUP BY c.id_cliente ORDER BY COUNT(*) DESC LIMIT 1";

        try (Connection con = new conexionBD().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                nombre = rs.getString("nombre");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    /**
     * Actualiza las tarjetas de información, incluyendo el número total de clientes y el cliente más frecuente.
     */
    public void actualizarTarjetasClientes() {
        int totalClientes = table1.getRowCount();
        labelClientesTotales.setText(String.valueOf(totalClientes));

        String clienteFrecuente = obtenerClienteMasFrecuente();
        labelClienteFrecuente.setText(clienteFrecuente != null ? clienteFrecuente : "Sin datos");
    }

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return Panel principal de la interfaz.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
