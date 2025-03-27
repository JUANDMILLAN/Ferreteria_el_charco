package Clientescharco;

import Conexion.ConexionBD;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientescharcoDAO
{
//instanciamos un objeto de la clase conexion para poder conectarnos a la base de datos
    private ConexionBD conexionBD = new ConexionBD();

//creamos el metodo para agregar un nuevo cliente
    public  void agregar(Clientescharco clientescharco)
    {
        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();
        //creamos la consulta para insertar un nuevo cliente
        String query = "INSERT INTO clientes (nombre, telefono, direccion, correo) VALUES (?, ?, ?, ?)";
//intentamos insertar un nuevo cliente
        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientescharco.getNombre());
            pst.setString(2, clientescharco.getTelefono());
            pst.setString(3, clientescharco.getDireccion());
            pst.setString(4, clientescharco.getCorreo());
//si el resultado es mayor a 0, mostramos un mensaje de que el cliente ha sido agregado correctamente
            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "Cliente agregado correctamente.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al agregar cliente.");
            }
            //si hay un error, mostramos el error
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //creamos el metodo para eliminar un cliente
    public void eliminar(int id)
    {
        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();
        //creamos la consulta para eliminar un cliente
        String query = "DELETE FROM clientes WHERE id = ?";
//intentamos eliminar un cliente
        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
//si el resultado es mayor a 0, mostramos un mensaje de que el cliente ha sido eliminado correctamente
            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al eliminar el cliente.");
            }
            //si hay un error, mostramos el error
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
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
    //creamos el metodo para actualizar un cliente
    public void actualizar(Clientescharco clientescharco)
    {
        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();
        //creamos la consulta para actualizar un cliente
        String query = "UPDATE clientes SET nombre = ?, telefono = ?, direccion = ?, correo = ? WHERE id = ?";
//intentamos actualizar un cliente
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientescharco.getNombre());
            pst.setString(2, clientescharco.getTelefono());
            pst.setString(3, clientescharco.getDireccion());
            pst.setString(4, clientescharco.getCorreo());
            pst.setInt(5, clientescharco.getId());
//si el resultado es mayor a 0, mostramos un mensaje de que el cliente ha sido actualizado correctamente
        int resultado = pst.executeUpdate();
        if (resultado > 0)
        {
            JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
        } else
        {
            JOptionPane.showMessageDialog(null, "Error al actualizar cliente.");
        }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
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
}
