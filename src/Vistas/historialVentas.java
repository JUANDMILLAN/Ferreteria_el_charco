package Vistas;

import Conexion.conexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class historialVentas extends JPanel {
    private JPanel mainPanel;
    private JTable table1;

    public historialVentas() {
        add(mainPanel); // necesario para que el formulario .form se vea
        cargarDatosVentas(); // carga la tabla al abrir la vista
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

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
