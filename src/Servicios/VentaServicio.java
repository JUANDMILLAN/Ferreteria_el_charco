package Servicios;

import Entidad.inventarioProductos;
import Conexion.conexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

/**
 * Clase que gestiona las operaciones relacionadas con la venta: agregar productos a la factura,
 * registrar ventas, actualizar stock de inventario, obtener reportes de ventas y productos,
 * y cambiar el estado de las ventas.
 */
public class VentaServicio {

    /**
     * Agrega un producto a la tabla de factura o aumenta su cantidad si ya existe.
     * @param tablaFactura La tabla donde se muestran los productos en la factura.
     * @param producto El producto que se va a agregar.
     */
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

    /**
     * Guarda la venta en la tabla de registro de ventas.
     * @param idCliente El ID del cliente que realiza la compra.
     * @param idEmpleado El ID del empleado que realiza la venta.
     * @param total El total de la venta.
     * @param estado El estado de la venta.
     * @return El ID de la venta registrada.
     */
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

    /**
     * Guarda el detalle de cada producto vendido.
     * @param idVenta El ID de la venta.
     * @param idProducto El ID del producto vendido.
     * @param cantidad La cantidad vendida del producto.
     * @param precioUnitario El precio unitario del producto.
     * @param subtotal El subtotal por la cantidad vendida del producto.
     */
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

    /**
     * Actualiza el stock de un producto después de una venta.
     * @param idProducto El ID del producto cuyo stock se actualizará.
     * @param cantidadVendida La cantidad vendida del producto.
     */
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

    /**
     * Obtiene las ventas registradas según el filtro de tiempo (diario, semanal o mensual).
     * @param filtro El filtro de tiempo (Diario, Semanal, Mensual).
     * @return Un ResultSet con los resultados de las ventas filtradas.
     */
    public ResultSet obtenerVentasFiltradas(String filtro) {
        String condicionFecha = "";

        switch (filtro) {
            case "Diario":
                condicionFecha = "DATE(fecha) = CURDATE()";
                break;
            case "Semanal":
                condicionFecha = "YEARWEEK(fecha, 1) = YEARWEEK(CURDATE(), 1)";
                break;
            case "Mensual":
                condicionFecha = "MONTH(fecha) = MONTH(CURDATE()) AND YEAR(fecha) = YEAR(CURDATE())";
                break;
        }

        String sql = "SELECT id_venta, total, fecha, estado FROM registro_ventas WHERE " + condicionFecha;
        conexionBD conBD = new conexionBD();

        try {
            Connection conn = conBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene los productos más vendidos según el filtro de tiempo (diario, semanal o mensual).
     * @param filtro El filtro de tiempo (Diario, Semanal, Mensual).
     * @return Un ResultSet con los productos más vendidos.
     */
    public ResultSet obtenerProductosMasVendidos(String filtro) {
        String condicionFecha = "";

        switch (filtro) {
            case "Diario":
                condicionFecha = "DATE(v.fecha) = CURDATE()";
                break;
            case "Semanal":
                condicionFecha = "YEARWEEK(v.fecha, 1) = YEARWEEK(CURDATE(), 1)";
                break;
            case "Mensual":
                condicionFecha = "MONTH(v.fecha) = MONTH(CURDATE()) AND YEAR(v.fecha) = YEAR(CURDATE())";
                break;
        }

        String sql = "SELECT p.nombre_producto, SUM(dv.cantidad) AS total_vendidos " +
                "FROM detalle_venta dv " +
                "INNER JOIN inventario_productos p ON dv.id_producto = p.id_producto " +
                "INNER JOIN registro_ventas v ON dv.id_venta = v.id_venta " +
                "WHERE " + condicionFecha + " " +
                "GROUP BY p.nombre_producto " +
                "ORDER BY total_vendidos DESC " +
                "LIMIT 5";

        conexionBD conBD = new conexionBD();

        try {
            Connection conn = conBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene los clientes con más compras según el filtro de tiempo (diario, semanal o mensual).
     * @param filtro El filtro de tiempo (Diario, Semanal, Mensual).
     * @return Un ResultSet con los clientes top.
     */
    public ResultSet obtenerClientesTop(String filtro) {
        String condicionFecha = "";

        switch (filtro) {
            case "Diario":
                condicionFecha = "DATE(v.fecha) = CURDATE()";
                break;
            case "Semanal":
                condicionFecha = "YEARWEEK(v.fecha, 1) = YEARWEEK(CURDATE(), 1)";
                break;
            case "Mensual":
                condicionFecha = "MONTH(v.fecha) = MONTH(CURDATE()) AND YEAR(v.fecha) = YEAR(CURDATE())";
                break;
        }

        String sql = "SELECT c.nombre AS nombre_cliente, SUM(v.total) AS total_compras " +
                "FROM registro_ventas v " +
                "INNER JOIN clientes c ON v.id_cliente = c.id_cliente " +
                "WHERE " + condicionFecha + " " +
                "GROUP BY c.nombre " +
                "ORDER BY total_compras DESC " +
                "LIMIT 5";

        conexionBD conBD = new conexionBD();

        try {
            Connection conn = conBD.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene una fecha según el filtro de tiempo (diario, semanal o mensual).
     * @param filtro El filtro de tiempo (Diario, Semanal, Mensual).
     * @return La fecha calculada según el filtro.
     */
    public java.sql.Date obtenerFechaFiltro(String filtro) {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        switch (filtro) {
            case "Diario" -> cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
            case "Semanal" -> cal.add(java.util.Calendar.DAY_OF_MONTH, -7);
            case "Mensual" -> cal.add(java.util.Calendar.MONTH, -1);
        }

        return new java.sql.Date(cal.getTimeInMillis());
    }

    /**
     * Actualiza el estado de una venta en la base de datos.
     * @param idVenta El ID de la venta a actualizar.
     * @param nuevoEstado El nuevo estado de la venta.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizarEstadoVenta(int idVenta, String nuevoEstado) {
        String sql = "UPDATE registro_ventas SET estado = ? WHERE id_venta = ?";

        conexionBD conBD = new conexionBD();

        try (Connection conn = conBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idVenta);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si existen productos con stock bajo y marca las filas en la tabla con color rojo.
     * @param tablaInventario La tabla de inventario donde se verificará el stock bajo.
     */
    public void verificarStockBajo(JTable tablaInventario) {
        String sql = "SELECT id_producto, nombre_producto, cantidad_stock FROM inventario_productos WHERE cantidad_stock < ?";

        conexionBD conBD = new conexionBD();
        try (Connection conn = conBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Umbral de stock bajo, por ejemplo, 10 unidades
            ps.setInt(1, 10);

            ResultSet rs = ps.executeQuery();
            boolean stockBajo = false;

            while (rs.next()) {
                stockBajo = true;
                int idProducto = rs.getInt("id_producto");
                String nombreProducto = rs.getString("nombre_producto");
                int cantidadStock = rs.getInt("cantidad_stock");

                // Aquí puedes marcar las filas de la tabla con color rojo
                // Encontrar la fila correspondiente y cambiar el color
                DefaultTableModel model = (DefaultTableModel) tablaInventario.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    int idExistente = (int) model.getValueAt(i, 0);
                    if (idExistente == idProducto) {
                        tablaInventario.setRowSelectionInterval(i, i); // Selecciona la fila
                        tablaInventario.setSelectionBackground(new Color(203,112,92)); // Cambia el color a rojo
                        break;
                    }
                }
            }

            if (stockBajo) {
                JOptionPane.showMessageDialog(null, "¡Advertencia! Algunos productos tienen stock bajo.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
