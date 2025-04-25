package Entidad;

/**
 * Clase que representa un producto en el inventario de la ferretería.
 * Contiene los atributos del producto como el id, la categoría, nombre,
 * precio de proveedor, precio de venta y cantidad en stock.
 */
public class inventarioProductos {

    // Atributos de la clase inventarioProductos
    private int id_producto;
    private String categoria;
    private String nombre_producto;
    private int precio_proveedor;
    private int precio_venta;
    private int cantidad_stock;

    /**
     * Constructor de la clase `inventarioProductos`, utilizado para inicializar
     * un nuevo objeto producto con todos los atributos necesarios.
     *
     * @param id_producto El ID único del producto.
     * @param categoria La categoría a la que pertenece el producto.
     * @param nombre_producto El nombre del producto.
     * @param precio_proveedor El precio al que se adquiere el producto del proveedor.
     * @param precio_venta El precio al que se vende el producto.
     * @param cantidad_stock La cantidad disponible de producto en stock.
     */
    public inventarioProductos(int id_producto, String categoria, String nombre_producto,
                               int precio_proveedor, int precio_venta, int cantidad_stock) {
        this.id_producto = id_producto;
        this.categoria = categoria;
        this.nombre_producto = nombre_producto;
        this.precio_proveedor = precio_proveedor;
        this.precio_venta = precio_venta;
        this.cantidad_stock = cantidad_stock;
    }

    // Métodos Getter y Setter

    /**
     * Obtiene el ID del producto.
     *
     * @return El ID del producto.
     */
    public int getId_producto() {
        return id_producto;
    }

    /**
     * Establece el ID del producto.
     *
     * @param id_producto El ID del producto.
     */
    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    /**
     * Obtiene la categoría del producto.
     *
     * @return La categoría del producto.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     *
     * @param categoria La categoría del producto.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre_producto() {
        return nombre_producto;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre_producto El nombre del producto.
     */
    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    /**
     * Obtiene la cantidad en stock del producto.
     *
     * @return La cantidad disponible en stock del producto.
     */
    public int getCantidad_stock() {
        return cantidad_stock;
    }

    /**
     * Establece la cantidad en stock del producto.
     *
     * @param cantidad_stock La cantidad en stock del producto.
     */
    public void setCantidad_stock(int cantidad_stock) {
        this.cantidad_stock = cantidad_stock;
    }

    /**
     * Obtiene el precio de venta del producto.
     *
     * @return El precio de venta del producto.
     */
    public int getPrecio_venta() {
        return precio_venta;
    }

    /**
     * Establece el precio de venta del producto.
     *
     * @param precio_venta El precio de venta del producto.
     */
    public void setPrecio_venta(int precio_venta) {
        this.precio_venta = precio_venta;
    }

    /**
     * Obtiene el precio de proveedor del producto.
     *
     * @return El precio que el proveedor cobra por el producto.
     */
    public int getPrecio_proveedor() {
        return precio_proveedor;
    }

    /**
     * Establece el precio de proveedor del producto.
     *
     * @param precio_proveedor El precio que el proveedor cobra por el producto.
     */
    public void setPrecio_proveedor(int precio_proveedor) {
        this.precio_proveedor = precio_proveedor;
    }
}
