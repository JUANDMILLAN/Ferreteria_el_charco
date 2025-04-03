package Entidad;

public class Personalcharco {
    int id_empleado;
    String nombre;
    String cargo;
    double salario;

    public Personalcharco(int id_empleado, String nombre, String cargo, double salario){
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }
     public int getId() {

        return id_empleado;
    }
    public void setId(int id) {

        this.id_empleado = id_empleado;
    }
    public String getNombre() {

        return nombre;
    }
    public void setNombre(String nombre) {

        this.nombre = nombre;
    }
    public String getcargo() {

        return cargo;
    }
    public void setcargo(String  cargo) {

        this.cargo = cargo;
    }
    public double getsalario() {

        return salario;
    }
    public void setsalario(double salario) {

        this.salario = salario;
    }


}
