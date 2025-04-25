package Vistas;

import Conexion.conexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 * La clase {@code historialVentas} representa una interfaz gráfica que muestra el historial de ventas realizadas.
 * La información se carga en una tabla desde la base de datos y se muestra en el panel principal.
 */
public class historialVentas extends JPanel {
    private JPanel mainPanel;
    private JTable table1;

    /**
     * Constructor de la clase {@code historialVentas}. Inicializa el panel y carga los datos del historial de ventas.
     */
    public historialVentas() {
        add(mainPanel); // necesario para que el formulario .form se vea
        cargarDatosVentas(); // carga la tabla al abrir la vista
    }

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal donde se muestra la tabla de historial de ventas.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Carga los datos del historial de ventas desde la base de datos y los muestra en la tabla {@code table1}.
     * Utiliza una consulta SQL para obtener la información de las ventas realizadas, incluyendo el cliente,
     * el empleado, el total, el estado y la fecha de la venta.
     */
    public void cargarDatosVentas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID Venta", "Cliente", "Empleado", "Total", "Estado", "Fecha"});

        String sql = """
            SELECT v.id_venta, c.nombre AS nombre_cliente, e.nombre AS nombre_empleado,
                   v.total, v.estado, v.fecha
            FROM registro_ventas v
            INNER JOIN clientes c ON v.id_cliente = c.id_cliente
            INNER JOIN empleados e ON v.id_empleado = e.id_empleado
        """;

        conexionBD conBD = new conexionBD();

        try (Connection conn = conBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getInt("id_venta"),
                        rs.getString("nombre_cliente"),
                        rs.getString("nombre_empleado"),
                        rs.getDouble("total"),
                        rs.getString("estado"),
                        rs.getDate("fecha")
                };
                modelo.addRow(fila);
            }

            table1.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar historial de ventas: " + e.getMessage());
        }
    }
}
