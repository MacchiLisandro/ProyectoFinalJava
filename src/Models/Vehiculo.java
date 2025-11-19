package Models;

import Enums.Marca;
import Interfaces.IJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Vehiculo implements IJson {
    private String patente;
    private Marca marca;
    private String modelo;

    public Vehiculo(String patente, Marca marca, String modelo) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Vehiculo() {
        this.patente = "";
        this.marca = null;
        this.modelo = "";
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vehiculo vehiculo)) return false;
        return Objects.equals(patente, vehiculo.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patente);
    }



    ///  toJson
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object=new JSONObject();
        try{
            object.put("patente",this.patente);
            object.put("marca",this.marca);
            object.put("modelo",this.modelo);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return object;
    }


    /// FromJson

    public static Vehiculo fromJson(JSONObject object) {
        Vehiculo vehiculo = new Vehiculo();
        try {
            vehiculo.setPatente(object.getString("patente"));
            vehiculo.setMarca(Marca.valueOf(object.getString("marca")));
            vehiculo.setModelo(object.getString("modelo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }






}
