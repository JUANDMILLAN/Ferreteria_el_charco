package Vistas;

import javax.swing.*;

/**
 * La clase {@code HistorialOrdenesGUI} proporciona una interfaz gráfica para mostrar el historial de órdenes.
 * Incluye una tabla para visualizar las órdenes registradas.
 */
public class HistorialOrdenesGUI extends JPanel {
    private JPanel mainPanel;
    private JTable table1;

    /**
     * Constructor de la clase {@code HistorialOrdenesGUI}. Inicializa los componentes gráficos y agrega el panel principal.
     */
    public HistorialOrdenesGUI() {
        add(mainPanel);
    }

    /**
     * Devuelve el panel principal de la interfaz gráfica.
     *
     * @return Panel principal de la interfaz.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
