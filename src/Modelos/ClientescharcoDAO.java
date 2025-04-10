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

public class ClientescharcoDAO {
    private Conexion.conexionBD conexionBD = new conexionBD();

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

    public static class InventarioDAO {
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
