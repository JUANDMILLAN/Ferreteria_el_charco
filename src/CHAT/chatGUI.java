package CHAT;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class chatGUI extends JFrame {
    private JPanel panelMensajes;
    private JTextField campoMensaje;
    private clienteChat cliente;
    private String nombre;

    public chatGUI(String nombre) {
        this.nombre = nombre;

        setTitle("Chat - " + nombre);
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔽 TÍTULO CENTRADO EN LA PARTE SUPERIOR
        JLabel titulo = new JLabel("Ferretería El Charco 🔩", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // 🔽 PANEL DE MENSAJES CON SCROLL
        panelMensajes = new JPanel();
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        panelMensajes.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(panelMensajes);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);

        // 🔽 CAMPO DE TEXTO
        campoMensaje = new JTextField();
        campoMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(campoMensaje, BorderLayout.SOUTH);

        campoMensaje.addActionListener(e -> {
            String mensaje = campoMensaje.getText().trim();
            if (!mensaje.isEmpty()) {
                cliente.enviarMensaje(nombre + "::" + mensaje);
                campoMensaje.setText("");
            }
        });

        // 🔽 CONEXIÓN AL SERVIDOR
        try {
            cliente = new clienteChat("localhost", 12345, this::mostrarMensaje);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
            System.exit(0);
        }

        setVisible(true);
    }


    private void mostrarMensaje(String mensajeCompleto) {
        SwingUtilities.invokeLater(() -> {
            String[] partes = mensajeCompleto.split("::", 2);
            String emisor = partes[0];
            String mensaje = partes.length > 1 ? partes[1] : mensajeCompleto;

            JPanel contenedor = new JPanel();
            contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
            contenedor.setBackground(Color.WHITE);
            contenedor.setAlignmentX(Component.LEFT_ALIGNMENT);
            contenedor.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel nombreLabel = new JLabel(emisor);
            nombreLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

            JLabel mensajeLabel = new JLabel("<html>" + mensaje + "</html>");
            mensajeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            contenedor.add(nombreLabel);
            contenedor.add(mensajeLabel);

            panelMensajes.add(contenedor);
            panelMensajes.revalidate();
            panelMensajes.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new chatGUI("Cliente"));
    }
}
