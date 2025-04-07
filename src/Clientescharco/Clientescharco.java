package Clientescharco;

/**
 * Clase que representa un cliente de la base de datos "el_charco".
 * Contiene los atributos básicos de un cliente como su ID, nombre, teléfono,
 * dirección y correo electrónico.
 *
 * Esta clase actúa como modelo para los registros en la tabla `clientes`.
 *
 * @author Juan
 * @version 1.0
 */
public class Clientescharco {

    /** Identificador único del cliente. */
    int id;

    /** Nombre completo del cliente. */
    String nombre;

    /** Número de teléfono del cliente. */
    String telefono;

    /** Dirección de residencia del cliente. */
    String direccion;

    /** Correo electrónico del cliente. */
    String correo;

    /**
     * Constructor para crear una instancia de un cliente con todos sus atributos.
     *
     * @param id        Identificador del cliente.
     * @param nombre    Nombre del cliente.
     * @param telefono  Teléfono del cliente.
     * @param direccion Dirección del cliente.
     * @param correo    Correo electrónico del cliente.
     */
    public Clientescharco(int id, String nombre, String telefono, String direccion, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    /**
     * Obtiene el ID del cliente.
     *
     * @return ID del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del cliente.
     *
     * @param id Nuevo ID del cliente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return Nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre Nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono del cliente.
     *
     * @return Teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     *
     * @param telefono Nuevo teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return Dirección del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param direccion Nueva dirección del cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return Correo electrónico del cliente.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo Nuevo correo electrónico del cliente.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
