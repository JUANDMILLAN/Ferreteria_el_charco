package Vistas;

import Conexion.conexionBD;
import Entidad.inventarioProductos;
import Modelos.InventarioProductoDAO;
import Servicios.VentaServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.sql.*;

/**
 * La clase {@code InventarioGUI} representa la interfaz gráfica para gestionar el inventario de productos.
 * Permite agregar, editar y eliminar productos, además de mostrar los productos en una tabla.
 */
public class InventarioGUI extends JPanel {
    private JPanel mainPanel;
    private JButton eliminarButton;
    private JButton agregarButton;
    private JButton editarButton;
    private JTable table1;
    private JTextField textFieldid;
    private JTextField textFieldnombreproducto;
    private JTextField textFieldpcantidad;
    private JTextField textFieldprecioproveedor;
    private JTextField textFieldprecioventa;
    private JComboBox comboBox1;
    private JTextField Buscaritem; // Campo de texto para la búsqueda
    int filas = 0;

    InventarioProductoDAO inventarioProductoDAO = new InventarioProductoDAO();
    VentaServicio ventaServicio = new VentaServicio(); // Instancia de VentaServicio

    /**
     * Constructor de la clase {@code InventarioGUI}. Inicializa los componentes de la interfaz gráfica,
     * carga los datos del inventario y configura las acciones de los botones.
     */
    public InventarioGUI() {
        add(mainPanel);
        textFieldid.setEnabled(false);
        mostrarDatos(); // Cargar los datos de inventario
        ventaServicio.verificarStockBajo(table1); // Verificar stock bajo después de cargar los datos

        // Acción del botón Agregar
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String categoria = comboBox1.getSelectedItem().toString();
                    String nombre_producto = textFieldnombreproducto.getText();
                    int precioCompra = Integer.parseInt(textFieldprecioproveedor.getText());
                    int precioVenta = Integer.parseInt(textFieldprecioventa.getText());
                    int cantidad = Integer.parseInt(textFieldpcantidad.getText());

                    inventarioProductos nuevoProducto = new inventarioProductos(0, categoria, nombre_producto, precioCompra, precioVenta, cantidad);
                    inventarioProductoDAO.agregar(nuevoProducto);
                    clear();
                    mostrarDatos();
                    ventaServicio.verificarStockBajo(table1); // Verificar nuevamente stock bajo después de agregar un producto
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingresar un valor valido");
                }
            }
        });

        // Acción del botón Eliminar
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id_producto = Integer.parseInt(textFieldid.getText());
                    inventarioProductoDAO.eliminar(id_producto);
                    clear();
                    mostrarDatos();
                    ventaServicio.verificarStockBajo(table1); // Verificar nuevamente stock bajo después de eliminar un producto
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto válido para eliminar.");
                }
            }
        });

        // Acción del botón Editar
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(textFieldid.getText());
                    String categoria = comboBox1.getSelectedItem().toString();
                    String nombre = textFieldnombreproducto.getText();
                    int precioCompra = Integer.parseInt(textFieldprecioproveedor.getText());
                    int precioVenta = Integer.parseInt(textFieldprecioventa.getText());
                    int cantidad = Integer.parseInt(textFieldpcantidad.getText());

                    inventarioProductos productoActualizado = new inventarioProductos(id, categoria, nombre, precioCompra, precioVenta, cantidad);
                    inventarioProductoDAO.actualizar(productoActualizado);
                    clear();
                    mostrarDatos();
                    ventaServicio.verificarStockBajo(table1); // Verificar nuevamente stock bajo después de actualizar un producto
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingresar un valor valido");
                }
            }
        });

        // Acción al hacer clic en la tabla
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selecfila = table1.getSelectedRow();
                if (selecfila >= 0) {
                    textFieldid.setText(table1.getValueAt(selecfila, 0).toString());
                    comboBox1.setSelectedItem((String) table1.getValueAt(selecfila, 1));
                    textFieldnombreproducto.setText((String) table1.getValueAt(selecfila, 2));
                    textFieldprecioproveedor.setText(table1.getValueAt(selecfila, 3).toString());
                    textFieldprecioventa.setText(table1.getValueAt(selecfila, 4).toString());
                    textFieldpcantidad.setText(table1.getValueAt(selecfila, 5).toString());
                    filas = selecfila;
                }
            }
        });

        // KeyListener para el campo de búsqueda
        Buscaritem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTabla(Buscaritem.getText()); // Filtrar la tabla cada vez que se escribe en el campo
            }
        });
    }

    /**
     * Limpia los campos de texto en la interfaz.
     */
    public void clear() {
        textFieldnombreproducto.setText("");
        textFieldprecioproveedor.setText("");
        comboBox1.setSelectedIndex(0);
        textFieldprecioventa.setText("");
        textFieldpcantidad.setText("");
        textFieldid.setText("");
    }

    /**
     * Muestra los datos de los productos del inventario en la tabla.
     */
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
                fila[3] = rs.getInt("precio_proveedor");
                fila[4] = rs.getInt("precio_venta");
                fila[5] = rs.getInt("cantidad_stock");

                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }

    /**
     * Filtra las filas de la tabla según el texto ingresado en el campo de búsqueda.
     *
     * @param busqueda El texto de búsqueda.
     */
    public void filtrarTabla(String busqueda) {
        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);

        // Filtro por el texto ingresado (ID o cualquier otra columna)
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + busqueda));
    }

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return El panel principal donde se muestra la tabla de productos.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Devuelve la tabla de productos.
     *
     * @return La tabla que muestra los productos del inventario.
     */
    public JTable getTable1() {
        return table1;
    }

    /**
     * Establece la tabla de productos.
     *
     * @param table1 La tabla que se desea establecer.
     */
    public void setTable1(JTable table1) {
        this.table1 = table1;
    }
}
