package CHAT;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new chatGUI("Empleado");
            new chatGUI("Cliente");
        });
    }
}

