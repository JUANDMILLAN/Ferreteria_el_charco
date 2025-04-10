package Vistas;
import Conexion.conexionBD;
import Entidad.Proveedorescharco;
import Modelos.ProveedorescharcoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProveedoresGUI extends JPanel {
    public JPanel mainPanel;
    private JTable table1;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton editarButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    int filas = 0;

    ProveedorescharcoDAO proveedorescharcoDAO = new ProveedorescharcoDAO();

    public ProveedoresGUI() {

        add(mainPanel);
        mostrarDatos();
        textField3.setEnabled(false);


        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                String contacto = textField2.getText();

                Proveedorescharco proveedorescharco = new Proveedorescharco(0, nombre, contacto);
                proveedorescharcoDAO.agregar(proveedorescharco);
                clear();
                mostrarDatos();

            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_proveedor = Integer.parseInt(textField3.getText());
                proveedorescharcoDAO.eliminar(id_proveedor);
                clear();
                mostrarDatos();
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_proveedor = Integer.parseInt(textField3.getText());
                String nombre = textField1.getText();
                String contacto = textField2.getText();

                Proveedorescharco proveedorescharco = new Proveedorescharco(id_proveedor, nombre, contacto);
                proveedorescharcoDAO.actualizar(proveedorescharco);
                clear();
                mostrarDatos();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selecfila = table1.getSelectedRow();
                if (selecfila >= 0) {
                    textField3.setText(table1.getValueAt(selecfila, 0).toString());
                    textField1.setText(table1.getValueAt(selecfila, 1).toString());
                    textField2.setText(table1.getValueAt(selecfila, 2).toString());
                    filas = selecfila;
                }
            }
        });
    }
    public void clear() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }
    public void mostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Contacto");


        table1.setModel(modelo);

        Connection con = new conexionBD().getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM proveedores";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_proveedor");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("contacto");

                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Proveedores");
        frame.setContentPane(new ProveedoresGUI().mainPanel);
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
