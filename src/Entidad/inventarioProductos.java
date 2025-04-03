package Entidad;

public class inventarioProductos
{
    int id_producto;
    enum categoria {herramienta,electricos};
    categoria categoria;
    String nombre_producto;
    float precio_proveedor;
    float precio_venta;
    int cantidad_stock;

    // metodo constructor
    public inventarioProductos(int id_producto,categoria categoria , String nombre_producto, float precio_proveedor, float precio_venta, int cantidad_stock) {
        this.id_producto = id_producto;
        this.categoria = categoria;
        this.nombre_producto = nombre_producto;
        this.precio_proveedor = precio_proveedor;
        this.precio_venta = precio_venta;
        this.cantidad_stock = cantidad_stock;
    }
    //get and set
    public int getId() {
        return id_producto;
    }
    public void setId(int id_producto) {
        this.id_producto = id_producto;
    }
    public categoria categoria() {
        return categoria;
    }
    public void setCategoria(categoria categoria) {
        this.categoria = categoria;
    }
    // Getter y Setter para nombre_producto
    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    // Getter y Setter para precio_proveedor
    public float getPrecio_proveedor() {
        return precio_proveedor;
    }

    public void setPrecio_proveedor(float precio_proveedor) {
        this.precio_proveedor = precio_proveedor;
    }

    // Getter y Setter para precio_venta
    public float getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }

    // Getter y Setter para cantidad_stock
    public int getCantidad_stock() {
        return cantidad_stock;
    }

    public void setCantidad_stock(int cantidad_stock) {
        this.cantidad_stock = cantidad_stock;
    }
}
