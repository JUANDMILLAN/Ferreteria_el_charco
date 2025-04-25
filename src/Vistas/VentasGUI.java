package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Entidad.inventarioProductos;
import Entidad.Clientescharco;
import Entidad.Personalcharco;
import Modelos.ClientescharcoDAO;
import Modelos.PersonalcharcoDAO;
import Servicios.VentaServicio;
import utilidades.CorreoFactura;
import utilidades.GeneradorPDF;

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
    private VentaServicio ventaServicio;

    public VentasGUI() {
        ventaServicio = new VentaServicio();
        add(mainPanel);

        cargarProductosEnTabla();
        cargarCombos();

        // Definir columnas para la tabla de factura
        tablaFactura.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Precio Unitario", "Cantidad", "Subtotal"}
        ));

        // Acción del botón Agregar
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int id = Integer.parseInt(table1.getValueAt(filaSeleccionada, 0).toString());
                    String nombre = table1.getValueAt(filaSeleccionada, 2).toString();
                    int precio = Integer.parseInt(table1.getValueAt(filaSeleccionada, 4).toString());

                    DefaultTableModel modeloFactura = (DefaultTableModel) tablaFactura.getModel();
                    boolean existe = false;

                    for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                        int idExistente = Integer.parseInt(modeloFactura.getValueAt(i, 0).toString());
                        if (idExistente == id) {
                            int cantidadActual = Integer.parseInt(modeloFactura.getValueAt(i, 3).toString());
                            cantidadActual++;
                            modeloFactura.setValueAt(cantidadActual, i, 3);
                            modeloFactura.setValueAt(cantidadActual * precio, i, 4);
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        Object[] fila = new Object[]{id, nombre, precio, 1, precio};
                        modeloFactura.addRow(fila);
                    }

                    actualizarTotales();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto para agregar a la factura.");
                }
            }
        });


        // Acción del botón Cancelar
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tablaFactura.getModel();
                model.setRowCount(0);
                labelIva.setText("IVA: $ 0.00");
                labelTotal.setText("Total: $ 0.00");
            }
        });
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

                // Calcular total
                int subtotal = 0;
                for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                    int cantidad = Integer.parseInt(modeloFactura.getValueAt(i, 3).toString());
                    int precio = Integer.parseInt(modeloFactura.getValueAt(i, 2).toString());
                    subtotal += cantidad * precio;
                }

                int iva = (int) (subtotal * 0.19);
                int total = subtotal + iva;

                // Registrar venta
                int idVenta = ventaServicio.guardarVenta(idCliente, idEmpleado, total, estado);

                // Registrar detalle por producto
                for (int i = 0; i < modeloFactura.getRowCount(); i++) {
                    int idProducto = Integer.parseInt(modeloFactura.getValueAt(i, 0).toString());
                    int precioUnitario = Integer.parseInt(modeloFactura.getValueAt(i, 2).toString());
                    int cantidad = Integer.parseInt(modeloFactura.getValueAt(i, 3).toString());
                    int subtotalProducto = Integer.parseInt(modeloFactura.getValueAt(i, 4).toString());

                    ventaServicio.guardarDetalleVenta(idVenta, idProducto, cantidad, precioUnitario, subtotalProducto);
                    ventaServicio.actualizarStock(idProducto, cantidad);
                }



                // ➕ Generar PDF y enviar por correo
                String nombreCliente = cliente.getNombre(); // o cliente.toString()
                String correoCliente = cliente.getCorreo(); // asegúrate de tener este método

                String rutaPDF = GeneradorPDF.generarFacturaPDF(modeloFactura, nombreCliente, total, iva, subtotal);
                CorreoFactura.enviarFactura("djhon8366@gmail.com", rutaPDF); // tu correo fijo aquí
                // Abre el PDF automáticamente (opcional)
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

    }

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
    }

    private void cargarCombos() {
        // Cargar clientes
        ClientescharcoDAO clienteDAO = new ClientescharcoDAO();
        ArrayList<Clientescharco> listaClientes = clienteDAO.obtenerTodos();
        for (Clientescharco cliente : listaClientes) {
            comboClientes.addItem(cliente);
        }

        // Cargar empleados
        PersonalcharcoDAO empleadoDAO = new PersonalcharcoDAO();
        ArrayList<Personalcharco> listaEmpleados = empleadoDAO.obtenerTodos();
        for (Personalcharco empleado : listaEmpleados) {
            comboEmpleados.addItem(empleado);
        }
    }

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
}
