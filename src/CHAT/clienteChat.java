package CHAT;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

public class clienteChat
{
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

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

    public void enviarMensaje(String mensaje) {
        if (salida != null) {
            salida.println(mensaje);
        }
    }

    public void cerrarConexion() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}