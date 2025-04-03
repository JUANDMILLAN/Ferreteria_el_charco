package Vistas;

import Conexion.ConexionBD;
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

public class ProveedorescharcoGUI {
    private JPanel main;
    private JTable tabla;
    private JTextField idText;
    private JTextField nombreText;
    private JTextField contactoText;
    private JButton eliminarButton;
    private JButton actualizarButton;
    private JButton agregarButton;
    ProveedorescharcoDAO proveedorescharcoDAO = new ProveedorescharcoDAO();
    ConexionBD conexionBD = new ConexionBD();
    int filas = 0;

    public ProveedorescharcoGUI()
    {
        mostrar();

        agregarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrar();
                String nombre = nombreText.getText();
                String contacto = contactoText.getText();
                Proveedorescharco proveedorescharco = new Proveedorescharco(0,nombre,contacto);
                proveedorescharcoDAO.agregar(proveedorescharco);
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrar();
                String nombre = nombreText.getText();
                String contacto = contactoText.getText();
                int id_proveedor = Integer.parseInt(idText.getText());
                Proveedorescharco proveedorescharco = new Proveedorescharco(id_proveedor,nombre,contacto);
                proveedorescharcoDAO.actualizar(proveedorescharco);
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrar();
                int id_proveedor = Integer.parseInt(idText.getText());
                proveedorescharcoDAO.eliminar(id_proveedor);
            }
        });
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int select_fila = tabla.getSelectedRow();

                if (select_fila >= 0){
                    idText.setText((String) tabla.getValueAt(select_fila,0));
                    nombreText.setText((String) tabla.getValueAt(select_fila,1));
                    contactoText.setText((String) tabla.getValueAt(select_fila,2));

                    filas = select_fila;
                }
            }
        });
    }

    public void mostrar ()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("nombre");
        model.addColumn("contacto");
        tabla.setModel(model);
        String[] dato = new String[3];
        Connection con = conexionBD.getConnection();

        try {
            Statement stat = con.createStatement();
            String  query = "SELECT * FROM proveedores";
            ResultSet fb = stat.executeQuery(query);

            while (fb.next())
            {
                dato[0] = fb.getString(1);
                dato[1] = fb.getString(2);
                dato[2] = fb.getString(3);
                model.addRow(dato);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("proveedores");
        frame.setContentPane(new ProveedorescharcoGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(880,700);
        frame.setResizable(false);
    }
}
