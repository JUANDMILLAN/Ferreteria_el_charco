package Entidad;

public class Proveedorescharco
{
    //atributos de la tabla proveedores
    int id_proveedor;
    String nombre;
    String contacto;

    //metodo constructor
    public Proveedorescharco(int id_proveedor, String nombre,String contacto) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.contacto = contacto;
    }
    //get and set
    public int getId() {
        return id_proveedor;
    }
    public void setId(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getContacto() {
        return contacto;
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}
