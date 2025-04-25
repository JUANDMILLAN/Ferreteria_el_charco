package Modelos;

import Conexion.conexionBD;
import Entidad.Clientescharco;
import Entidad.inventarioProductos;

import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que maneja las operaciones de base de datos relacionadas con los clientes.
 * Permite agregar, eliminar, actualizar y obtener clientes desde la base de datos.
 */
public class ClientescharcoDAO {

    private Conexion.conexionBD conexionBD = new conexionBD();

    /**
     * Agrega un nuevo cliente a la base de datos.
     *
     * @param clientescharco El cliente a agregar.
     */
    public void agregar(Clientescharco clientescharco) {
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO clientes (nombre, telefono, direccion, correo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientescharco.getNombre());
            pst.setString(2, clientescharco.getTelefono());
            pst.setString(3, clientescharco.getDireccion());
            pst.setString(4, clientescharco.getCorreo());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Elimina un cliente de la base de datos por su ID.
     *
     * @param id El ID del cliente a eliminar.
     */
    public void eliminar(int id) {
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM clientes WHERE id_cliente = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el cliente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actualiza la información de un cliente en la base de datos.
     *
     * @param clientescharco El cliente con los nuevos datos.
     */
    public void actualizar(Clientescharco clientescharco) {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE clientes SET nombre = ?, telefono = ?, direccion = ?, correo = ? WHERE id_cliente = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientescharco.getNombre());
            pst.setString(2, clientescharco.getTelefono());
            pst.setString(3, clientescharco.getDireccion());
            pst.setString(4, clientescharco.getCorreo());
            pst.setInt(5, clientescharco.getId());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar cliente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtiene todos los clientes de la base de datos, incluyendo su ID y nombre.
     *
     * @return Una lista de clientes.
     */
    public ArrayList<Clientescharco> obtenerTodos() {
        ArrayList<Clientescharco> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps;
        ResultSet rs;

        try {
            con = conexionBD.getConnection();
            String sql = "SELECT id_cliente, nombre FROM clientes";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Clientescharco cliente = new Clientescharco();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return lista;
    }

    /**
     * Clase interna que maneja las operaciones relacionadas con el inventario de productos.
     */
    public static class InventarioDAO {

        /**
         * Obtiene todos los productos del inventario desde la base de datos.
         *
         * @return Una lista de productos.
         */
        public ArrayList<inventarioProductos> obtenerProductos() {
            ArrayList<inventarioProductos> lista = new ArrayList<>();
            Connection con = null;
            PreparedStatement ps;
            ResultSet rs;

            try {
                con = new conexionBD().getConnection();
                String sql = "SELECT * FROM inventario_productos";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    inventarioProductos producto = new inventarioProductos(
                            rs.getInt("id_producto"),
                            rs.getString("categoria"),
                            rs.getString("nombre_producto"),
                            rs.getInt("precio_proveedor"),
                            rs.getInt("precio_venta"),
                            rs.getInt("cantidad_stock")
                    );
                    lista.add(producto);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return lista;
        }
    }
}
