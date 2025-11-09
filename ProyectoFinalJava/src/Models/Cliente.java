package Models;
import java.util.ArrayList;

public class Cliente {

    private double deuda;
    private ArrayList<Vehiculo> arrayVehiculo;


    /// Constructor

    public Cliente(double deuda) {
        this.deuda = deuda;
        this.arrayVehiculo = new ArrayList<>();
    }

    /// Getters Setters

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public ArrayList<Vehiculo> getArrayVehiculo() {
        return arrayVehiculo;
    }

    /// Metodos

    @Override
    public String toString() {
        return super.toString() +
                "Cliente{" +
                "deuda=" + deuda +
                ", arrayVehiculo=" + arrayVehiculo +
                '}';
    }

        /// Metodo para agregar un vehiculo al cliente

    public boolean agregarAutoCliente(Vehiculo vehiculocliente){

        if (vehiculocliente != null){
           return arrayVehiculo.add(vehiculocliente);
        }
        else{
            return false;
        }
    }
    /// Eliminar vehiculo del cliente
    public boolean eliminarAutoCliente(Vehiculo vehiculocliente){
        if (vehiculocliente!= null){
            return arrayVehiculo.remove(vehiculocliente);
        }
        else{
            return false;
        }
    }




}
