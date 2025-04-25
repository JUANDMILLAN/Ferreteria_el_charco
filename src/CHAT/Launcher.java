package CHAT;

import javax.swing.*;

/**
 * Clase principal que lanza dos instancias de la interfaz de chat.
 * Una para el rol de "Empleado" y otra para el rol de "Cliente".
 * Utiliza `SwingUtilities.invokeLater` para asegurarse de que la creación
 * de las interfaces gráficas se haga en el hilo de eventos de Swing.
 */
public class Launcher {

    /**
     * Método principal que inicia las instancias de `chatGUI` para los roles de "Empleado" y "Cliente".
     *
     * @param args Los argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new chatGUI("Empleado");
            new chatGUI("Cliente");
        });
    }
}
