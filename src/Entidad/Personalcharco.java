package Entidad;

public class Personalcharco {
    int id;
    String nombre;
    String cargo;
    double salario;

    public Personalcharco(int id, String nombre, String cargo, double salario){
        this.id = id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }
     public int getId() {

        return id;
    }
    public void setId(int id) {

        this.id = id;
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
