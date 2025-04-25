package Modelos;

import Conexion.conexionBD;
import Entidad.inventarioProductos;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Esta clase proporciona los métodos para interactuar con la tabla de productos en la base de datos.
 * Permite agregar, eliminar y actualizar productos en el inventario.
 */
public class InventarioProductoDAO {

    /**
     * Agrega un nuevo producto al inventario.
     *
     * @param producto El objeto {@link inventarioProductos} que contiene la información del producto a agregar.
     */
    public void agregar(inventarioProductos producto) {
        Connection con = new conexionBD().getConnection();
        try {
            String query = "INSERT INTO inventario_productos (categoria, nombre_producto, precio_proveedor, precio_venta, cantidad_stock) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, producto.getCategoria());
            ps.setString(2, producto.getNombre_producto());
            ps.setInt(3, producto.getPrecio_proveedor()); // Correcto
            ps.setInt(4, producto.getPrecio_venta());     // Correcto
            ps.setInt(5, producto.getCantidad_stock());   // Correcto

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el producto: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto del inventario basado en su ID.
     *
     * @param idProducto El ID del producto a eliminar.
     */
    public void eliminar(int idProducto) {
        Connection con = new conexionBD().getConnection();
        try {
            String query = "DELETE FROM inventario_productos WHERE id_producto = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idProducto);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage());
        }
    }

    /**
     * Actualiza los detalles de un producto en el inventario.
     *
     * @param producto El objeto {@link inventarioProductos} que contiene los nuevos datos del producto.
     */
    public void actualizar(inventarioProductos producto) {
        Connection con = new conexionBD().getConnection();
        try {
            String query = "UPDATE inventario_productos SET categoria=?, nombre_producto=?, precio_proveedor=?, precio_venta=?, cantidad_stock=? WHERE id_producto=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, producto.getCategoria());
            ps.setString(2, producto.getNombre_producto());
            ps.setInt(3, producto.getPrecio_proveedor()); // Correcto
            ps.setInt(4, producto.getPrecio_venta());     // Correcto
            ps.setInt(5, producto.getCantidad_stock());   // Correcto
            ps.setInt(6, producto.getId_producto());      // Correcto

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + e.getMessage());
        }
    }
}
