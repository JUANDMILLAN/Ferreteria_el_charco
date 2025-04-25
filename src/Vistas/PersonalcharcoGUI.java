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

/**
 * Clase que representa la vista para gestionar el personal de la empresa.
 * Esta clase permite agregar, actualizar y eliminar registros de empleados,
 * mostrando la información en una tabla y permitiendo realizar operaciones
 * mediante un formulario de entrada.
 */
public class PersonalcharcoGUI extends JPanel {
    private JPanel mainPanel; // Panel principal de la vista
    private JTable table1; // Tabla para mostrar los empleados
    private JTextField textField1; // Campo de texto para ingresar el salario
    private JTextField textField3; // Campo de texto para ingresar el nombre
    private JComboBox comboBox1; // ComboBox para seleccionar el cargo del empleado
    private JButton agregarButton; // Botón para agregar un nuevo empleado
    private JButton actualizarButton; // Botón para actualizar un empleado
    private JButton eliminarButton; // Botón para eliminar un empleado
    private JTextField textField2; // Campo de texto para mostrar el ID del empleado seleccionado
    int filas = 0; // Variable para almacenar la fila seleccionada en la tabla

    // Instancia de PersonalcharcoDAO para manejar las operaciones con la base de datos
    PersonalcharcoDAO personalcharcoDAO = new PersonalcharcoDAO();

    /**
     * Constructor de la clase PersonalcharcoGUI.
     * Inicializa los componentes de la interfaz y configura las acciones
     * de los botones para agregar, actualizar y eliminar empleados.
     */
    public PersonalcharcoGUI() {
        // Este panel ya está armado desde el diseñador y contiene todos los componentes
        add(mainPanel);
        mostrarDatos(); // Mostrar los datos de los empleados en la tabla
        textField2.setEnabled(false); // El campo de ID no es editable

        // Acción del botón para agregar un nuevo empleado
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int salario = Integer.parseInt(textField1.getText());
                    String nombre = textField3.getText();
                    String cargo = comboBox1.getSelectedItem().toString();

                    // Crear un objeto Personalcharco con los datos del formulario
                    Personalcharco personalcharco = new Personalcharco(0, nombre, cargo, salario);
                    personalcharcoDAO.agregar(personalcharco); // Agregar el nuevo empleado
                    clear(); // Limpiar los campos
                    mostrarDatos(); // Actualizar la tabla con los nuevos datos
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El salario debe ser un número entero.");
                }
                mostrarDatos();
            }
        });

        // Acción del botón para eliminar un empleado
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(textField2.getText());
                personalcharcoDAO.eliminar(id); // Eliminar el empleado por su ID
                clear(); // Limpiar los campos
                mostrarDatos(); // Actualizar la tabla
            }
        });

        // Acción del botón para actualizar un empleado
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int salario = Integer.parseInt(textField1.getText());
                    String nombre = textField3.getText();
                    String cargo = comboBox1.getSelectedItem().toString();
                    int id = Integer.parseInt(textField2.getText());

                    // Crear un objeto Personalcharco con los datos del formulario
                    Personalcharco personalcharco = new Personalcharco(id, nombre, cargo, salario);
                    personalcharcoDAO.actualizar(personalcharco); // Actualizar el empleado
                    clear(); // Limpiar los campos
                    mostrarDatos(); // Actualizar la tabla
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El salario debe ser un número entero.");
                }
                mostrarDatos();
            }
        });

        // Acción al seleccionar una fila en la tabla
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selecfila = table1.getSelectedRow();
                if (selecfila >= 0) {
                    // Rellenar los campos con los datos de la fila seleccionada
                    textField2.setText(table1.getValueAt(selecfila, 0).toString()); // ID
                    textField3.setText(table1.getValueAt(selecfila, 1).toString());
                    textField1.setText(table1.getValueAt(selecfila, 2).toString()); // Salario
                    comboBox1.setSelectedItem(table1.getValueAt(selecfila, 3).toString()); // Cargo
                    filas = selecfila;
                }
            }
        });
    }

    /**
     * Limpia los campos del formulario.
     */
    public void clear() {
        textField1.setText("");
        textField3.setText("");
        comboBox1.setSelectedIndex(0);
    }

    /**
     * Muestra los datos de los empleados en la tabla.
     * Recupera los datos de la base de datos y los carga en la tabla.
     */
    public void mostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Salario");
        modelo.addColumn("Cargo");

        table1.setModel(modelo);

        Connection con = new conexionBD().getConnection();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM empleados"; // Consulta para obtener los empleados
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id_empleado");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("salario");
                fila[3] = rs.getString("cargo");

                modelo.addRow(fila); // Agregar fila a la tabla
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al mostrar los datos: " + e.getMessage());
        }
    }

    /**
     * Devuelve el panel principal de la vista.
     * @return El panel principal que contiene los componentes de la vista.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
