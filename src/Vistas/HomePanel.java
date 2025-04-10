package Vistas;

import Servicios.VentaServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class HomePanel extends JPanel {
    public JPanel mainPanel;
    public JTable table1;
    public JTable table2;
    public JTable table3;
    private JComboBox<String> comboBox1; // Filtro: Diario, Semanal, Mensual
    private JComboBox<String> comboBox2; // Cambiar estado: Pendiente, Enviado, Pagado

    public HomePanel() {
        add(mainPanel);

        // Opciones para comboBox1 (filtro de fecha)
        if (comboBox1.getItemCount() == 0) {
            comboBox1.addItem("Diario");
            comboBox1.addItem("Semanal");
            comboBox1.addItem("Mensual");
        }

        // Opciones para comboBox2 (estado de la venta)
        if (comboBox2.getItemCount() == 0) {
            comboBox2.addItem("Pendiente");
            comboBox2.addItem("Enviado");
            comboBox2.addItem("Pagado");
        }

        // Cargar datos por defecto
        cargarDatosFiltrados("Diario");

        // Listener para cambiar el filtro de fechas
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtroSeleccionado = (String) comboBox1.getSelectedItem();
                cargarDatosFiltrados(filtroSeleccionado);
            }
        });

        // Listener para actualizar estado de la venta seleccionada
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table1.getSelectedRow();

                if (filaSeleccionada != -1) {
                    int idVenta = (int) table1.getValueAt(filaSeleccionada, 0);
                    String nuevoEstado = (String) comboBox2.getSelectedItem();

                    VentaServicio ventaServicio = new VentaServicio();
                    boolean actualizado = ventaServicio.actualizarEstadoVenta(idVenta, nuevoEstado);

                    if (actualizado) {
                        JOptionPane.showMessageDialog(null, "Estado actualizado correctamente.");
                        table1.setValueAt(nuevoEstado, filaSeleccionada, 3);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar el estado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una venta para cambiar el estado.");
                }
            }
        });
    }

    public void cargarDatosFiltrados(String filtro) {
        VentaServicio servicio = new VentaServicio();

        // 🟩 Tabla 1: Ventas Totales
        ResultSet rsVentas = servicio.obtenerVentasFiltradas(filtro);
        DefaultTableModel modeloVentas = new DefaultTableModel();
        modeloVentas.setColumnIdentifiers(new String[]{"ID Venta", "Total", "Fecha", "Estado"});

        try {
            while (rsVentas != null && rsVentas.next()) {
                modeloVentas.addRow(new Object[]{
                        rsVentas.getInt("id_venta"),
                        rsVentas.getDouble("total"),
                        rsVentas.getDate("fecha"),
                        rsVentas.getString("estado")
                });
            }
            table1.setModel(modeloVentas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🟦 Tabla 2: Productos Más Vendidos
        ResultSet rsProductos = servicio.obtenerProductosMasVendidos(filtro);
        DefaultTableModel modeloProductos = new DefaultTableModel();
        modeloProductos.setColumnIdentifiers(new String[]{"Producto", "Cantidad Vendida"});

        try {
            while (rsProductos != null && rsProductos.next()) {
                modeloProductos.addRow(new Object[]{
                        rsProductos.getString("nombre_producto"),
                        rsProductos.getInt("total_vendidos")
                });
            }
            table2.setModel(modeloProductos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🟨 Tabla 3: Clientes Top
        ResultSet rsClientes = servicio.obtenerClientesTop(filtro);
        DefaultTableModel modeloClientes = new DefaultTableModel();
        modeloClientes.setColumnIdentifiers(new String[]{"Nombre Cliente", "Total Comprado"});

        try {
            while (rsClientes != null && rsClientes.next()) {
                modeloClientes.addRow(new Object[]{
                        rsClientes.getString("nombre_cliente"),
                        rsClientes.getDouble("total_compras")
                });
            }
            table3.setModel(modeloClientes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
