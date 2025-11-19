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

    public Cliente(String nombre, String apellido, int dni, int telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.deuda = 0;
        this.arrayVehiculo = new ArrayList<>();
    }

    public Cliente() {
        super("", "", 0, 0, "");
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


    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object= super.toJson();
        try{
        object.put("deuda",this.deuda);
            JSONArray array=new JSONArray();

        for(Vehiculo vehiculoJson:this.arrayVehiculo){
            array.put(vehiculoJson.toJson());
        }
        object.put("vehiculos",array);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return object;
    }


    /// fromJson
    public static Cliente fromJson(JSONObject object) {
        Cliente cliente = new Cliente(); // constructor vacío
        try {
            /// Aca se tienen que volver a pasar los atributos de la clase padre.
            cliente.setNombre(object.getString("nombre"));
            cliente.setApellido(object.getString("apellido"));
            cliente.setDni(object.getLong("dni"));
            cliente.setTelefono(object.getLong("telefono"));
            cliente.setEmail(object.getString("email"));

            /// Los atributos de cliente van aca
            cliente.setDeuda(object.getDouble("deuda"));

            /// Serializar los objetos del array
            cliente.arrayVehiculo = new ArrayList<>();
            JSONArray arrayJson = object.getJSONArray("vehiculos");
            for (int i = 0; i < arrayJson.length(); i++) {
                JSONObject vehiculoJson = arrayJson.getJSONObject(i);
                Vehiculo vehiculoA = Vehiculo.fromJson(vehiculoJson);
                cliente.getArrayVehiculo().add(vehiculoA);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cliente;
    }


}
