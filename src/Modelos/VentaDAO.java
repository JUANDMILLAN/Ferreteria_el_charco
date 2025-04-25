package Entidad;

import java.sql.Timestamp;

/**
 * Clase que representa una venta realizada en el sistema. Esta clase almacena
 * información como el identificador de la venta, el nombre del cliente,
 * el nombre del empleado que atendió, el total de la venta y la fecha en que se realizó.
 *
 * <p>Nota: A pesar del nombre {@code VentaDAO}, esta clase no contiene lógica de acceso
 * a base de datos, por lo tanto sería más apropiado un nombre como VentaDTO o VentaEntidad.</p>
 */
public class VentaDAO {

    /** Identificador único de la venta. Corresponde al campo id_venta en la base de datos. */
    private int id;

    /** Nombre del cliente que realizó la compra. Este dato se obtiene de la tabla clientes. */
    private String nombreCliente;

    /** Nombre del empleado que registró la venta. Este dato se obtiene de la tabla empleados. */
    private String nombreEmpleado;

    /** Total en dinero correspondiente al valor de la venta (sin desglose de productos). */
    private double total;

    /** Fecha y hora exacta en que se registró la venta. Se asigna usualmente con NOW() en SQL. */
    private Timestamp fecha;

    /**
     * Obtiene el identificador único de la venta.
     *
     * @return el ID de la venta.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la venta.
     *
     * @param id el nuevo ID de la venta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cliente que realizó la compra.
     *
     * @return el nombre del cliente.
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Establece el nombre del cliente asociado a esta venta.
     *
     * @param nombreCliente nombre del cliente.
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el nombre del empleado que registró la venta.
     *
     * @return el nombre del empleado.
     */
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    /**
     * Establece el nombre del empleado que realizó la venta.
     *
     * @param nombreEmpleado nombre del empleado.
     */
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    /**
     * Obtiene el total en dinero de la venta.
     *
     * @return el total de la venta.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el total en dinero de la venta.
     *
     * @param total el valor total de la venta.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Obtiene la fecha y hora en la que se realizó la venta.
     *
     * @return un objeto {@code Timestamp} con la fecha de la venta.
     */
    public Timestamp getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha y hora en la que se realizó la venta.
     *
     * @param fecha objeto {@code Timestamp} que representa la fecha de la venta.
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
