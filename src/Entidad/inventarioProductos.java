package Entidad;

public class inventarioProductos
{
    int id_producto;
    String categoria;
    String nombre_producto;
    int precio_proveedor;
    int precio_venta;
    int cantidad_stock;

    // metodo constructor
    public inventarioProductos(int id_producto,String categoria , String nombre_producto, int precio_proveedor, int precio_venta, int cantidad_stock) {
        this.id_producto = id_producto;
        this.categoria = categoria;
        this.nombre_producto = nombre_producto;
        this.precio_proveedor = precio_proveedor;
        this.precio_venta = precio_venta;
        this.cantidad_stock = cantidad_stock;
    }
    //get and set

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getCantidad_stock() {
        return cantidad_stock;
    }

    public void setCantidad_stock(int cantidad_stock) {
        this.cantidad_stock = cantidad_stock;
    }

    public int getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(int precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getPrecio_proveedor() {
        return precio_proveedor;
    }

    public void setPrecio_proveedor(int precio_proveedor) {
        this.precio_proveedor = precio_proveedor;
    }
}
