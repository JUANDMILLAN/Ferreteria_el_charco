package Clientescharco;
import Conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientescharcoGUI {
    private JTable table1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JButton actualizarButton;

    int filas = 0;
    // Instancia de la clase ClienteDAO
    ClientescharcoDAO clientescharcoDAO = new ClientescharcoDAO();
    // Constructor de la clase
    public ClientescharcoGUI() {
        textField1.setEnabled(false);
        obtenerDatos();
        agregarButton.addActionListener(e -> {
            String nombre = textField2.getText();
            String telefono = textField3.getText();
            String direccion = textField4.getText();
            String correo = textField5.getText();

            Clientescharco clientescharco = new Clientescharco(0, nombre, telefono, direccion, correo);
            clientescharcoDAO.agregar(clientescharco);
            obtenerDatos();
            clear();
        });

        actualizarButton.addActionListener(e -> {
            String nombre = textField2.getText();
            String telefono = textField3.getText();
            String direccion = textField4.getText();
            String correo = textField5.getText();
            int id = Integer.parseInt(textField1.getText());

            Clientescharco clientescharco = new Clientescharco(id, nombre, telefono, direccion, correo);
            clientescharcoDAO.actualizar(clientescharco);
            obtenerDatos();
            clear();
        });

        eliminarButton.addActionListener(e -> {
            int id = Integer.parseInt(textField1.getText());
            clientescharcoDAO.eliminar(id);
            obtenerDatos();
            clear();
        });
        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectfila = table1.getSelectedRow();
                if (selectfila >= 0) {
                    textField1.setText(table1.getValueAt(selectfila, 0).toString());
                    textField2.setText(table1.getValueAt(selectfila, 1).toString());
                    textField3.setText(table1.getValueAt(selectfila, 2).toString());
                    textField4.setText(table1.getValueAt(selectfila, 3).toString());
                    textField5.setText(table1.getValueAt(selectfila, 4).toString());
                    filas = selectfila;
                }
            }

        });

    }
    public void clear() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }
    // metodo para obtener los datos de la tabla
    public void obtenerDatos()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Telefono");
        model.addColumn("Direccion");
        model.addColumn("Correo");

        table1.setModel(model);
        String[] dato = new String[5];

        Connection con = new ConexionBD().getConnection();
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM clientescharco";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                dato[0] = rs.getString("id");
                dato[1] = rs.getString("nombre");
                dato[2] = rs.getString("telefono");
                dato[3] = rs.getString("direccion");
                dato[4] = rs.getString("correo");
                model.addRow(dato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("ClientescharcoGUI");
        frame.setContentPane(new ClientescharcoGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setResizable(false);
    }
}

