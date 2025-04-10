package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;

public class conexionBD {
    //creacion objeto
    public Connection getConnection() {
        //no tiene ninguna coneccion
        Connection con = null;
//intenta obtener la url, la base de datos el nombre y la contraseña
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/el_charco","root", "");
        }
        //para atrapar un  error si lo hay
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }
}
