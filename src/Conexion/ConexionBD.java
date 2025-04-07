package Conexion; // Paquete donde se encuentra esta clase

import java.sql.Connection;       // Importa la clase Connection para manejar conexiones a bases de datos
import java.sql.DriverManager;    // Importa la clase DriverManager para obtener la conexión a la base de datos

public class ConexionBD {
    // Metodo que se encarga de obtener una conexión a la base de datos
    public Connection getConnection() {
        // Declaramos un objeto Connection que inicialmente no tiene ninguna conexión establecida
        Connection con = null;

        // Intentamos establecer la conexión
        try {
            // Usamos DriverManager para conectarnos a la base de datos
            // Los parámetros son: URL del servidor MySQL, nombre de usuario y contraseña
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/el_charco", "root", "");
        }
        // Si ocurre algún error al intentar conectarse, lo atrapamos aquí
        catch (Exception e) {
            // Imprime en consola el detalle del error
            e.printStackTrace();
        }

        // Retorna el objeto Connection (si la conexión fue exitosa, ya estará conectado)
        return con;
    }
}
