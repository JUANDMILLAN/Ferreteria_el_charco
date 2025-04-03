package Modelos;

import Conexion.ConexionBD;
import Entidad.Clientescharco;
import Entidad.Proveedorescharco;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProveedorescharcoDAO {
    //instanciamos un objeto de la clase conexion para poder conectarnos a la base de datos

    private final ConexionBD conexionBD = new ConexionBD();

    //creamos el metodo para agregar un nuevo proveedor
    public void agregar(Proveedorescharco proveedorescharco) {

        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();

        //creamos la consulta para insertar un nuevo cliente
        String query = "INSERT INTO proveedores (id_proveedor,nombre,contacto) VALUES (?, ?, ?)";

//intentamos insertar un nuevo proveedor
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, proveedorescharco.getId());
            pst.setString(2, proveedorescharco.getNombre());
            pst.setString(3, proveedorescharco.getContacto());
//si el resultado es mayor a 0, mostramos un mensaje de que el cliente ha sido agregado correctamente
                int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "proveedor agregado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el proveedor.");
            }
            //si hay un error, mostramos el error
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

    //creamos el metodo para eliminar un proveedor
    public void eliminar(int id_proveedor) {
        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();
        //creamos la consulta para eliminar un proveedor
        String query = "DELETE FROM proveedores WHERE id_proveedor = ?";
        {
//intentamos eliminar un cliente
            try {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, id_proveedor);
//si el resultado es mayor a 0, mostramos un mensaje de que el proveedor ha sido eliminado correctamente
                int resultado = pst.executeUpdate();
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "proveedor eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor.");
                }
                //si hay un error, mostramos el error
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
        //creamos el metodo para actualizar un cliente
        public void actualizar (Proveedorescharco Proveedorescharco)
        {
            //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
            Connection con = conexionBD.getConnection();
            //creamos la consulta para actualizar un cliente
            String query = "UPDATE proveedores SET nombre = ?,contacto= ? WHERE id_proveedor = ?";
//intentamos actualizar un cliente
            try {
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, Proveedorescharco.getNombre());
                pst.setString(2, Proveedorescharco.getContacto());
                pst.setInt(3, Proveedorescharco.getId());
//si el resultado es mayor a 0, mostramos un mensaje de que el cliente ha sido actualizado correctamente
                int resultado = pst.executeUpdate();
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(null, "proveedor actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar proveedor.");
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

