package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entidad.inventarioProductos;
import Entidad.Clientescharco;
import Entidad.Personalcharco;
import Modelos.ClientescharcoDAO;
import Modelos.PersonalcharcoDAO;
import Servicios.VentaServicio;
import utilidades.CorreoFactura;
import utilidades.GeneradorPDF;

/**
 * VentasGUI representa la interfaz principal para la gestión de ventas.
 * Permite seleccionar productos, clientes, empleados y registrar una factura.
 */
public class VentasGUI extends JPanel {
    public JPanel mainPanel;
    private JTable table1;
    private JTable tablaFactura;
    private JButton guardarVentaButton;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JLabel labelIva;
    private JLabel labelTotal;
    private JComboBox comboClientes;
    private JComboBox comboEmpleados;
    private JComboBox comboEstado;
    private JTextField buscarProduc;
    private VentaServicio ventaServicio;
    private JSpinner spinner1;

    /**
     * Constructor que inicializa la interfaz de ventas y sus eventos.
     */
    public VentasGUI() {
        ventaServicio = new VentaServicio();
        add(mainPanel);

        spinner1.setModel(new SpinnerNumberModel(1, 1, 1000, 1));

        cargarProductosEnTabla();
        cargarCombos();

        tablaFactura.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Precio Unitario", "Cantidad", "Subtotal"}
        ));

        // Evento del botón Agregar producto a la factura
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int id = Integer.parseInt(table1.getValueAt(filaSeleccionada, 0).toString());
                    String nombre = table1.getValueAt(filaSeleccionada, 2).toString();
                    int precio = Integer.parseInt(table1.getValueAt(filaSeleccionada, 4).toString());
                    int cantidadSpinner = (int) spinner1.getValue();

                    DefaultTableModel modeloFactura = (DefaultTableModel) tablaFactura.getModel();
                    boolean existe = false;

                    for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                        int idExistente = Integer.parseInt(modeloFactura.getValueAt(i, 0).toString());
                        if (idExistente == id) {
                            int cantidadActual = Integer.parseInt(modeloFactura.getValueAt(i, 3).toString());
                            cantidadActual += cantidadSpinner;
                            modeloFactura.setValueAt(cantidadActual, i, 3);
                            modeloFactura.setValueAt(cantidadActual * precio, i, 4);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Object[] fila = new Object[]{id, nombre, precio, cantidadSpinner, precio * cantidadSpinner};
                        modeloFactura.addRow(fila);
                    }

                    actualizarTotales();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto para agregar a la factura.");
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tablaFactura.getModel();
                model.setRowCount(0);
                labelIva.setText("IVA: $ 0.00");
                labelTotal.setText("Total: $ 0.00");
            }
        });

        // Evento del botón Guardar Venta
        guardarVentaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clientescharco cliente = (Clientescharco) comboClientes.getSelectedItem();
                Personalcharco empleado = (Personalcharco) comboEmpleados.getSelectedItem();
                String estado = comboEstado.getSelectedItem() != null ? comboEstado.getSelectedItem().toString() : "pendiente";

                if (cliente == null || empleado == null) {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente y un empleado.");
                    return;
                }

                int idCliente = cliente.getId();
                int idEmpleado = empleado.getId();

                DefaultTableModel modeloFactura = (DefaultTableModel) tablaFactura.getModel();
                if (modeloFactura.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Agrega productos a la factura.");
                    return;
                }

                int subtotal = 0;
                for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                    int cantidad = Integer.parseInt(modeloFactura.getValueAt(i, 3).toString());
                    int precio = Integer.parseInt(modeloFactura.getValueAt(i, 2).toString());
                    subtotal += cantidad * precio;
                }

                int iva = (int) (subtotal * 0.19);
                int total = subtotal + iva;

                int idVenta = ventaServicio.guardarVenta(idCliente, idEmpleado, total, estado);

                for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                    int idProducto = Integer.parseInt(modeloFactura.getValueAt(i, 0).toString());
                    int precioUnitario = Integer.parseInt(modeloFactura.getValueAt(i, 2).toString());
                    int cantidad = Integer.parseInt(modeloFactura.getValueAt(i, 3).toString());
                    int subtotalProducto = Integer.parseInt(modeloFactura.getValueAt(i, 4).toString());

                    ventaServicio.guardarDetalleVenta(idVenta, idProducto, cantidad, precioUnitario, subtotalProducto);
                    ventaServicio.actualizarStock(idProducto, cantidad);
                }

                String nombreCliente = cliente.getNombre();
                String correoCliente = cliente.getCorreo();

                String rutaPDF = GeneradorPDF.generarFacturaPDF(modeloFactura, nombreCliente, total, iva, subtotal);
                CorreoFactura.enviarFactura("djhon8366@gmail.com", rutaPDF);

                try {
                    java.awt.Desktop.getDesktop().open(new java.io.File(rutaPDF));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Venta registrada y factura enviada.");
                modeloFactura.setRowCount(0);
                actualizarTotales();
            }
        });

        // Evento para filtrar productos en la tabla
        buscarProduc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String busqueda = buscarProduc.getText().toLowerCase();
                filtrarTabla(busqueda);
            }
        });
    }

    /**
     * Carga los productos desde la base de datos en la tabla de selección.
     */
    private void cargarProductosEnTabla() {
        ClientescharcoDAO.InventarioDAO dao = new ClientescharcoDAO.InventarioDAO();
        ArrayList<inventarioProductos> lista = dao.obtenerProductos();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"ID", "Categoría", "Producto", "Precio Proveedor", "Precio Venta", "Stock"});

        for (inventarioProductos p : lista) {
            modelo.addRow(new Object[]{
                    p.getId_producto(),
                    p.getCategoria(),
                    p.getNombre_producto(),
                    p.getPrecio_proveedor(),
                    p.getPrecio_venta(),
                    p.getCantidad_stock()
            });
        }

        table1.setModel(modelo);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);
    }

    /**
     * Carga los datos de clientes y empleados en los JComboBox.
     */
    private void cargarCombos() {
        ClientescharcoDAO clienteDAO = new ClientescharcoDAO();
        ArrayList<Clientescharco> listaClientes = clienteDAO.obtenerTodos();
        for (Clientescharco cliente : listaClientes) {
            comboClientes.addItem(cliente);
        }

        PersonalcharcoDAO empleadoDAO = new PersonalcharcoDAO();
        ArrayList<Personalcharco> listaEmpleados = empleadoDAO.obtenerTodos();
        for (Personalcharco empleado : listaEmpleados) {
            comboEmpleados.addItem(empleado);
        }
    }

    /**
     * Calcula y actualiza los totales (IVA y total general) de la factura.
     */
    private void actualizarTotales() {
        DefaultTableModel modeloFactura = (DefaultTableModel) tablaFactura.getModel();
        int subtotal = 0;

        for (int i = 0; i < modeloFactura.getRowCount(); i++) {
            int cantidad = Integer.parseInt(modeloFactura.getValueAt(i, 3).toString());
            int precio = Integer.parseInt(modeloFactura.getValueAt(i, 2).toString());
            subtotal += cantidad * precio;
        }

        int iva = (int) (subtotal * 0.19);
        int total = subtotal + iva;

        labelIva.setText("IVA: $ " + iva);
        labelTotal.setText("Total: $ " + total);
    }

    /**
     * Filtra la tabla de productos según la búsqueda del usuario.
     * @param busqueda texto ingresado para buscar productos
     */
    private void filtrarTabla(String busqueda) {
        DefaultTableModel modelo = (DefaultTableModel) table1.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        table1.setRowSorter(sorter);

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + busqueda);
        sorter.setRowFilter(rowFilter);
    }
}
