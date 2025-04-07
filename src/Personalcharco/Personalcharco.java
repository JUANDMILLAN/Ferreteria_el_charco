package Personalcharco;

/**
 * Clase que representa a un miembro del personal en el sistema de gestión "El Charco".
 * Contiene atributos relacionados con el personal como ID, nombre, cargo y salario.
 *
 * Esta clase es un modelo que se utiliza para transferir datos entre la interfaz y la base de datos.
 *
 * @author Juan
 * @version 1.0
 */
public class Personalcharco {
    /** Identificador único del personal */
    private int id;

    /** Nombre del personal */
    private String nombre;

    /** Cargo o puesto del personal */
    private String cargo;

    /** Salario mensual del personal */
    private double salario;

    /**
     * Constructor de la clase Personalcharco.
     *
     * @param id Identificador del personal.
     * @param nombre Nombre completo del personal.
     * @param cargo Cargo que desempeña el personal.
     * @param salario Salario del personal.
     */
    public Personalcharco(int id, String nombre, String cargo, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }

    /** @return el ID del personal. */
    public int getId() {
        return id;
    }

    /** @param id Establece el ID del personal. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return el nombre del personal. */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre Establece el nombre del personal. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return el cargo del personal. */
    public String getCargo() {
        return cargo;
    }

    /** @param cargo Establece el cargo del personal. */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /** @return el salario del personal. */
    public double getSalario() {
        return salario;
    }

    /** @param salario Establece el salario del personal. */
    public void setSalario(double salario) {
        this.salario = salario;
    }
}
