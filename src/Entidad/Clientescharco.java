package Entidad;

/**
 * Clase que representa un cliente de la ferretería `El Charco`.
 * Los objetos de esta clase contienen la información de los clientes
 * de la base de datos, como el nombre, teléfono, dirección y correo electrónico.
 */
public class Clientescharco {

    // Atributos de la tabla clientes
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;

    /**
     * Constructor con parámetros para inicializar los atributos de un cliente.
     *
     * @param id El ID del cliente.
     * @param nombre El nombre del cliente.
     * @param telefono El número de teléfono del cliente.
     * @param direccion La dirección del cliente.
     * @param correo El correo electrónico del cliente.
     */
    public Clientescharco(int id, String nombre, String telefono, String direccion, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    /**
     * Constructor vacío para la creación de un objeto `Clientescharco` sin inicializar los atributos.
     */
    public Clientescharco() {}

    // Getters y setters

    /**
     * Obtiene el ID del cliente.
     *
     * @return El ID del cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del cliente.
     *
     * @param id El ID del cliente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return El nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombre El nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return El número de teléfono del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del cliente.
     *
     * @param telefono El número de teléfono del cliente.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return La dirección del cliente.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del cliente.
     *
     * @param direccion La dirección del cliente.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return El correo electrónico del cliente.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correo El correo electrónico del cliente.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Devuelve una representación en forma de texto del cliente,
     * utilizando su nombre para mostrarlo en un combo o lista.
     *
     * @return El nombre del cliente.
     */
    @Override
    public String toString() {
        return nombre;
    }
}
