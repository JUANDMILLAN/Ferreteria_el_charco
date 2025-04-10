package Vistas;

import Conexion.conexionBD;
import Entidad.Clientescharco;
import Modelos.ClientescharcoDAO;

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

import java.awt.*;

public class ClientescharcoGUI extends JPanel {
    private JPanel mainPanel;
    private JTable table1;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JTextField textFieldnombrecliente;
    private JTextField textFielddireccioncliente;
    private JTextField textFieldtelefonocliente;
    private JTextField textFieldcorreocliente;
    private JTextField textField1;
    int filas = 0;

    ClientescharcoDAO clientescharcoDAO = new ClientescharcoDAO();

    public ClientescharcoGUI() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000,1000));
        add(mainPanel);
        textField1.setEnabled(false);
        mostrarDatos();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldnombrecliente.getText();
                String telefono = textFieldtelefonocliente.getText();
                String direccion = textFielddireccioncliente.getText();
                String correo = textFieldcorreocliente.getText();


                Clientescharco clientes = new Clientescharco(0, nombre, direccion, telefono, correo);
                clientescharcoDAO.agregar(clientes);
                clear();
                mostrarDatos();


            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldnombrecliente.getText();
                String telefono = textFieldtelefonocliente.getText();
                String direccion = textFielddireccioncliente.getText();
                String correo = textFieldcorreocliente.getText();
                int id = Integer.parseInt(textField1.getText());



                Clientescharco clientes = new Clientescharco(id, nombre,  telefono,direccion, correo);
                clientescharcoDAO.actualizar(clientes);
                clear();
                mostrarDatos();


            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField1.getText());
                clientescharcoDAO.eliminar(id);
                clear();
                mostrarDatos();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selecfila = table1.getSelectedRow();
                if (selecfila >= 0) {
                    textField1.setText(table1.getValueAt(selecfila, 0).toString());
                    textFieldnombrecliente.setText((String) table1.getValueAt(selecfila, 1));
                    textFieldtelefonocliente.setText((String) table1.getValueAt(selecfila, 2));
                    textFielddireccioncliente.setText((String) table1.getValueAt(selecfila, 3));
                    textFieldcorreocliente.setText((String) table1.getValueAt(selecfila, 4));
                    filas = selecfila;
                }
            }
        });


    }
    public void clear() {
        textFieldnombrecliente.setText("");
        textFieldtelefonocliente.setText("");
        textFielddireccioncliente.setText("");
        textFieldcorreocliente.setText("");
        textField1.setText(""); // Limpia el campo de ID
    }

    public void mostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Dirección");
        modelo.addColumn("Correo");

        table1.setModel(modelo);

        Connection con = new conexionBD().getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM clientes";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_cliente");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("direccion");
                fila[4] = rs.getString("correo");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clientescharco");
        frame.setContentPane(new ClientescharcoGUI().mainPanel);
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
