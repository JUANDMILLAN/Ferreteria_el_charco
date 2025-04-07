package Clientescharco;

import Conexion.ConexionBD;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión entre la aplicación y la base de datos
 * para realizar operaciones CRUD sobre la tabla "clientes".
 *
 * Contiene métodos para agregar, eliminar y actualizar clientes.
 * Utiliza la clase {@link ConexionBD} para establecer la conexión a la base de datos.
 *
 * @author Juan
 * @version 1.0
 */
public class ClientescharcoDAO {

    /** Objeto encargado de gestionar la conexión a la base de datos. */
    private ConexionBD conexionBD = new ConexionBD();

    /**
     * Agrega un nuevo cliente a la base de datos.
     *
     * @param clientescharco Objeto que contiene los datos del cliente a agregar.
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
     * Elimina un cliente de la base de datos mediante su ID.
     *
     * @param id Identificador único del cliente a eliminar.
     */
    public void eliminar(int id) {
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM clientes WHERE id = ?";

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
     * Actualiza los datos de un cliente existente en la base de datos.
     *
     * @param clientescharco Objeto que contiene los datos actualizados del cliente.
     */
    public void actualizar(Clientescharco clientescharco) {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE clientes SET nombre = ?, telefono = ?, direccion = ?, correo = ? WHERE id = ?";

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
}
