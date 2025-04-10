package Entidad;

public class Personalcharco {
    int id_empleado;
    String nombre;
    String cargo;
    int salario;

    // Constructor con parámetros
    public Personalcharco(int id_empleado, String nombre, String cargo, int salario) {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }

    // Constructor vacío
    public Personalcharco() {}

    // Getters y setters
    public int getId() {
        return id_empleado;
    }

    public void setId(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    // Para mostrar el nombre en el combo
    @Override
    public String toString() {
        return nombre;
    }
}
