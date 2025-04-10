package Vistas;
import Conexion.conexionBD;
import Entidad.Personalcharco;
import Modelos.PersonalcharcoDAO;
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

public class PersonalcharcoGUI extends JPanel {
    private JPanel mainPanel;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JTextField textField2;
    int filas = 0;
    // Aquí puedes agregar la lógica de conexión a la base de datos y el modelo
     PersonalcharcoDAO personalcharcoDAO = new PersonalcharcoDAO();


    public PersonalcharcoGUI() {
        // Este panel ya está armado desde el diseñador y contiene todos los componentes
        add(mainPanel);
        mostrarDatos();
        textField2.setEnabled(false);
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField3.getText();
                String cargo = comboBox1.getSelectedItem().toString();
                double salario = Double.parseDouble(textField1.getText());

                Personalcharco personalcharco = new Personalcharco(0, nombre, cargo, salario);
                personalcharcoDAO.agregar(personalcharco);
                clear();
                mostrarDatos();


            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField2.getText());
                personalcharcoDAO.eliminar(id);
                clear();
                mostrarDatos();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField3.getText();
                String cargo = comboBox1.getSelectedItem().toString();
                double salario = Double.parseDouble(textField1.getText());
                int id = Integer.parseInt(textField2.getText());

                Personalcharco personalcharco = new Personalcharco(id, nombre, cargo, salario);
                personalcharcoDAO.actualizar(personalcharco);
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
                    textField2.setText(table1.getValueAt(selecfila, 0).toString()); // ID
                    textField3.setText(table1.getValueAt(selecfila, 1).toString());
                    textField1.setText(table1.getValueAt(selecfila, 2).toString()); // Salario
                    comboBox1.setSelectedItem(table1.getValueAt(selecfila, 3).toString()); // Cargo
                    filas = selecfila;

                }
            }
        });
    }
    public void clear() {
        textField1.setText("");
        textField3.setText("");
        comboBox1.setSelectedIndex(0);
    }
    public void mostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("salario");
        modelo.addColumn("Cargo");


        table1.setModel(modelo);

        Connection con = new conexionBD().getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM empleados";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id_empleado");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("salario");
                fila[3] = rs.getString("cargo");


                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Personalcharco");
        frame.setContentPane(new PersonalcharcoGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(700, 500);
        frame.setResizable(false);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Aquí puedes ir agregando la lógica de los botones si lo necesitas
}
