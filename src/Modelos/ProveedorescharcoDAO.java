package Modelos;

import Conexion.conexionBD;
import Entidad.Proveedorescharco;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase DAO para manejar las operaciones de base de datos relacionadas con la entidad Proveedores.
 * Proporciona métodos para agregar, eliminar y actualizar proveedores en la base de datos.
 */
public class ProveedorescharcoDAO {

    // Instanciamos un objeto de la clase conexionBD para conectarnos a la base de datos
    private final conexionBD conexionBD = new conexionBD();

    /**
     * Agrega un nuevo proveedor a la base de datos.
     *
     * @param proveedorescharco El objeto proveedor que se desea agregar.
     */
    public void agregar(Proveedorescharco proveedorescharco) {
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO proveedores (id_proveedor, nombre, contacto,productosuministro ) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, proveedorescharco.getId());
            pst.setString(2, proveedorescharco.getNombre());
            pst.setString(3, proveedorescharco.getContacto());
            pst.setString(4, proveedorescharco.getProductosuministro());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el proveedor.");
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
     * Elimina un proveedor de la base de datos por su ID.
     *
     * @param id_proveedor El ID del proveedor que se desea eliminar.
     */
    public void eliminar(int id_proveedor) {
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM proveedores WHERE id_proveedor = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_proveedor);

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor.");
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
     * Actualiza los datos de un proveedor existente en la base de datos.
     *
     * @param proveedorescharco El objeto proveedor con los datos actualizados.
     */
    public void actualizar(Proveedorescharco proveedorescharco) {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE proveedores SET nombre = ?, contacto = ?, productosuministro = ? WHERE id_proveedor = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, proveedorescharco.getNombre());
            pst.setString(2, proveedorescharco.getContacto());
            pst.setString(3, proveedorescharco.getProductosuministro());
            pst.setInt(4, proveedorescharco.getId());


            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor.");
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
}
