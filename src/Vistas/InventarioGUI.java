package Vistas;

import Conexion.conexionBD;
import Entidad.inventarioProductos;
import Modelos.InventarioProductoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InventarioGUI extends JPanel {
    private JPanel mainPanel;
    private JButton eliminarButton;
    private JButton agregarButton;
    private JButton editarButton;
    private JTable table1;
    private JTextField textFieldid;
    private JTextField textFieldnombreproducto;
    private JTextField textFieldpcantidad;
    private JTextField textFieldprecioprove;
    private JTextField textFieldprecioventa;
    private JTextField textField1;
    int filas = 0;

    InventarioProductoDAO inventarioProductoDAO = new InventarioProductoDAO();

    public InventarioGUI() {
        add(mainPanel);
        textFieldid.setVisible(false);
        mostrarDatos();// Ocultamos el campo de ID


        // Agregamos el panel generado por el GUI Designer
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores de los campos de texto
                String categoria = textField1.getText();
                String nombre_producto = textFieldnombreproducto.getText();
                int cantidad = Integer.parseInt(textFieldpcantidad.getText());
                int precioCompra = Integer.parseInt(textFieldprecioprove.getText());
                int precioVenta = Integer.parseInt(textFieldprecioventa.getText());

                // Crear un objeto inventarioProductos con los valores ingresados
                inventarioProductos nuevoProducto = new inventarioProductos(0,  categoria, nombre_producto, cantidad, precioCompra,precioVenta);
                inventarioProductoDAO.agregar(nuevoProducto);
                clear();
                mostrarDatos(); // Actualizar la tabla después de agregar el producto
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el ID del producto a eliminar
                int id_producto = Integer.parseInt(textFieldid.getText());
                inventarioProductoDAO.eliminar(id_producto);
                clear();
                mostrarDatos(); // Actualizar la tabla después de eliminar el producto
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textFieldid.getText());

                String categoria = textField1.getText();
                String nombre = textFieldnombreproducto.getText();
                int cantidad = Integer.parseInt(textFieldpcantidad.getText());
                int precioCompra = Integer.parseInt(textFieldprecioprove.getText());
                int precioVenta = Integer.parseInt(textFieldprecioventa.getText());



                // Crear un objeto inventarioProductos con los valores ingresados
                inventarioProductos productoActualizado = new inventarioProductos(id, categoria, nombre, cantidad, precioCompra, precioVenta);
                inventarioProductoDAO.actualizar(productoActualizado);
                clear();
                mostrarDatos(); // Actualizar la tabla después de editar el producto
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selecfila = table1.getSelectedRow();
                if (selecfila >= 0) {
                    textFieldid.setText(table1.getValueAt(selecfila, 0).toString());
                    textField1.setText((String) table1.getValueAt(selecfila, 1));
                    textFieldnombreproducto.setText((String) table1.getValueAt(selecfila, 2));
                    textFieldpcantidad.setText(table1.getValueAt(selecfila, 3).toString());
                    textFieldprecioprove.setText(table1.getValueAt(selecfila, 4).toString());
                    textFieldprecioventa.setText(table1.getValueAt(selecfila, 5).toString());
                    filas = selecfila;


                }
            }
        });
    }

    public void clear() {
        textFieldnombreproducto.setText("");
        textFieldprecioprove.setText("");
        textField1.setText("");
        textFieldprecioventa.setText("");
        textFieldpcantidad.setText("");
        textFieldid.setText(""); // Limpia el campo de ID
    }

    public void mostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Categoria");
        modelo.addColumn("Nombre Producto");
        modelo.addColumn("Precio Proveedor");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Cantidad Stock");

        table1.setModel(modelo);

        Connection con = new conexionBD().getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM inventario_productos";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Object[] fila = new Object[6];

                fila[0] = rs.getInt("id_producto");
                fila[1] = rs.getString("categoria");
                fila[2] = rs.getString("nombre_producto");
                fila[3] = rs.getString("precio_proveedor");
                fila[4] = rs.getString("precio_venta");
                fila[5] = rs.getString("cantidad_stock");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Inventario");
        frame.setContentPane(new InventarioGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 500);
        frame.setResizable(false);
    }



    public JPanel getMainPanel() {

        return mainPanel;
    }
}
