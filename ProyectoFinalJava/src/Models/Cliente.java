package Models;
import Interfaces.IJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cliente extends Persona implements IJson {

    private double deuda;
    private ArrayList<Vehiculo> arrayVehiculo;


    /// Constructor

    public Cliente(String nombre, String apellido, int dni, int telefono, String email, double deuda, ArrayList<Vehiculo> arrayVehiculo) {
        super(nombre, apellido, dni, telefono, email);
        this.deuda = deuda;
        this.arrayVehiculo = arrayVehiculo;
    }

    public Cliente() {
        super("", "", 0, 0, "");
        this.deuda = deuda;
        this.arrayVehiculo = arrayVehiculo;
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


    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object= super.toJson();
        try{
        object.put("deuda",this.deuda);
            JSONArray array=new JSONArray();

        for(Vehiculo vehiculoJson:this.arrayVehiculo){
            array.put(vehiculoJson);
        }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return object;
    }

}
