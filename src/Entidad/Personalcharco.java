package Entidad;

/**
 * Clase que representa a un empleado dentro de la empresa.
 * Contiene los atributos relacionados con el empleado como el id, nombre, cargo y salario.
 */
public class Personalcharco {

    // Atributos de la clase Personalcharco
    private int id_empleado;
    private String nombre;
    private String cargo;
    private int salario;

    /**
     * Constructor de la clase `Personalcharco`, utilizado para inicializar un nuevo empleado
     * con los atributos necesarios.
     *
     * @param id_empleado El ID único del empleado.
     * @param nombre El nombre del empleado.
     * @param cargo El cargo o puesto de trabajo del empleado.
     * @param salario El salario del empleado.
     */
    public Personalcharco(int id_empleado, String nombre, String cargo, int salario) {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }

    /**
     * Constructor vacío para crear un objeto `Personalcharco` sin establecer valores iniciales.
     */
    public Personalcharco() {}

    // Métodos Getter y Setter

    /**
     * Obtiene el ID del empleado.
     *
     * @return El ID único del empleado.
     */
    public int getId() {
        return id_empleado;
    }

    /**
     * Establece el ID del empleado.
     *
     * @param id_empleado El ID único del empleado.
     */
    public void setId(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    /**
     * Obtiene el nombre del empleado.
     *
     * @return El nombre del empleado.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del empleado.
     *
     * @param nombre El nombre del empleado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el cargo o puesto del empleado.
     *
     * @return El cargo del empleado.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece el cargo del empleado.
     *
     * @param cargo El cargo del empleado.
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Obtiene el salario del empleado.
     *
     * @return El salario del empleado.
     */
    public int getSalario() {
        return salario;
    }

    /**
     * Establece el salario del empleado.
     *
     * @param salario El salario del empleado.
     */
    public void setSalario(int salario) {
        this.salario = salario;
    }

    /**
     * Método sobrecargado `toString()` para mostrar el nombre del empleado.
     *
     * @return El nombre del empleado como cadena.
     */
    @Override
    public String toString() {
        return nombre;
    }
}
