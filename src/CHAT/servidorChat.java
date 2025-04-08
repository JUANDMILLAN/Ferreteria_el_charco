package CHAT;
import java.io.*;
import java.net.*;
import java.util.*;

public class servidorChat {
    private static List<Socket> clientes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor de chat iniciado...");

        while (true) {
            Socket cliente = servidor.accept();
            clientes.add(cliente);
            new HiloCliente(cliente).start();
        }
    }

    static class HiloCliente extends Thread {
        private Socket socket;
        private BufferedReader entrada;

        public HiloCliente(Socket socket) throws IOException {
            this.socket = socket;
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);
                    for (Socket cliente : clientes) {
                            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
                            salida.println(mensaje);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
