package Models;

import Interfaces.IJson;
import org.json.JSONException;
import org.json.JSONObject;

public class Servicio extends ItemTaller implements IJson {
    private int tiempoEstimado;
    private String descripcion;

    public Servicio(String nombre, double precio, int tiempoEstimado, String descripcion) {
        super(nombre, precio);
        this.tiempoEstimado = tiempoEstimado;
        this.descripcion = descripcion;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    /// To json

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object=super.toJson();
        try{
            object.put("tiempoEstimado",this.tiempoEstimado);
            object.put("descripcion",this.descripcion);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return object;
    }
}
