package Personalcharco;

import Conexion.ConexionBD;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase DAO (Data Access Object) para gestionar las operaciones CRUD del personal
 * en la base de datos "El Charco".
 *
 * Esta clase maneja la lógica de acceso a datos para agregar, actualizar y eliminar
 * registros en la tabla "personal".
 *
 * @author Juan
 * @version 1.0
 */
public class PersonalcharcoDAO {

    /** Objeto para manejar la conexión con la base de datos */
    private ConexionBD conexionBD = new ConexionBD();

    /**
     * Agrega un nuevo registro de personal a la base de datos.
     *
     * @param personalcharco Objeto que contiene los datos del nuevo personal.
     */
    public void agregar(Personalcharco personalcharco) {
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO personal (nombre, cargo, salario) VALUES (?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, personalcharco.getNombre());
            pst.setString(2, personalcharco.getCargo());
            pst.setDouble(3, personalcharco.getSalario());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Empleado agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar empleado.");
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
     * Elimina un registro de personal de la base de datos por ID.
     *
     * @param id ID del personal que se desea eliminar.
     */
    public void eliminar(int id) {
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM personal WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, id);

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Personal eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el personal.");
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
     * Actualiza un registro existente de personal en la base de datos.
     *
     * @param personalcharco Objeto con los nuevos datos del personal.
     */
    public void actualizar(Personalcharco personalcharco) {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE personal SET nombre = ?, cargo = ?, salario = ? WHERE id = ?";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, personalcharco.getNombre());
            pst.setString(2, personalcharco.getCargo());
            pst.setDouble(3, personalcharco.getSalario());
            pst.setInt(4, personalcharco.getId());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Personal actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar personal.");
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
