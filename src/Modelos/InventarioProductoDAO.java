package Modelos;

import Conexion.conexionBD;
import Entidad.inventarioProductos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventarioProductoDAO {
    //instanciamos un objeto de la clase conexion para poder conectarnos a la base de datos
    private Conexion.conexionBD conexionBD = new conexionBD();

    //creamos el metodo para agregar un nuevo producto
    public  void agregar(inventarioProductos inventarioProductos)
    {
        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();

        //creamos la consulta para insertar un nuevo producto
        String query = "INSERT INTO inventario_productos (categoria, nombre_producto, precio_proveedor, precio_venta, cantidad_stock) VALUES (?, ?, ?, ?, ?)";
        //intentamos insertar un nuevo producto
        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, inventarioProductos.getCategoria());
            pst.setString(2, inventarioProductos.getNombre_producto());
            pst.setInt(3, inventarioProductos.getPrecio_proveedor());
            pst.setInt(4, inventarioProductos.getPrecio_venta());
            pst.setInt(5, inventarioProductos.getCantidad_stock());
//si el resultado es mayor a 0, mostramos un mensaje de que el producto ha sido agregado correctamente
            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "producto agregado correctamente.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al agregar el producto.");
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
    //creamos el metodo para eliminar un producto
    public void eliminar(int id_producto)
    {
        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();

        //creamos la consulta para eliminar un producto
        String query = "DELETE FROM inventario_productos WHERE id_producto = ?";

//intentamos eliminar un cliente
        try
        {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_producto);
//si el resultado es mayor a 0, mostramos un mensaje de que el producto ha sido eliminado correctamente
            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "producto eliminado correctamente.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error al eliminar el producto.");
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
    //creamos el metodo para actualizar un producto
    public void actualizar(inventarioProductos inventarioProductos)
    {
        //creamos un objeto de la clase conexion para poder conectarnos a la base de datos
        Connection con = conexionBD.getConnection();
        //creamos la consulta para actualizar un producto
        String query = "UPDATE inventario_productos SET categoria = ?, nombre_producto = ?, precio_proveedor = ?, precio_venta = ?, cantidad_stock = ? WHERE id_producto = ?";
//intentamos actualizar un producto
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, inventarioProductos.getCategoria());
            pst.setString(2, inventarioProductos.getNombre_producto());
            pst.setDouble(3, inventarioProductos.getPrecio_proveedor());
            pst.setDouble(4, inventarioProductos.getPrecio_venta());
            pst.setInt(5, inventarioProductos.getCantidad_stock());
            pst.setInt(6, inventarioProductos.getId_producto());
//si el resultado es mayor a 0, mostramos un mensaje de que el producto ha sido actualizado correctamente
            int resultado = pst.executeUpdate();
            if (resultado > 0)
            {
                JOptionPane.showMessageDialog(null, "producto actualizado correctamente.");
            } else
            {
                JOptionPane.showMessageDialog(null, "Error al actualizar el producto.");
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