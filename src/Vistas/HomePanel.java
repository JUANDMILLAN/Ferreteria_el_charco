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
    private JComboBox<String> comboBox1; // Asegúrate de que esté en el .form con opciones: Diario, Semanal, Mensual

    public HomePanel() {
        add(mainPanel);

        // Configurar ComboBox con opciones si no lo hiciste en el .form
        if (comboBox1.getItemCount() == 0) {
            comboBox1.addItem("Diario");
            comboBox1.addItem("Semanal");
            comboBox1.addItem("Mensual");
        }

        // Cargar ventas por defecto (Diarias)
        cargarDatosFiltrados("Diario");

        // Acción al cambiar filtro
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtroSeleccionado = (String) comboBox1.getSelectedItem();
                cargarDatosFiltrados(filtroSeleccionado);
            }
        });
    }

    // Cargar ventas filtradas en la tabla1
    public void cargarDatosFiltrados(String filtro) {
        VentaServicio servicio = new VentaServicio();

        // 🟩 Tabla 1: Ventas Totales (solo id, total, fecha y estado)
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

        // 🟨 Tabla 3: Clientes Top (mostrar nombre del cliente)
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
