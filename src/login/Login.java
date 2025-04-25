package login;

import Vistas.MenuGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que representa la ventana de inicio de sesión (Login).
 * Permite al usuario ingresar su nombre de usuario y contraseña para acceder al sistema.
 */
public class Login extends JFrame {

    private JPanel main;
    private JPanel bg;
    private JTextField ingreseSuUsuarioTextField;
    private JPasswordField passwordField1;
    private JButton ingresarButton;
    private JButton xButton;

    /**
     * Constructor que configura la interfaz de usuario del Login.
     * Inicializa los componentes y agrega los eventos de los botones y campos de texto.
     */
    public Login() {
        setTitle("Login");
        setContentPane(main);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Listener para el campo de usuario
        ingreseSuUsuarioTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ingreseSuUsuarioTextField.getText().equals("Ingrese su Usuario")) {
                    ingreseSuUsuarioTextField.setText("");
                    ingreseSuUsuarioTextField.setForeground(Color.black);
                }
                if (String.valueOf(passwordField1.getPassword()).isEmpty()) {
                    passwordField1.setText("");
                    passwordField1.setForeground(Color.gray);
                }
            }
        });

        // Listener para el campo de contraseña
        passwordField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (String.valueOf(passwordField1.getPassword()).equals("")) {
                    passwordField1.setText("");
                    passwordField1.setForeground(Color.gray);
                }
                if (ingreseSuUsuarioTextField.getText().isEmpty()) {
                    ingreseSuUsuarioTextField.setText("Ingrese su Usuario");
                    ingreseSuUsuarioTextField.setForeground(Color.black);
                }
            }
        });

        // Acción del botón de cerrar
        xButton.addActionListener(e -> System.exit(0));

        // Validar el login cuando el usuario hace clic en el botón ingresar
        ingresarButton.addActionListener(e -> {
            String usuario = ingreseSuUsuarioTextField.getText();
            String contrasena = String.valueOf(passwordField1.getPassword());

            if ("admin".equals(usuario) && "123".equals(contrasena)) {
                new MenuGUI().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error, Usuario o Contraseña Incorrectos");
            }
        });
    }

    /**
     * Método principal para iniciar la aplicación de Login.
     * Configura el Look and Feel de la interfaz y muestra la ventana de Login.
     */
    public static void main(String[] args) {
        // Establecer el Look and Feel de la interfaz
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarculaLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar el Login en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
