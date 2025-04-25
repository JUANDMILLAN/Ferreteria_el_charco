package CHAT;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Clase que implementa un servidor de chat básico utilizando sockets.
 * El servidor acepta conexiones de múltiples clientes y retransmite
 * los mensajes recibidos de un cliente a todos los demás clientes conectados.
 */
public class servidorChat {

    // Lista para mantener los sockets de los clientes conectados
    private static List<Socket> clientes = new ArrayList<>();

    /**
     * Método principal que inicia el servidor de chat y espera las conexiones de los clientes.
     * Por cada cliente que se conecta, se crea un nuevo hilo para manejar la comunicación con ese cliente.
     *
     * @param args Los argumentos de la línea de comandos (no utilizados en este caso).
     * @throws IOException Si ocurre un error al crear el servidor o aceptar una conexión.
     */
    public static void main(String[] args) throws IOException {
        // Se crea el ServerSocket para aceptar conexiones en el puerto 12345
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor de chat iniciado...");

        // Bucle para aceptar clientes continuamente
        while (true) {
            // Espera una conexión de un cliente
            Socket cliente = servidor.accept();
            clientes.add(cliente);  // Se añade el cliente a la lista
            // Se inicia un nuevo hilo para gestionar la comunicación con el cliente
            new HiloCliente(cliente).start();
        }
    }

    /**
     * Clase interna que maneja la comunicación con un cliente específico.
     * Recibe los mensajes de un cliente y los retransmite a todos los demás clientes.
     */
    static class HiloCliente extends Thread {

        private Socket socket;
        private BufferedReader entrada;

        /**
         * Constructor que inicializa el socket del cliente y el lector de entrada.
         *
         * @param socket El socket de conexión con el cliente.
         * @throws IOException Si ocurre un error al crear el BufferedReader.
         */
        public HiloCliente(Socket socket) throws IOException {
            this.socket = socket;
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        /**
         * Método que lee los mensajes del cliente y los envía a todos los clientes conectados.
         * Si ocurre un error, el hilo se detiene.
         */
        public void run() {
            try {
                String mensaje;
                // Bucle para leer mensajes enviados por el cliente
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                    // Enviar el mensaje recibido a todos los clientes conectados
                    for (Socket cliente : clientes) {
                        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
                        salida.println(mensaje);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();  // Imprime la traza de la excepción
            }
        }
    }
}
