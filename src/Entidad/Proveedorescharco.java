package Entidad;

/**
 * Clase que representa a un proveedor de la empresa.
 * Contiene los atributos relacionados con el proveedor como el id, nombre y contacto.
 */
public class Proveedorescharco {

    // Atributos de la clase Proveedorescharco
    private int id_proveedor;
    private String nombre;
    private String contacto;

    /**
     * Constructor de la clase `Proveedorescharco`, utilizado para inicializar un nuevo proveedor
     * con los atributos necesarios.
     *
     * @param id_proveedor El ID único del proveedor.
     * @param nombre El nombre del proveedor.
     * @param contacto El contacto del proveedor.
     */
    public Proveedorescharco(int id_proveedor, String nombre, String contacto) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.contacto = contacto;
    }

    // Métodos Getter y Setter

    /**
     * Obtiene el ID del proveedor.
     *
     * @return El ID único del proveedor.
     */
    public int getId() {
        return id_proveedor;
    }

    /**
     * Establece el ID del proveedor.
     *
     * @param id_proveedor El ID único del proveedor.
     */
    public void setId(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    /**
     * Obtiene el nombre del proveedor.
     *
     * @return El nombre del proveedor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del proveedor.
     *
     * @param nombre El nombre del proveedor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el contacto del proveedor.
     *
     * @return El contacto del proveedor.
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * Establece el contacto del proveedor.
     *
     * @param contacto El contacto del proveedor.
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}
