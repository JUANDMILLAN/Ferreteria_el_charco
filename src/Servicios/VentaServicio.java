package Servicios;

import Entidad.inventarioProductos;
import Conexion.conexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class VentaServicio {

    // Agrega un producto a la tablaFactura o aumenta su cantidad si ya existe
    public void agregarProducto(JTable tablaFactura, inventarioProductos producto) {
        DefaultTableModel model = (DefaultTableModel) tablaFactura.getModel();

        boolean encontrado = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            int idExistente = (int) model.getValueAt(i, 0);

            if (idExistente == producto.getId_producto()) {
                int cantidadActual = (int) model.getValueAt(i, 5);
                model.setValueAt(cantidadActual + 1, i, 5);

                int precioVenta = (int) model.getValueAt(i, 4);
                model.setValueAt((cantidadActual + 1) * precioVenta, i, 6);

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            Vector<Object> fila = new Vector<>();
            fila.add(producto.getId_producto());
            fila.add(producto.getCategoria());
            fila.add(producto.getNombre_producto());
            fila.add(producto.getPrecio_proveedor());
            fila.add(producto.getPrecio_venta());
            fila.add(1); // Cantidad
            fila.add(producto.getPrecio_venta()); // Subtotal
            model.addRow(fila);
        }
    }

    // Guarda la venta principal en la tabla registro_ventas, incluyendo el estado
    public int guardarVenta(int idCliente, int idEmpleado, int total, String estado) {
        int idVenta = -1;
        String sql = "INSERT INTO registro_ventas (id_cliente, id_empleado, total, estado, fecha) VALUES (?, ?, ?, ?, NOW())";

        conexionBD conBD = new conexionBD();
        try (Connection conn = conBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idCliente);
            ps.setInt(2, idEmpleado);
            ps.setInt(3, total);
            ps.setString(4, estado);  // Aquí se agrega el estado

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idVenta = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idVenta;
    }


    // Guarda el detalle de cada producto vendido
    public void guardarDetalleVenta(int idVenta, int idProducto, int cantidad, int precioUnitario, int subtotal) {
        String sql = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        conexionBD conBD = new conexionBD();
        try (Connection conn = conBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setInt(4, precioUnitario);
            ps.setInt(5, subtotal);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    // Resta la cantidad vendida al inventario
    public void actualizarStock(int idProducto, int cantidadVendida) {
        String sql = "UPDATE inventario_productos SET cantidad_stock = cantidad_stock - ? WHERE id_producto = ?";

        conexionBD conBD = new conexionBD();
        try (Connection conn = conBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idProducto);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
