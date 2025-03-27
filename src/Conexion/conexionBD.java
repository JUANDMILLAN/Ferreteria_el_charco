package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexionBD
{
    public Connection getconnection()
    {
        Connection con = null;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/el_Charco","root","");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return con;
    }
}
