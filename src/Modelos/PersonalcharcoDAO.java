package Modelos;
import Conexion.conexionBD;
import Entidad.Personalcharco;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class PersonalcharcoDAO
{
    private Conexion.conexionBD conexionBD = new conexionBD();

    // Metodo para agregar un empleado
    public void agregar(Personalcharco personalcharco)
    {
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO personal (nombre, cargo, salario) VALUES (?, ?, ?)";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,personalcharco.getNombre());
            pst.setString(2, personalcharco.getcargo());
            pst.setDouble(3, personalcharco.getsalario());

            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "empleado ha sido agregado correctamente");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al agregar empleado.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (con != null) con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    // Metodo para eliminar un empleado
    public void eliminar(int id)
    {
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM empleados WHERE id_empleado = ?";

        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "personal eliminado correctamente.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al eliminar el personal.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (con != null) con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    // Metodo para actualizar un empleado
    public void actualizar(Personalcharco personalcharco) {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE empleados SET nombre = ?, cargo = ?, salario = ? WHERE id_empleado = ?";


        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, personalcharco.getNombre());
            pst.setString(2, personalcharco.getcargo());
            pst.setDouble(3, personalcharco.getsalario());
            pst.setInt(4, personalcharco.getId());

            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "personal actualizado correctamente.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al actualizar personal.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (con != null) con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}

