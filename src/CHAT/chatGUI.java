package CHAT;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Clase que representa la ventana de la interfaz de usuario del chat para el cliente.
 * Esta clase permite al usuario enviar y recibir mensajes en tiempo real a través de una conexión al servidor.
 *
 * Se muestra una interfaz con un panel de mensajes, un campo para escribir mensajes y la funcionalidad de enviar
 * los mensajes a través de un socket con el servidor.
 */
public class chatGUI extends JFrame {

    private JPanel panelMensajes;
    private JTextField campoMensaje;
    private clienteChat cliente;
    private String nombre;

    /**
     * Constructor de la clase chatGUI.
     * Inicializa la interfaz de usuario y establece la conexión con el servidor de chat.
     *
     * @param nombre El nombre del usuario que se conecta al chat.
     */
    public chatGUI(String nombre) {
        this.nombre = nombre;

        // Configuración de la ventana del chat
        setTitle("Chat - " + nombre);
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título de la ventana
        JLabel titulo = new JLabel("Ferretería El Charco 🔩", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel para los mensajes
        panelMensajes = new JPanel();
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        panelMensajes.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(panelMensajes);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);

        // Campo de texto para enviar mensajes
        campoMensaje = new JTextField();
        campoMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(campoMensaje, BorderLayout.SOUTH);

        // Acción al presionar "Enter" en el campo de texto
        campoMensaje.addActionListener(e -> {
            String mensaje = campoMensaje.getText().trim();
            if (!mensaje.isEmpty()) {
                cliente.enviarMensaje(nombre + "::" + mensaje);
                campoMensaje.setText("");
            }
        });

        // Conexión al servidor de chat
        try {
            cliente = new clienteChat("localhost", 12345, this::mostrarMensaje);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
            System.exit(0);
        }

        setVisible(true);
    }

    /**
     * Método para mostrar un mensaje en el panel de chat.
     * Se ejecuta en el hilo de la interfaz gráfica para actualizar los elementos visuales.
     *
     * @param mensajeCompleto El mensaje completo recibido del servidor.
     */
    private void mostrarMensaje(String mensajeCompleto) {
        SwingUtilities.invokeLater(() -> {
            String[] partes = mensajeCompleto.split("::", 2);
            String emisor = partes[0];
            String mensaje = partes.length > 1 ? partes[1] : mensajeCompleto;

            // Panel para mostrar el mensaje
            JPanel contenedor = new JPanel();
            contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
            contenedor.setBackground(Color.WHITE);
            contenedor.setAlignmentX(Component.LEFT_ALIGNMENT);
            contenedor.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // Etiquetas para el emisor y el mensaje
            JLabel nombreLabel = new JLabel(emisor);
            nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

            JLabel mensajeLabel = new JLabel("<html>" + mensaje + "</html>");
            mensajeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            contenedor.add(nombreLabel);
            contenedor.add(mensajeLabel);

            // Agregar el contenedor al panel de mensajes
            panelMensajes.add(contenedor);
            panelMensajes.revalidate();
            panelMensajes.repaint();
        });
    }

    /**
     * Método principal que ejecuta la interfaz de chat.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new chatGUI("Cliente"));
    }
}
