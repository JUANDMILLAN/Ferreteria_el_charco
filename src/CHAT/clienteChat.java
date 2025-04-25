package CHAT;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

/**
 * Clase que representa al cliente de chat que se conecta a un servidor de chat mediante un socket.
 * Permite enviar y recibir mensajes al servidor.
 * La clase gestiona la conexión, la recepción de mensajes en un hilo separado y el envío de mensajes.
 */
public class clienteChat {

    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

    /**
     * Constructor de la clase clienteChat.
     * Establece la conexión con el servidor y configura el hilo para recibir los mensajes.
     *
     * @param host El host del servidor al que el cliente se conecta.
     * @param puerto El puerto del servidor al que el cliente se conecta.
     * @param receptorMensajes Un consumidor que maneja los mensajes recibidos desde el servidor.
     * @throws IOException Si ocurre un error al establecer la conexión o al leer los datos.
     */
    public clienteChat(String host, int puerto, Consumer<String> receptorMensajes) throws IOException {
        socket = new Socket(host, puerto);
        salida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Hilo para recibir mensajes
        new Thread(() -> {
            String mensaje;
            try {
                while ((mensaje = entrada.readLine()) != null) {
                    receptorMensajes.accept(mensaje);
                }
            } catch (IOException e) {
                receptorMensajes.accept("Conexión cerrada.");
            }
        }).start();
    }

    /**
     * Método para enviar un mensaje al servidor.
     *
     * @param mensaje El mensaje que se desea enviar al servidor.
     */
    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }

    /**
     * Cierra la conexión con el servidor.
     * Este método libera los recursos utilizados por el socket.
     */
    public void cerrarConexion() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
